package ca.dal.bartertrader.presentation.view.provider_home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import ca.dal.bartertrader.databinding.FragmentHandlePostBinding;
import ca.dal.bartertrader.di.view_model.provider_home.HandlePostViewModelFactory;
import ca.dal.bartertrader.presentation.view_model.provider_home.HandlePostViewModel;
import ca.dal.bartertrader.utils.BindingUtils;

import static androidx.core.content.FileProvider.getUriForFile;

public class HandlePostFragment extends Fragment {

    private FragmentHandlePostBinding binding = null;
    private HandlePostViewModel viewModel;
    private final HandlePostViewModelFactory handlePostViewModelFactory;

    private Uri imageUri = null;

    public HandlePostFragment(HandlePostViewModelFactory handlePostViewModelFactory) {
        this.handlePostViewModelFactory = handlePostViewModelFactory;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHandlePostBinding.inflate(getLayoutInflater());

        viewModel = new ViewModelProvider(this, handlePostViewModelFactory).get(HandlePostViewModel.class);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.setViewModel(viewModel);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        viewModel.getUploadImageFromCameraEvent().observe(getViewLifecycleOwner(), __ -> {
            try {
                imageUri = createImageFile();
            } catch (IOException e) {
                Toast.makeText(getContext(), "Something went wrong... Please try again!", Toast.LENGTH_LONG).show();
                return;
            }
            validatePermissions(Manifest.permission.CAMERA, imageUri, takePicture::launch);
        });

        viewModel.getUploadImageFromGalleryEvent().observe(getViewLifecycleOwner(), __ -> {
            validatePermissions(Manifest.permission.READ_EXTERNAL_STORAGE, "image/*", pickPicture::launch);
        });

        viewModel.getTitleIsValid().observe(getViewLifecycleOwner(), validity -> {
            BindingUtils.setErrorOnTextInputLayout(binding.handlePostTitle, validity, "Title cannot be empty");
        });
    }

    private Uri createImageFile() throws IOException {
        File imagePath = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String timeStamp = String.valueOf(System.currentTimeMillis());
        File post = File.createTempFile(timeStamp, ".jpg", imagePath);

        return getUriForFile(getContext(), "ca.dal.bartertrader", post);
    }

    private final ActivityResultLauncher<String> requestPermissions = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
        if (!isGranted) {
            Navigation.findNavController(getView()).popBackStack();
            Toast.makeText(getContext(), "Access Denied: Posts Require Permission!", Toast.LENGTH_SHORT).show();
        }
    });

    private final ActivityResultLauncher<String> pickPicture = registerForActivityResult(new ActivityResultContracts.GetContent(), image -> {
        BindingUtils.setImageUrOnImageView(binding.handlePostSelectedImage, image);
        viewModel.setImage(image);
    });

    private final ActivityResultLauncher<Uri> takePicture = registerForActivityResult(new ActivityResultContracts.TakePicture(), pictureTaken -> {
        if (pictureTaken) {
            BindingUtils.setImageUrOnImageView(binding.handlePostSelectedImage, imageUri);
            viewModel.setImage(imageUri);
        }
    });

    private <ContractInputT> void validatePermissions(String permission, ContractInputT input, Consumer<ContractInputT> contract) {
        if (getContext().checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED) {
            contract.accept(input);
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            Toast.makeText(getContext(), "Access Denied: Permissions Required!", Toast.LENGTH_SHORT).show();
        } else {
            requestPermissions.launch(permission);
        }
    }
}
