package com.honganjk.ynybzbizfood.view.store.home.adapter;

import android.content.Context;

import com.honganjk.ynybzbizfood.R;
import com.honganjk.ynybzbizfood.code.base.baseadapter.ViewHolder;
import com.honganjk.ynybzbizfood.code.base.baseadapter.recyclerview.CommonAdapter;
import com.honganjk.ynybzbizfood.code.base.view.activity.BaseActivity;
import com.honganjk.ynybzbizfood.mode.javabean.store.home.StoreHomeData;

import java.util.List;

/**
 * 说明:
 * 作者： 杨阳; 创建于：  2017-06-29  15:41
 */
public class StoreHomeAdapter extends CommonAdapter<StoreHomeData.ObjsBean> {
    BaseActivity mContext;

    public StoreHomeAdapter(BaseActivity context, List<StoreHomeData.ObjsBean> datas) {
        super(context, R.layout.store_item_home, datas);
        mContext = context;
    }

    private StoreHomeAdapter(Context context, int layoutId, List<StoreHomeData.ObjsBean> datas) {
        super(context, layoutId, datas);
    }

    @Override
    public void convert(ViewHolder holder, final StoreHomeData.ObjsBean data) {
        holder.setImageBitmapRound(R.id.picture, data.getImg(), 5);
//        holder.setImageResource(R.id.picture, R.mipmap.test_picture);
        holder.setText(R.id.name, data.getFeature());
        holder.setText(R.id.price, data.getMoney());
    }
}
