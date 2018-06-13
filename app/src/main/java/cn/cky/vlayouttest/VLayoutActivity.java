package cn.cky.vlayouttest;

import android.content.Context;
import android.graphics.Rect;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by office on 2018/6/13.
 */

public class VLayoutActivity extends AppCompatActivity {

    private static final String TAG = "VLayoutActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vlayout_layout);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.vRv);

        //设置LayoutManager
        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //设置间距
        RecyclerView.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(8, 8, 8, 8);
            }
        };
        recyclerView.addItemDecoration(itemDecoration);


        //对某种viewType设置复用池
        final RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        final RecyclerView.RecycledViewPool viewPool1 = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool1);
        viewPool1.setMaxRecycledViews(1, 10);

        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);
        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        ItemBean itemBeanBanner = new ItemBean();
        itemBeanBanner.setType(TypeUtils.BANNER);
        adapters.add(new SubAdapter(this, new LinearLayoutHelper(), new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300), 1,itemBeanBanner,viewPool1));
        delegateAdapter.setAdapters(adapters);

    }

}
