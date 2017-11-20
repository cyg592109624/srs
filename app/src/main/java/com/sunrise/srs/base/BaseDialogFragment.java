package com.sunrise.srs.base;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sunrise.srs.Constant;
import com.sunrise.srs.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChuHui on 2017/9/6.
 */

public abstract class BaseDialogFragment extends DialogFragment {

    private Unbinder bind;
    public View parentView;
    public Context fragmentContext;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getContext(), R.style.Dialog_No_Border);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutId(), container, false);
        changeSystemUiState();
        fragmentContext = ((BaseFragmentActivity) getActivity()).activityContext;
        Constant.TAG=getClass().getSimpleName();
        return parentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        setTextStyle();
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager windowManager = getActivity().getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        window.setLayout(dm.widthPixels, dm.heightPixels);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recycleObject();
        bind.unbind();
        bind = null;
        parentView = null;
        System.gc();
    }

    /**
     * 隐藏部分系统ui、部分button事件拦截
     */
    public void changeSystemUiState() {
        Dialog dialog = getDialog();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        dialog.setCancelable(false);


        final View decorView = dialog.getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                int uiOptions = -1;
                //隐藏虚拟按键，并且全屏
                uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav  bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE;
                decorView.setSystemUiVisibility(uiOptions);
            }
        });

    }

    /**
     * 返回布局ID 给onCreateView方法
     *
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 清空资源引用
     *
     * @return
     */
    public abstract void recycleObject();

    protected void setTextStyle() {
    }

    ;

    protected void init() {
    }

    ;


}
