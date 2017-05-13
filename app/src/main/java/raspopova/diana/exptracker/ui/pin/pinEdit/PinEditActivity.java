package raspopova.diana.exptracker.ui.pin.pinEdit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState;
import com.hannesdorfmann.mosby3.mvp.viewstate.ViewState;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import raspopova.diana.exptracker.R;
import raspopova.diana.exptracker.base.GeneralActivity;
import raspopova.diana.exptracker.ui.chart.ChartActivity;

/**
 * Created by Diana.Raspopova on 5/9/2017.
 */

public class PinEditActivity extends GeneralActivity<IPinEditView, PinEditPresenter, PinEditViewState> implements IPinEditView {


    @BindView(R.id.indicator1)
    ImageView indicator1;
    @BindView(R.id.indicator2)
    ImageView indicator2;
    @BindView(R.id.indicator3)
    ImageView indicator3;
    @BindView(R.id.indicator4)
    ImageView indicator4;

    @BindView(R.id.key0)
    Button key0;
    @BindView(R.id.key1)
    Button key1;
    @BindView(R.id.key2)
    Button key2;
    @BindView(R.id.key3)
    Button key3;
    @BindView(R.id.key4)
    Button key4;
    @BindView(R.id.key5)
    Button key5;
    @BindView(R.id.key6)
    Button key6;
    @BindView(R.id.key7)
    Button key7;
    @BindView(R.id.key8)
    Button key8;
    @BindView(R.id.key9)
    Button key9;
    @BindView(R.id.keyDel)
    ImageButton keyDel;
    @BindView(R.id.keyBackspace)
    ImageButton keyBackspace;
    @BindView(R.id.forgetButton)
    Button forgetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_enter);
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewState.setPin(presenter.getPin());
    }

    @NonNull
    @Override
    public PinEditPresenter createPresenter() {
        return new PinEditPresenter();
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
        showErrorSnackBar(message);
    }

    @NonNull
    @Override
    public PinEditViewState createViewState() {
        return new PinEditViewState();
    }

    @Override
    public void onNewViewStateInstance() {

    }


    @OnClick(R.id.keyBackspace)
    void onBackspaceButtonClick() {
        presenter.onSymbolDelete();
    }

    @OnClick(R.id.keyDel)
    void onResetButtonClick() {
        presenter.onReset();
    }

    @OnClick({R.id.key0, R.id.key1, R.id.key2, R.id.key3, R.id.key4, R.id.key5, R.id.key6,
            R.id.key7, R.id.key8, R.id.key9})
    void onSymbolButtonClick(View view) {
        switch (view.getId()) {
            case R.id.key0:
                presenter.onSymbolAdd("0");
                break;
            case R.id.key1:
                presenter.onSymbolAdd("1");
                break;
            case R.id.key2:
                presenter.onSymbolAdd("2");
                break;
            case R.id.key3:
                presenter.onSymbolAdd("3");
                break;
            case R.id.key4:
                presenter.onSymbolAdd("4");
                break;
            case R.id.key5:
                presenter.onSymbolAdd("5");
                break;
            case R.id.key6:
                presenter.onSymbolAdd("6");
                break;
            case R.id.key7:
                presenter.onSymbolAdd("7");
                break;
            case R.id.key8:
                presenter.onSymbolAdd("8");
                break;
            case R.id.key9:
                presenter.onSymbolAdd("9");
                break;

        }
    }

    @Override
    public void fillView(String pin) {
        presenter.onRestorePinState();
    }

    @Override
    public void showEmptyPin() {
        indicator1.setBackgroundResource(R.drawable.pin_indicator_empty);
        indicator2.setBackgroundResource(R.drawable.pin_indicator_empty);
        indicator3.setBackgroundResource(R.drawable.pin_indicator_empty);
        indicator4.setBackgroundResource(R.drawable.pin_indicator_empty);
    }

    @Override
    public void showFullPin() {
        indicator1.setBackgroundResource(R.drawable.pin_indicator_full);
        indicator2.setBackgroundResource(R.drawable.pin_indicator_full);
        indicator3.setBackgroundResource(R.drawable.pin_indicator_full);
        indicator4.setBackgroundResource(R.drawable.pin_indicator_full);
    }

    @Override
    public void showOneItemPinSelected() {
        indicator1.setBackgroundResource(R.drawable.pin_indicator_full);
        indicator2.setBackgroundResource(R.drawable.pin_indicator_empty);
        indicator3.setBackgroundResource(R.drawable.pin_indicator_empty);
        indicator4.setBackgroundResource(R.drawable.pin_indicator_empty);
    }

    @Override
    public void showTwoItemPinSelected() {
        indicator1.setBackgroundResource(R.drawable.pin_indicator_full);
        indicator2.setBackgroundResource(R.drawable.pin_indicator_full);
        indicator3.setBackgroundResource(R.drawable.pin_indicator_empty);
        indicator4.setBackgroundResource(R.drawable.pin_indicator_empty);
    }

    @Override
    public void showThreeItemPinSelected() {
        indicator1.setBackgroundResource(R.drawable.pin_indicator_full);
        indicator2.setBackgroundResource(R.drawable.pin_indicator_full);
        indicator3.setBackgroundResource(R.drawable.pin_indicator_full);
        indicator4.setBackgroundResource(R.drawable.pin_indicator_empty);
    }

    @Override
    public void onPinSuccess() {
        startActivity(ChartActivity.class, true);
    }

    @OnClick(R.id.forgetButton)
    void onPinForget() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.enter_pin_title)
                .setMessage(R.string.dialog_pin_forget_message)
                .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        presenter.pinForget();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, null);
        builder.create();
        builder.show();
    }
}
