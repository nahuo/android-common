package com.nahuo.library.widget.popwindow;

import android.app.Activity;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.nahuo.library.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description:底部弹出菜单,list形式，类似于上传头像时选择拍照和从相处选
 * Created by JorsonWong on 2015/7/22.
 */
public class PopListBottom extends PopupWindow implements OnItemClickListener, View.OnClickListener {

    private Activity mActivity;
    private View mRootView;
    private ListView mGridView;
    private OnItemClickListener mMenuItemClickListener;
    private LinearLayout mGridViewBg;
    private String[] mItemTexts;

    public PopListBottom(Activity activity) {
        super();
        this.mActivity = activity;
        initViews();
    }

    public PopListBottom(Activity activity, AttributeSet atrr) {
        super();
        this.mActivity = activity;
        initViews();
    }

    private void initViews() {
        mRootView = mActivity.getLayoutInflater().inflate(R.layout.pop_list_bottom, null);
        mRootView.setOnClickListener(this);
        mGridView = (ListView) mRootView.findViewById(android.R.id.list);
        mGridViewBg = (LinearLayout) mRootView.findViewById(android.R.id.content);
        mRootView.findViewById(android.R.id.button1).setOnClickListener(this);
        mGridView.setOnItemClickListener(this);

    }

    public PopListBottom setItems(String... items) {
        this.mItemTexts = items;
        return this;
    }

    private PopListBottom create() {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < mItemTexts.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", mItemTexts[i]);
            maps.add(map);
        }
        String[] from = new String[]{"text"};
        int[] to = new int[]{android.R.id.title};
        SimpleAdapter adapter = new SimpleAdapter(mActivity, maps, R.layout.pop_list_bottom_item, from, to);
        mGridView.setAdapter(adapter);

        return this;
    }

    /**
     * @description 显示菜单栏
     * @created 2015年3月20日 上午11:22:23
     * @author JorsonWong
     */
    public void show() {

        create();

        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);

        this.setContentView(mRootView);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x50000000);
        this.setBackgroundDrawable(dw);

        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);

        mGridViewBg.setVisibility(View.VISIBLE);
        mGridViewBg.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.trans_appear_from_bottom));
        setAnimationStyle(R.style.AlphaAnim);

    }

    public int getStatusHeight() {
        Rect frame = new Rect();
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusHeight = frame.top;
        return statusHeight;
    }

    /**
     * @description item click callback
     * @created 2015年3月20日 上午11:21:15
     * @author JorsonWong
     */
    public PopListBottom setOnMenuItemClickListener(OnItemClickListener listener) {
        this.mMenuItemClickListener = listener;
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mMenuItemClickListener != null) {
            mMenuItemClickListener.onItemClick(parent, view, position, id);
        }
        dismiss();
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void dismiss() {
        if (mGridViewBg.isShown()) {
            Animation anim = AnimationUtils.loadAnimation(mActivity, R.anim.trans_disappear_to_bottom);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    mGridViewBg.clearAnimation();
                    mGridViewBg.setVisibility(View.GONE);
                    dismiss();
                }
            }, anim.getDuration());
            mGridViewBg.startAnimation(anim);
        } else {
            super.dismiss();
        }
    }

}
