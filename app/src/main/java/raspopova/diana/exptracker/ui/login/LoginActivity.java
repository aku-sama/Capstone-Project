package raspopova.diana.exptracker.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.base.GeneralActivity;
import raspopova.diana.exptracker.ui.pin.pinSet.PinSetActivity;
import raspopova.diana.exptracker.utils.TextInputHelper;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class LoginActivity extends GeneralActivity<ILoginView, LoginPresenter> implements ILoginView {

    @BindView(R.id.emailInputLay)
    TextInputLayout emailInputLay;

    @BindView(R.id.passwordInputLay)
    TextInputLayout passwordInputLay;

    @BindView(R.id.googleSignInButton)
    SignInButton googleSignInButton;

    @BindView(R.id.registerButton)
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.googleSignInButton)
    void googleSignIn() {
        presenter.onGoogleSignIn();
    }

    @OnClick(R.id.registerButton)
    void registerClick() {
        presenter.onRegistration(TextInputHelper.getValue(emailInputLay), TextInputHelper.getValue(passwordInputLay));
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void showProgress() {
        showProgressView();
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void showError(String message, int... code) {
        showErrorSnackBar(message);
    }

    @Override
    public void showError(int message, int... code) {
        showErrorSnackBar(getString(message));
    }

    @Override
    public void emailValidationError() {
        TextInputHelper.showError(emailInputLay, R.string.login_email_error);
    }

    @Override
    public void passwordValidationError() {
        TextInputHelper.showError(emailInputLay, R.string.login_password_error);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        presenter.onGoogleSignInResult(requestCode,resultCode,data);
    }

    @Override
    public void onLoginSuccess() {
        startActivity(PinSetActivity.class);
    }
}
