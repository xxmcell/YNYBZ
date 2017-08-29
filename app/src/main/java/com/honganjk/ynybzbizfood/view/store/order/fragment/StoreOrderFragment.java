package com.honganjk.ynybzbizfood.view.store.order.fragment;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.Global;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.click.OnItemClickListener;
import com.honganjk.ynybzbizfood.code.base.view.fragment.BaseListFragment;
import com.honganjk.ynybzbizfood.mode.javabean.store.order.StoreOrderData;
import com.honganjk.ynybzbizfood.pressenter.store.order.StoreOrderPresenter;
import com.honganjk.ynybzbizfood.utils.ui.divider.HorizontalDividerItemDecoration;
import com.honganjk.ynybzbizfood.view.store.order.activity.StoreOrderDetailsActivity;
import com.honganjk.ynybzbizfood.view.store.order.adapter.StoreOrderAdapter;
import com.honganjk.ynybzbizfood.view.store.order.view.StoreOrderParentInterfaces;
import com.honganjk.ynybzbizfood.widget.empty_layout.ContextData;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * 说明:商城-我的订单
 * 作者： 杨阳; 创建于：  2017-07-11  15:27
 */
@SuppressLint("ValidFragment")
public class StoreOrderFragment extends BaseListFragment<StoreOrderParentInterfaces.IOrder, StoreOrderPresenter> implements StoreOrderParentInterfaces.IOrder {
    /**
     * 0:待支付; 1:待服务; 2:服务中;4:已完成;
     */
    private int mType;
    ArrayList<StoreOrderData.ObjsBean> mDatas = new ArrayList<>();
    StoreOrderAdapter adapter;

    private MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();
    private IntentFilter intentFilter = new IntentFilter();

    @SuppressLint("ValidFragment")
    public StoreOrderFragment() {
    }

    public StoreOrderFragment(int type) {
        this.mType = type;
    }

    public static StoreOrderFragment getInstance(int type) {
        return new StoreOrderFragment(type);
    }

    @Override
    public int getContentView() {
        return R.layout.widget_superrecyclerview;
    }

    @Override
    public void initView() {
        super.initView();
        intentFilter.addAction(Global.ST_ORDER_CANCEL);
        intentFilter.addAction(Global.ST_PAY_SUCCEED);
        intentFilter.addAction(Global.LOGIN_SUCCEED);
        activity.registerReceiver(myBroadcastReceiver, intentFilter);

        presenter.getHttpData(0, true, mType);

        adapter.setOnItemClickListener(new OnItemClickListener< StoreOrderData.ObjsBean>() {
            @Override
            public void onItemClick(ViewGroup parent, View view,  StoreOrderData.ObjsBean data, int position) {
                StoreOrderDetailsActivity.starUi(StoreOrderFragment.this, REQUEST_CODE,data);
            }
        });
    }

    @Override
    public CommonAdapter getAdapter() {
        return adapter = new StoreOrderAdapter(this, mDatas);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            //详情页面操作后列表页要刷新
            if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
                if (data.getBooleanExtra("isRefresh", false)) {
                    presenter.getHttpData(0, true, mType);
                }
            }
        }
        //评价成功
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK + 1) {
            presenter.getHttpData(0, true, mType);
        }
    }

    @Override
    public RecyclerView.ItemDecoration getItemDecoration() {
        return new HorizontalDividerItemDecoration.Builder(activity).colorResId(R.color.gray_ee).sizeResId(R.dimen.dp_10).build();
    }

    @Override
    public void onRefresh() {
        presenter.getHttpData(0, true, mType);
    }

    @Override
    public StoreOrderPresenter initPressenter() {
        return new StoreOrderPresenter(REQUEST_CODE);
    }

    @Override
    public void getHttpData(int total, List<StoreOrderData.ObjsBean> datas) {
        mDatas.addAll(datas);
        adapter.notifyDataSetChanged();
        // 0:待支付; 1:待服务; 2:服务中;4:已完成;
        int postiont = mType == 1 ? 0 : mType == 2 ? 1 : 2;
//        ((OrderActivity) activity).setCount(postiont, mDatas.size());
    }

    @Override
    public void confirmCompleted(boolean data) {
        presenter.getHttpData(0, true, mType);
    }

    @Override
    public void deleteOrder(boolean data) {
        presenter.getHttpData(0, true, mType);
        showInforSnackbar("删除成功");
    }

    @Override
    public void cancleOrder(boolean data) {
        presenter.getHttpData(0, true, mType);
        showInforSnackbar("取消成功成功");
    }

    @Override
    public void clearData() {
        mDatas.clear();
    }

    @Override
    public void onLoadding() {
        presenter.getHttpData(0, false, mType);
    }

    @Override
    public void onRetryClick(ContextData data) {
        presenter.getHttpData(0, true, mType);
    }

    @Override
    public void onEmptyClick(ContextData data) {
        presenter.getHttpData(0, true, mType);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activity.unregisterReceiver(myBroadcastReceiver);
    }

    /**
     * 显示不同的订单状态
     */
    public void setOrderStatus(int type) {
        presenter.getHttpData(type, true, mType);
    }

    class MyBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //取消订单成功
            if (intent.getAction().equals(Global.ST_ORDER_CANCEL)) {
                presenter.getHttpData(0, true, mType);
            }
            //支付订单成功
            if (intent.getAction().equals(Global.ST_PAY_SUCCEED)) {
                presenter.getHttpData(0, true, mType);
            }

            //登录成功成功
            if (intent.getAction().equals(Global.LOGIN_SUCCEED)) {
                presenter.getHttpData(0, true, mType);
            }

        }
    }
}