package com.nahuo.library.widget.popwindow;

import android.app.Activity;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;

import com.nahuo.library.R;
import com.nahuo.library.utils.DisplayUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 垂直列表弹出窗，类似于标题栏点击menu弹出
 * Created by JorsonWong on 2015/7/22.
 */
public class PoplList extends PopupWindow implements OnItemClickListener {

    private Activity mActivity;
    private View mRootView;
    private ListView mListView;
    private OnItemClickListener mMenuItemClickListener;
    private List<VerticalPopMenuItem> mVerticalMenuItems = new ArrayList<VerticalPopMenuItem>();
    private ImageView mIconArrowTop, mIconArrowBottom;
    private static final int WINDOW_WIDTH = 150;                                                 // dip

    public PoplList(Activity activity) {
        super();
        this.mActivity = activity;
        initViews();
    }

    private void initViews() {
        mRootView = mActivity.getLayoutInflater().inflate(R.layout.pop_list, null);
        mListView = (ListView) mRootView.findViewById(android.R.id.list);
        mIconArrowTop = (ImageView) mRootView.findViewById(android.R.id.icon1);
        mIconArrowBottom = (ImageView) mRootView.findViewById(android.R.id.icon2);

        mListView.setOnItemClickListener(this);

        this.setWidth(DisplayUtil.dip2px(mActivity, WINDOW_WIDTH));
        this.setHeight(LayoutParams.WRAP_CONTENT);
        this.setContentView(mRootView);
        this.setFocusable(true);

        mRootView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int[] location = new int[2];
                mRootView.getLocationOnScreen(location);
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    dismiss();
                }
                return true;
            }
        });
    }

    public void setDrawableDivider(int drawableResId) {
        mListView.setDivider(mActivity.getResources().getDrawable(drawableResId));
    }

    public PoplList addMenuItem(VerticalPopMenuItem menuItem) {
        mVerticalMenuItems.add(menuItem);
        return this;
    }

    public PoplList addMenuItems(List<VerticalPopMenuItem> items) {
        mVerticalMenuItems.addAll(items);
        return this;
    }

    private PoplList create() {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();

        for (VerticalPopMenuItem item : mVerticalMenuItems) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", item.itemText);
            map.put("icon", item.leftIconResId);
            map.put("icon2", item.rightIconResId);
            maps.add(map);
        }
        String[] from = new String[]{"text", "icon", "icon2"};
        int[] to = new int[]{android.R.id.text1, android.R.id.icon1, android.R.id.icon2};
        SimpleAdapter adapter = new SimpleAdapter(mActivity, maps, R.layout.pop_list_item, from, to);
        mListView.setAdapter(adapter);
        return this;
    }

    public PoplList setMenuItemClickListener(OnItemClickListener listener) {
        this.mMenuItemClickListener = listener;
        return this;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mMenuItemClickListener != null) {
            id = mVerticalMenuItems.get(position).id;
            mMenuItemClickListener.onItemClick(parent, view, position, id);
        }
        dismiss();
    }

    public void show(View anchor) {

        create();

        Display display = mActivity.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int[] mAnchorLocation = new int[2];
        anchor.getLocationOnScreen(mAnchorLocation);
        int paddingLeft = mRootView.getPaddingLeft();
        int paddingRight = mRootView.getPaddingRight();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(DisplayUtil.dip2px(mActivity, 30),
                DisplayUtil.dip2px(mActivity, 15));

        if (((2 * mAnchorLocation[0] + anchor.getWidth()) / 2 < width / 2)) {// left
            lp.setMargins(DisplayUtil.dip2px(mActivity, 5) + paddingLeft, 0, 0, 0);
        } else {// right
            int w = getWidth();
            lp.setMargins(w - DisplayUtil.dip2px(mActivity, 5) - DisplayUtil.dip2px(mActivity, 30) - paddingLeft
                    - paddingRight, 0, 0, 0);
        }

        mIconArrowTop.setLayoutParams(lp);
        // mRootView.setBackgroundResource(((2 * mAnchorLocation[0] + anchor
        // .getWidth()) / 2 < width / 2) ? R.drawable.dropdown_menu_left
        // : R.drawable.dropdown_menu_right);

        showAsDropDown(anchor);
    }

    public static class VerticalPopMenuItem {

        public int leftIconResId = 0;
        public int rightIconResId = 0;
        public String itemText;
        public int id;
        private Object tag;

        public VerticalPopMenuItem(String itemText) {
            this.itemText = itemText;
        }

        public VerticalPopMenuItem(int id, int leftIconResId, String itemText) {
            this.id = id;
            this.leftIconResId = leftIconResId;
            this.itemText = itemText;
        }

        public VerticalPopMenuItem(int leftIconResId, String itemText) {
            this(0, leftIconResId, itemText);
        }

        public VerticalPopMenuItem(int leftIconResId, String itemText, int rightIconResId) {
            this.leftIconResId = leftIconResId;
            this.itemText = itemText;
            this.rightIconResId = rightIconResId;
        }

        public Object getTag() {
            return tag;
        }

        public void setTag(Object tag) {
            this.tag = tag;
        }
    }

}
