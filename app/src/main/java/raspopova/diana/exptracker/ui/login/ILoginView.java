package raspopova.diana.exptracker.ui.login;

import raspopova.diana.exptracker.base.IGeneralView;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public interface ILoginView extends IGeneralView {

    void emailValidationError();

    void passwordValidationError();

    void onLoginSuccess();
}
