package ca.dal.bartertrader.utils.handler.live_data;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class NavControllerLiveData extends LiveData<NavController> implements NavController.OnDestinationChangedListener {

        private final BottomNavigationView bottomNavigationView;
        private final FragmentManager fragmentManager;
        private final int navGraphContainerId;
        private final HashMap<Integer, String> navGraphMap;
        private final int[] navGraphIds;
        private NavController controller;

        public NavControllerLiveData(BottomNavigationView bottomNavigationView, int[] navGraphIds, FragmentManager fragmentManager, int navGraphContainerId) {
                this.bottomNavigationView = bottomNavigationView;
                this.fragmentManager = fragmentManager;
                this.navGraphContainerId = navGraphContainerId;
                this.navGraphMap = new HashMap<>();
                this.navGraphIds = navGraphIds;
                setController();
        }

        private void setController() {
                AtomicReference<NavController> result = new AtomicReference<>();
                AtomicInteger topLevelDestinationId = new AtomicInteger();

                for (int index = 0; index < navGraphIds.length; index++) {
                        String fragmentTag = "navigation#" + index;

                        NavHostFragment navHostFragment = getNavHostFragment(fragmentTag, navGraphIds[index]);

                        int currentGraphId = navHostFragment.getNavController().getGraph().getId();
                        Boolean isTopLevel = index == 0;

                        if (isTopLevel) {
                                topLevelDestinationId.set(currentGraphId);
                        }

                        navGraphMap.put(currentGraphId, fragmentTag);

                        if (bottomNavigationView.getSelectedItemId() == currentGraphId) {
                                setValue(navHostFragment.getNavController());
                                attachNavHostFragment(navHostFragment, isTopLevel);
                        } else {
                                detachNavHostFragment(navHostFragment);
                        }
                }

                AtomicReference<String> selectedItemTag = new AtomicReference<>(navGraphMap.get(bottomNavigationView.getSelectedItemId()));
                String topLevelDestinationTag = navGraphMap.get(topLevelDestinationId.get());
                AtomicBoolean isOnTopLevel = new AtomicBoolean(selectedItemTag.get().equals(topLevelDestinationTag));

                bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
                        if (fragmentManager.isStateSaved()) {
                                return false;
                        } else {
                                String currentSelectedItemTag = navGraphMap.get(item.getItemId());

                                if (!selectedItemTag.get().equals(currentSelectedItemTag)) {
                                        fragmentManager.popBackStack(topLevelDestinationTag, FragmentManager.POP_BACK_STACK_INCLUSIVE);

                                        NavHostFragment currentSelectedFragment = (NavHostFragment) fragmentManager.findFragmentByTag(currentSelectedItemTag);

                                        if (!topLevelDestinationTag.equals(currentSelectedItemTag)) {
                                                FragmentTransaction transaction = fragmentManager.beginTransaction()
                                                        .attach(currentSelectedFragment)
                                                        .setPrimaryNavigationFragment(currentSelectedFragment);

                                                navGraphMap.forEach((id, tag) -> {
                                                        if (!tag.equals(currentSelectedItemTag)) {
                                                                transaction.detach(fragmentManager.findFragmentByTag(topLevelDestinationTag));
                                                        }
                                                });

                                                transaction.addToBackStack(topLevelDestinationTag)
                                                        .setReorderingAllowed(true)
                                                        .commit();
                                        }
                                        selectedItemTag.set(currentSelectedItemTag);
                                        isOnTopLevel.set(selectedItemTag.get().equals(topLevelDestinationTag));
                                        setValue(currentSelectedFragment.getNavController());
                                        return true;
                                } else {
                                        return false;
                                }
                        }
                });

                setOnItemReselected();

                fragmentManager.addOnBackStackChangedListener(() -> {
                        if (!isOnTopLevel.get() && !isOnBackStack(topLevelDestinationTag)) {
                                bottomNavigationView.setSelectedItemId(topLevelDestinationId.get());
                        }

                        if (controller != null) {
                                if (controller.getCurrentDestination() == null) {
                                        controller.navigate(controller.getGraph().getId());
                                }
                        }
                });

        }

        private void setOnItemReselected() {
                this.bottomNavigationView.setOnNavigationItemReselectedListener(item -> {
                        String currentSelectedItemTag = navGraphMap.get(item.getItemId());
                        NavHostFragment currentSelectedFragment = (NavHostFragment) fragmentManager.findFragmentByTag(currentSelectedItemTag);
                        NavController navController = currentSelectedFragment.getNavController();

                        navController.popBackStack(navController.getGraph().getStartDestination(), false);
                });
        }

        private void detachNavHostFragment(NavHostFragment navHostFragment) {
                fragmentManager.beginTransaction().detach(navHostFragment).commitNow();
        }

        private void attachNavHostFragment(NavHostFragment navHostFragment, Boolean isTopLevel) {
                FragmentTransaction transaction = fragmentManager.beginTransaction().attach(navHostFragment);

                if (isTopLevel) {
                        transaction.setPrimaryNavigationFragment(navHostFragment);
                }

                transaction.commitNow();
        }
        private NavHostFragment getNavHostFragment(String fragmentTag, Integer navGraphId) {
                NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentByTag(fragmentTag);

                if (navHostFragment != null) {
                        return navHostFragment;
                }

                navHostFragment = NavHostFragment.create(navGraphId);

                fragmentManager.beginTransaction()
                        .add(navGraphContainerId, navHostFragment, fragmentTag)
                        .commitNow();

                return navHostFragment;
        }

        private boolean isOnBackStack(String backStackName) {
                int backStackSize = fragmentManager.getBackStackEntryCount();

                for (int index = 0; index < backStackSize; index++) {
                        if (fragmentManager.getBackStackEntryAt(index).getName().equals(backStackName)) {
                                return true;
                        }
                }

                return false;
        }

        @Override
        public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                if (controller.getGraph().getId() != destination.getParent().getId()) {

                        bottomNavigationView.setSelectedItemId(destination.getParent().getId());
                }
        }

        @Override
        protected void setValue(NavController navController) {
                if (this.controller != null) {
                        this.controller.removeOnDestinationChangedListener(this);
                }
                this.controller = navController;
                if (this.controller != null) {
                        this.controller.addOnDestinationChangedListener(this);
                }
                super.setValue(navController);
        }
}
