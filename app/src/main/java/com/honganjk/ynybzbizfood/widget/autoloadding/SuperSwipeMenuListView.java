package com.honganjk.ynybzbizfood.widget.autoloadding;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.utils.other.DimensUtils;
import com.honganjk.ynybzbizfood.utils.ui.UiUtils;
import com.honganjk.ynybzbizfood.widget.empty_layout.OnRetryClickListion;
import com.honganjk.ynybzbizfood.widget.swipemenulistview.SwipeMenuListView;

import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.mode;

/**
 * Created by Administrator on 2016/4/28.
 */
public class SuperSwipeMenuListView extends RelativeLayout {
    @BindView(R.id.swipe)
    public SwipeRefreshLayout swipeView;
    @BindView(R.id.list_swipe_target)
    SwipeMenuListView swipeMenuListView;

    public SuperSwipeMenuListView(Context context) {
        this(context, null);
    }

    public SuperSwipeMenuListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SuperSwipeMenuListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode()) {
            initView();
        }

    }


    public boolean isRecycleView() {
        return mode == 2;
    }

    public boolean isListleView() {
        return mode == 2;
    }


    private void initView() {
        UiUtils.getInflaterView(getContext(), R.layout.base_swipe_swipelist, this, true);
        ButterKnife.bind(this, this);
        /**
         * 设置集合view的刷新view
         */
        swipeMenuListView.setSwipeRefreshLayout(swipeView);
        UiUtils.setColorSchemeResources(swipeView);

    }


    /**
     * 设置item点击事件 listView he GridViewForAutoLoadding
     *
     * @param onItemClickListen
     */
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListen) {
        swipeMenuListView.setOnItemClickListener(onItemClickListen);
    }


    /**
     * 设置加载更多的回调
     *
     * @param onLoaddingListener
     */
    public void setOnLoaddingListener(OnLoaddingListener onLoaddingListener) {
        swipeMenuListView.setOnLoaddingListener(onLoaddingListener);

    }

    /**
     * 设置下拉刷新的回调
     *
     * @param listener
     */
    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener listener) {
        if (swipeView != null) {
            swipeView.setOnRefreshListener(listener);
        }
    }

    /**
     * 设置适配器
     */

    public void setAdapter(BaseAdapter adapter) {
        swipeMenuListView.setAdapter(adapter);
    }

    public void setDividerHeight(float dp) {
        swipeMenuListView.setDividerHeight(DimensUtils.dip2px(getContext(), dp));
    }


    /**
     * 获取pagingHelp
     */
    public PagingHelp getPagingHelp() {
        return swipeMenuListView.getPagingHelp();
    }

    /**
     * 获取分页的有效数据
     */
    public <T> Collection<T> getValidData(Collection<T> c) {
        return getPagingHelp().getValidData(c);
    }

    /**
     * 获取容器view
     *
     * @return
     */
    public SwipeMenuListView getListView() {
        return swipeMenuListView;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeView;
    }


    public ConfigChang getConfigChang() {
        if (swipeMenuListView instanceof ConfigChang) {
            return (ConfigChang) swipeMenuListView;
        } else {
            return null;
        }
    }

    /**
     * 刷新Swp
     *
     * @return
     */
    public void setRefreshing(boolean refreshing) {
        swipeView.setRefreshing(refreshing);
    }


    public StatusChangListener getStatusChangListener() {
        return swipeMenuListView.getStatusChangListener();
    }

    public interface ListSwipeViewListener extends SwipeRefreshLayout.OnRefreshListener, OnLoaddingListener, OnRetryClickListion {

    }

    public interface ListViewListener extends OnLoaddingListener, OnRetryClickListion {

    }


}
