package com.sunrise.srs.dialog.settings;

import com.sunrise.srs.R;
import com.sunrise.srs.activity.setting.SettingLockActivity;
import com.sunrise.srs.base.BaseDialogFragment;
import com.sunrise.srs.views.NumberKeyBoardView;

import butterknife.BindView;

/**
 * Created by ChuHui on 2017/9/16.
 */

public class SettingsLockPswDialog extends BaseDialogFragment {

    @BindView(R.id.dialog_settings_lock_key_board)
    NumberKeyBoardView keyBoardView;

    @Override
    public int getLayoutId() {
        return R.layout.dialog_settings_lock_customer_pass_word;
    }

    @Override
    public void recycleObject() {
        keyBoardView.recycle();
        keyBoardView = null;
    }

    @Override
    protected void init() {
        keyBoardView.setKeyBoardReturn((SettingLockActivity) getActivity());
    }
}
