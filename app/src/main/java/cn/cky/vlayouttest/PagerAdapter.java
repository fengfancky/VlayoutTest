package cn.cky.vlayouttest;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.vlayout.RecyclablePagerAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

/**
 * Created by office on 2018/6/13.
 */

public class PagerAdapter extends RecyclablePagerAdapter<SubAdapter.MainViewHolder> {

    public PagerAdapter(SubAdapter adapter, RecyclerView.RecycledViewPool pool) {
        super(adapter, pool);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public void onBindViewHolder(SubAdapter.MainViewHolder viewHolder, int position) {
        // only vertical
        viewHolder.itemView.setLayoutParams(new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        int[] res = new int[]{R.mipmap.mountain,R.mipmap.sunset,R.mipmap.landscape};
        final ImageView imageView = ((ImageView) viewHolder.itemView.findViewById(R.id.img));
        imageView.setImageResource(res[position]);

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
