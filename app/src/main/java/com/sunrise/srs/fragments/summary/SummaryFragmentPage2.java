package com.sunrise.srs.fragments.summary;

import android.widget.TextView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.GlobalSetting;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseFragment;
import com.sunrise.srs.bean.Level;
import com.sunrise.srs.bean.WorkOut;
import com.sunrise.srs.utils.LanguageUtils;
import com.sunrise.srs.utils.TextUtils;
import com.sunrise.srs.views.LineChat;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by ChuHui on 2017/9/12.
 */

public class SummaryFragmentPage2 extends BaseFragment {


    @BindView(R.id.summary_fragment2_lineChat)
    LineChat lineChat;

    @BindView(R.id.summary_fragment2_avg_value)
    TextView avgValue;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_summary_page_2;
    }

    @Override
    public void recycleObject() {
        avgValue = null;
        lineChat.recycle();
        lineChat = null;
    }

    @Override
    protected void setTextStyle() {
        List<TextView> txtList = new ArrayList<>();
        txtList.add((TextView) parentView.findViewById(R.id.summary_fragment2_hint));
        txtList.add((TextView) parentView.findViewById(R.id.summary_fragment2_avg));
        txtList.add((TextView) parentView.findViewById(R.id.summary_fragment2_avg_unit));
        txtList.add(avgValue);

        if (GlobalSetting.AppLanguage.equals(LanguageUtils.zh_CN)) {
            TextUtils.setTextTypeFace(txtList, TextUtils.Microsoft());
        } else {
            TextUtils.setTextTypeFace(txtList, TextUtils.Arial());
        }
        txtList.clear();
        txtList = null;
    }

    private long avgLevel = 0L;

    @Override
    protected void init() {
        WorkOut workOutInfo = getActivity().getIntent().getParcelableExtra(Constant.WORK_OUT_INFO);
        if (workOutInfo.getRunningLevelCount() <= 0) {
            return;
        }
        List<Level> levels = workOutInfo.getLevelList();
        List<Integer> list = new ArrayList<>();
        avgLevel = 0L;
        for (int i = 0; i < workOutInfo.getRunningLevelCount(); i++) {
            avgLevel = avgLevel + levels.get(i).getLevel();
        }
        avgLevel = avgLevel / workOutInfo.getRunningLevelCount();

        for (int i = 0; i < workOutInfo.getRunningLevelCount(); i++) {
            list.add(levels.get(i).getLevel());
        }

        lineChat.setData(list);
    }

    private int loadTimes = -1;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (loadTimes == -1) {
                lineChat.reFlashView();
                avgValue.setText(avgLevel + "");
                loadTimes = 1;
            }
        }
    }
}
