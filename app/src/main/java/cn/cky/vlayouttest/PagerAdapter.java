package cn.cky.vlayouttest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.RecyclablePagerAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.bumptech.glide.Glide;

/**
 * Created by office on 2018/6/13.
 */

public class PagerAdapter extends RecyclablePagerAdapter<SubAdapter.MainViewHolder> {

    private Context context;
    private ItemBean itemBean;

    public PagerAdapter( Context context,SubAdapter adapter, RecyclerView.RecycledViewPool pool,ItemBean itemBean) {
        super(adapter, pool);
        this.context = context;
        this.itemBean = itemBean;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public void onBindViewHolder(SubAdapter.MainViewHolder viewHolder, int position) {
        // only vertical
        viewHolder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

//        int[] res = new int[]{R.mipmap.mountain,R.mipmap.sunset,R.mipmap.sunrise,R.mipmap.snow,R.mipmap.huangshan,};
        final ImageView imageView = ((ImageView) viewHolder.itemView.findViewById(R.id.img));

        Glide.with(context)
                .load(itemBean.getData()[position])
                .into(imageView);

//        imageView.setImageResource(res[position]);

        viewHolder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    v.bringToFront();
                    v.animate().scaleX(1.1f).scaleY(1.1f).setDuration(400).setInterpolator(new OvershootInterpolator()).start();
                    imageView.setBackgroundResource(R.drawable.bg_focus);
                }else {

                    v.animate().scaleX(1.0f).scaleY(1.0f).setDuration(400).setInterpolator(new OvershootInterpolator()).start();
                    imageView.setBackgroundResource(R.drawable.bg_normal);
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }
}
