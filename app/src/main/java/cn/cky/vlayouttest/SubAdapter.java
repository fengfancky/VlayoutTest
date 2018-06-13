package cn.cky.vlayouttest;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;

/**
 * Created by office on 2018/6/13.
 */

public class SubAdapter extends DelegateAdapter.Adapter<SubAdapter.MainViewHolder> {

    private Context mContext;
    private LayoutHelper mLayoutHelper;
    private VirtualLayoutManager.LayoutParams mLayoutParams;
    private ItemBean itemBean;
    private int mCount = 0;
    RecyclerView.RecycledViewPool viewPool;


    public SubAdapter(Context mContext,LayoutHelper mLayoutHelper,VirtualLayoutManager.LayoutParams mLayoutParams,int count,ItemBean itemBean,RecyclerView.RecycledViewPool viewPool){
        this.mContext = mContext;
        this.mLayoutHelper = mLayoutHelper;
        this.mLayoutParams = mLayoutParams;
        this.mCount = count;
        this.itemBean = itemBean;
        this.viewPool = viewPool;
    }


    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        MainViewHolder mainViewHolder =null;

        if (viewType == TypeUtils.BANNER_TYPE){
            mainViewHolder = new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.view_pager, parent, false));
        }else if (viewType == TypeUtils.TITEL_TYPE){

        }else if (viewType == TypeUtils.CONNECT_TYPE){

        }else {

        }
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        holder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(mLayoutParams));

        if (itemBean.getType().equals(TypeUtils.BANNER)){
            if (holder.itemView instanceof ViewPager) {
                ViewPager viewPager = (ViewPager) holder.itemView;
                viewPager.setAdapter(new PagerAdapter(this,viewPool));
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (itemBean.getType().equals(TypeUtils.BANNER)){
            return TypeUtils.BANNER_TYPE;
        }else if (itemBean.getType().equals(TypeUtils.TITLE)){
            return TypeUtils.TITEL_TYPE;
        }else if (itemBean.getType().equals(TypeUtils.CONTENT)){
            return TypeUtils.CONNECT_TYPE;
        }else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {

        public static volatile int existing = 0;
        public static int createdTimes = 0;

        public MainViewHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }

        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }
}
