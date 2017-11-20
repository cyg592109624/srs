package com.sunrise.srs.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ChuHui on 2017/9/9.
 */

public abstract class BaseFragment extends Fragment {
    private Unbinder bind;
    public View parentView;

    public FragmentManager fragmentManager ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parentView = inflater.inflate(getLayoutId(), container, false);
        fragmentManager = getActivity().getSupportFragmentManager();

        return parentView;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind = ButterKnife.bind(this, view);
        setTextStyle();
        init();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recycleObject();
        bind.unbind();
        bind = null;
        fragmentManager = null;
        parentView = null;
        System.gc();
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

    protected void init() {
    }

}
