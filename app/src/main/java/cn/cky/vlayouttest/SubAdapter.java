package cn.cky.vlayouttest;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;

import java.lang.reflect.Field;

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
    OnItemFocusListener onItemFocusListener;
    BannerScrollListener bannerScrollListener;

    public interface BannerScrollListener{
        void scroll(ViewPager viewPager,ItemBean itemBean);
    }

    public interface OnItemFocusListener{
        void itemFoucs(View view,View view1,boolean hasFocus);
    }


    public void setBannerScrollListener(BannerScrollListener bannerScrollListener) {
        this.bannerScrollListener = bannerScrollListener;
    }

    public SubAdapter(Context mContext, LayoutHelper mLayoutHelper, VirtualLayoutManager.LayoutParams mLayoutParams, int count, ItemBean itemBean, RecyclerView.RecycledViewPool viewPool, OnItemFocusListener onItemFocusListener){
        this.mContext = mContext;
        this.mLayoutHelper = mLayoutHelper;
        this.mLayoutParams = mLayoutParams;
        this.mCount = count;
        this.itemBean = itemBean;
        this.viewPool = viewPool;
        this.onItemFocusListener = onItemFocusListener;
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
            mainViewHolder = new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.title_layout, parent, false));
        }else if (viewType == TypeUtils.CONNECT_TYPE){
            mainViewHolder = new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.content_item_layout, parent, false));
        }else {
            mainViewHolder = new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.content_item_layout, parent, false));
        }
        return mainViewHolder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {

        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(mLayoutParams);
        layoutParams.setMargins(10,5,10,5);
        holder.itemView.setLayoutParams(layoutParams);

        if (itemBean.getType().equals(TypeUtils.BANNER)){

            ViewPager viewPager = (ViewPager) holder.itemView;

            viewPager.setAdapter(new PagerAdapter(mContext,this,viewPool,itemBean));
            viewPager.setCurrentItem(2);
            try {
                Field field = ViewPager.class.getDeclaredField("mScroller");
                field.setAccessible(true);
                FixedSpeedScroller scroller = new FixedSpeedScroller(viewPager.getContext(), new AccelerateDecelerateInterpolator());
                field.set(viewPager, scroller);
                scroller.setmDuration(500);
            } catch (Exception e) {

            }

            if (bannerScrollListener != null){
                bannerScrollListener.scroll(viewPager,itemBean);
            }

        }else if (itemBean.getType().equals(TypeUtils.TITLE)){
            ((TextView)(holder.itemView.findViewById(R.id.title))).setText(itemBean.getTitle()+"");
        }else {
            final ImageView imageView = ((ImageView)(holder.itemView.findViewById(R.id.img)));
            if (null == itemBean.getData()){
                Glide.with(mContext)
                        .load(R.mipmap.one)
                        .into(imageView);
            }else {
                Glide.with(mContext)
                        .load(itemBean.getData()[position])
                        .into(imageView);
            }

            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(final View v, boolean hasFocus) {
                    if (onItemFocusListener!=null){
                        onItemFocusListener.itemFoucs(v,imageView,hasFocus);
                    }
                }
            });

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
