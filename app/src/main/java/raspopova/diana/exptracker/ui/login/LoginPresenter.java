package raspopova.diana.exptracker.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.util.Patterns;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import raspopova.diana.exptracker.app.Config;
import raspopova.diana.exptracker.utils.TokenHelper;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

class LoginPresenter extends MvpBasePresenter<ILoginView> implements GoogleApiClient.OnConnectionFailedListener {

    private static final Integer RC_SIGN_IN = 456;
    private GoogleApiClient mGoogleApiClient;
    private Activity context;

    LoginPresenter(Activity context) {
        this.context = context;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage((FragmentActivity) context, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    void onGoogleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        context.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    void onRegistration(String email, String password) {
        if (validate(email, password)) {
            String token = TokenHelper.createToken(password, email);
            saveAuthGoNext(token);
        }
    }

    private boolean validate(String email, String password) {
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            getView().emailValidationError();
            return false;
        } else if (password.isEmpty() || password.length() < 6) {
            getView().passwordValidationError();
            return false;
        }
        return true;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        getView().showError(connectionResult.getErrorMessage(), connectionResult.getErrorCode());
    }

    void onGoogleSignInResult(int requestCode, int resultCode, Intent data) {
        // Result returned from launching the Intent from
        //   GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                // Get account information
                String token = TokenHelper.createToken(acct.getId(), acct.getEmail());
                saveAuthGoNext(token);
            }
        }
    }

    private void saveAuthGoNext(String token) {
        Config.setAuthorizationToken(token);
        getView().onLoginSuccess();
    }
}
