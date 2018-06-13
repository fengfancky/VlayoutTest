package cn.cky.vlayouttest;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
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
        ((ImageView) viewHolder.itemView.findViewById(R.id.img)).setImageResource(R.mipmap.one);
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}
