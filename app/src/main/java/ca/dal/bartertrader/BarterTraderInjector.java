package ca.dal.bartertrader;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import ca.dal.bartertrader.data.data_source.FirebaseAuthDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseFirestoreDataSource;
import ca.dal.bartertrader.data.data_source.FirebaseStorageDataSource;
import ca.dal.bartertrader.data.repository.FirebasePostsRepository;
import ca.dal.bartertrader.data.repository.FirebaseUserRepository;
import ca.dal.bartertrader.di.data_source.FirebaseAuthDataSourceFactory;
import ca.dal.bartertrader.di.data_source.FirebaseFirestoreDataSourceFactory;
import ca.dal.bartertrader.di.data_source.FirebaseStorageDataSourceFactory;
import ca.dal.bartertrader.di.fragment.CustomFragmentFactory;
import ca.dal.bartertrader.di.repository.FirebasePostsRepositoryFactory;
import ca.dal.bartertrader.di.repository.FirebaseUserRepositoryFactory;
import ca.dal.bartertrader.di.use_case.GetPostsUseCaseFactory;
import ca.dal.bartertrader.di.use_case.LoginUseCaseFactory;
import ca.dal.bartertrader.di.use_case.RegisterUseCaseFactory;
import ca.dal.bartertrader.di.use_case.ResetPasswordUseCaseFactory;
import ca.dal.bartertrader.di.use_case.SetPostUseCaseFactory;
import ca.dal.bartertrader.di.use_case.VerifyEmailExistsUseCaseFactory;
import ca.dal.bartertrader.di.view_model.LoginViewModelFactory;
import ca.dal.bartertrader.di.view_model.PasswordResetViewModelFactory;
import ca.dal.bartertrader.di.view_model.RegistrationViewModelFactory;
import ca.dal.bartertrader.di.view_model.provider_home.ProviderHomeViewModelFactory;
import ca.dal.bartertrader.domain.use_case.GetPostsUseCase;
import ca.dal.bartertrader.domain.use_case.LoginUserUseCase;
import ca.dal.bartertrader.domain.use_case.RegisterUseUseCase;
import ca.dal.bartertrader.domain.use_case.ResetPasswordUseCase;
import ca.dal.bartertrader.domain.use_case.SetPostBaseUseCase;

public class BarterTraderInjector {

    // Database References
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    private final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    // Data Sources
    private final FirebaseAuthDataSource firebaseAuthDataSource = new FirebaseAuthDataSourceFactory(firebaseAuth).create();
    private final FirebaseStorageDataSource firebaseStorageDataSource = new FirebaseStorageDataSourceFactory(firebaseStorage).create();
    private final FirebaseFirestoreDataSource firebaseFirestoreDataSource = new FirebaseFirestoreDataSourceFactory(firebaseFirestore).create();

    // Repositories
    private final FirebaseUserRepository firebaseUserRepository= new FirebaseUserRepositoryFactory(firebaseAuthDataSource, firebaseFirestoreDataSource).create();
    private final FirebasePostsRepository firebasePostsRepository = new FirebasePostsRepositoryFactory(firebaseStorageDataSource, firebaseFirestoreDataSource).create();

    // Use Cases for User Repository
    private final LoginUserUseCase loginUserUseCase = new LoginUseCaseFactory(firebaseUserRepository).create();
    private final RegisterUseUseCase registerUserUseCase = new RegisterUseCaseFactory(firebaseUserRepository).create();
    private final ResetPasswordUseCase resetPasswordUseCase = new ResetPasswordUseCaseFactory(firebaseUserRepository).create();
    private final ca.dal.bartertrader.domain.use_case.VerifyEmailExistsUseCase verifyEmailExistsUseCase = new VerifyEmailExistsUseCaseFactory(firebaseUserRepository).create();

    // Use Cases for Post Repository
    private final SetPostBaseUseCase setPostUseCase = new SetPostUseCaseFactory(firebasePostsRepository).create();
    private final GetPostsUseCase getPostsUseCase = new GetPostsUseCaseFactory(firebasePostsRepository).create();

    // View Model Factories
    private final LoginViewModelFactory loginViewModelFactory = new LoginViewModelFactory(loginUserUseCase);
    private final RegistrationViewModelFactory registrationViewModelFactory= new RegistrationViewModelFactory(registerUserUseCase, verifyEmailExistsUseCase);
    private final PasswordResetViewModelFactory passwordResetViewModelFactory = new PasswordResetViewModelFactory(resetPasswordUseCase);
    private final ProviderHomeViewModelFactory  providerHomeViewModelFactory = new ProviderHomeViewModelFactory(getPostsUseCase);

    // Fragment Factories
    public final CustomFragmentFactory customFragmentFactory = new CustomFragmentFactory(loginViewModelFactory, registrationViewModelFactory, passwordResetViewModelFactory, providerHomeViewModelFactory);
}
