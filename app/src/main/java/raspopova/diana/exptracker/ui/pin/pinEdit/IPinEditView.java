package raspopova.diana.exptracker.ui.pin.pinEdit;

import raspopova.diana.exptracker.base.IGeneralView;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public interface IPinEditView extends IGeneralView {
    void fillView(String pin);

    void showEmptyPin();

    void showFullPin();

    void showOneItemPinSelected();

    void showTwoItemPinSelected();

    void showThreeItemPinSelected();

    void onPinSuccess();

}
