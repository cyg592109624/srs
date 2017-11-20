package com.sunrise.srs.activity.home;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.widget.ImageView;

import com.sunrise.srs.Constant;
import com.sunrise.srs.R;
import com.sunrise.srs.base.BaseFragmentActivity;
import com.sunrise.srs.dialog.home.NfcDialog;
import com.sunrise.srs.utils.BitMapUtils;
import com.sunrise.srs.utils.ImageUtils;

import butterknife.BindView;

/**
 * Created by ChuHui on 2017/9/6.
 */

public class NfcActivity extends BaseFragmentActivity {
    @BindView(R.id.nfc_img)
    ImageView nfc;
    private Bitmap nfcBitmap;

    private boolean stopChange = false;

    private static final int CLEAR_SHOW = -1;
    private static final int CHANGE_IMG_1 = 1001;
    private static final int CHANGE_IMG_2 = 1002;
    private static final int STOP_CHANGE = 1003;
    private static final int SHOW_ERROR = 1004;
    private NfcDialog nfcDialog;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (nfcBitmap != null) {
                nfcBitmap.recycle();
            }
            switch (msg.what) {
                default:
                    break;
                case CLEAR_SHOW:
                    stopChange = true;
                    if (nfcDialog != null) {
                        nfcDialog.dismiss();
                    }
                    finishActivity();
                    break;
                case CHANGE_IMG_1:
                    if (!stopChange) {
                        nfcBitmap = BitMapUtils.loadMipMapResource(getResources(), R.mipmap.img_nfc_2);
                        ImageUtils.changeImageView(nfc, nfcBitmap);
                        mHandler.sendEmptyMessageDelayed(CHANGE_IMG_2, 500);
                    }
                    break;
                case CHANGE_IMG_2:
                    if (!stopChange) {
                        nfcBitmap = BitMapUtils.loadMipMapResource(getResources(), R.mipmap.img_nfc_1);
                        ImageUtils.changeImageView(nfc, nfcBitmap);
                        mHandler.sendEmptyMessageDelayed(CHANGE_IMG_1, 500);
                    }
                    break;
                case STOP_CHANGE:
                    stopChange = true;
                    break;
                case SHOW_ERROR:
                    stopChange = true;
                    nfcDialog = new NfcDialog();
                    nfcDialog.show(fragmentManager, Constant.TAG);
                    break;
            }
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_nfc;
    }

    @Override
    public void recycleObject() {
        if (nfcBitmap != null) {
            nfcBitmap.recycle();
            nfcBitmap = null;
        }
        nfc = null;
        mHandler = null;
        nfcDialog = null;
    }

    @Override
    protected void init() {
        mHandler.sendEmptyMessageDelayed(CHANGE_IMG_1, 1000);
        mHandler.sendEmptyMessageDelayed(STOP_CHANGE, 4000);
        mHandler.sendEmptyMessageDelayed(SHOW_ERROR, 7000);
        mHandler.sendEmptyMessageDelayed(CLEAR_SHOW, 10000);

    }
}
