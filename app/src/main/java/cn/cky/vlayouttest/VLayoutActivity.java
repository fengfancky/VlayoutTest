package cn.cky.vlayouttest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by office on 2018/6/13.
 */

public class VLayoutActivity extends AppCompatActivity {

    private static final String TAG = "VLayoutActivity";
    RecyclerViewTV recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vlayout_layout);
        recyclerView = (RecyclerViewTV) findViewById(R.id.vRv);

        recyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        },300);

    }

    private void initView(){

        //设置LayoutManager
        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //设置间距
//        RecyclerViewTV.ItemDecoration itemDecoration = new RecyclerView.ItemDecoration() {
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                outRect.set(10, 10, 10, 10);
//            }
//        };
//        recyclerView.addItemDecoration(itemDecoration);

        //对某种viewType设置复用池
        final RecyclerViewTV.RecycledViewPool viewPool = new RecyclerViewTV.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 20);

        final RecyclerViewTV.RecycledViewPool viewPool1 = new RecyclerViewTV.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool1);
        viewPool1.setMaxRecycledViews(1, 20);

        final RecyclerViewTV.RecycledViewPool viewPool2 = new RecyclerViewTV.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool2);
        viewPool2.setMaxRecycledViews(2, 20);

        final RecyclerViewTV.RecycledViewPool viewPool3 = new RecyclerViewTV.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool3);
        viewPool3.setMaxRecycledViews(3, 30);


        final DelegateAdapter delegateAdapter = new DelegateAdapter(layoutManager, true);
        recyclerView.setAdapter(delegateAdapter);
        final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

        //banner
        ItemBean itemBeanBanner = new ItemBean();
        itemBeanBanner.setType(TypeUtils.BANNER);
        adapters.add(new SubAdapter(this, new LinearLayoutHelper(), new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300), 1,itemBeanBanner,viewPool1,onItemFocusListener));


        //推荐title
        ItemBean itemBeanTitle1 = new ItemBean();
        itemBeanTitle1.setType(TypeUtils.TITLE);
        itemBeanTitle1.setTitle("江山如此多娇");
        adapters.add(new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle1,viewPool2 ,null));

        //one row
        ItemBean itemBeanGrid1 = new ItemBean();
        itemBeanGrid1.setType(TypeUtils.CONTENT);
        itemBeanGrid1.setCount(2);
        itemBeanGrid1.setData(new int[]{R.mipmap.sunrise,R.mipmap.snow});
        final GridLayoutHelper helper = new GridLayoutHelper(2, 2);
        adapters.add(new SubAdapter(this,helper,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200),2,itemBeanGrid1,viewPool3,onItemFocusListener));

        //更多精彩影片
        ItemBean itemBeanTitle2 = new ItemBean();
        itemBeanTitle2.setType(TypeUtils.TITLE);
        itemBeanTitle2.setTitle("人物 - 不一样的烟火");
        adapters.add(new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle2,viewPool2 ,null));

        //one row 5
        ItemBean itemBeanGrid2 = new ItemBean();
        itemBeanGrid2.setType(TypeUtils.CONTENT);
        itemBeanGrid2.setCount(5);
        itemBeanGrid2.setData(new int[]{R.mipmap.person1,R.mipmap.person2,R.mipmap.person3,R.mipmap.person4,R.mipmap.person5});
        final GridLayoutHelper helper2 = new GridLayoutHelper(5, 5);
        adapters.add(new SubAdapter(this,helper2,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150),5,itemBeanGrid2,viewPool3,onItemFocusListener));

        //一周热映
        ItemBean itemBeanTitle3= new ItemBean();
        itemBeanTitle3.setType(TypeUtils.TITLE);
        itemBeanTitle3.setTitle("建筑 - 智慧与艺术的结合");
        adapters.add(new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle3,viewPool2,null));

        //two row 10
        ItemBean itemBeanGrid3 = new ItemBean();
        itemBeanGrid3.setType(TypeUtils.CONTENT);
        itemBeanGrid3.setCount(6);
        itemBeanGrid3.setData(new int[]{R.mipmap.build1,R.mipmap.build2,R.mipmap.build3,R.mipmap.build4,R.mipmap.build5,R.mipmap.build6,R.mipmap.build7});
        final GridLayoutHelper helper3 = new GridLayoutHelper(5, 7);
        adapters.add(new SubAdapter(this,helper3,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300),7,itemBeanGrid3,viewPool3,onItemFocusListener));

        //热播动漫
        ItemBean itemBeanTitle4= new ItemBean();
        itemBeanTitle4.setType(TypeUtils.TITLE);
        itemBeanTitle4.setTitle("风景 - 向往的诗与远方");
        adapters.add(new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle4,viewPool2,null));


        ItemBean itemBeanGrid4= new ItemBean();
        itemBeanGrid4.setType(TypeUtils.CONTENT);
        itemBeanGrid4.setCount(5);
        itemBeanGrid4.setData(new int[]{R.mipmap.bea1,R.mipmap.bea2,R.mipmap.bea3,R.mipmap.bea4,R.mipmap.bea5});
        final GridLayoutHelper helper4 = new GridLayoutHelper(5, 5);
        adapters.add(new SubAdapter(this,helper4,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300),5,itemBeanGrid4,viewPool3,onItemFocusListener));

        //精彩热播剧
        ItemBean itemBeanTitle5= new ItemBean();
        itemBeanTitle5.setType(TypeUtils.TITLE);
        itemBeanTitle5.setTitle("精彩热播剧");
        adapters.add(new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle5,viewPool2,null));

        ItemBean itemBeanGrid5= new ItemBean();
        itemBeanGrid5.setType(TypeUtils.CONTENT);
        itemBeanGrid5.setCount(10);
        final GridLayoutHelper helper5 = new GridLayoutHelper(5, 5);
        adapters.add(new SubAdapter(this,helper5,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300),5,itemBeanGrid5,viewPool3,onItemFocusListener));


        //世界那么大 - 我想去看看
        ItemBean itemBeanTitle6= new ItemBean();
        itemBeanTitle6.setType(TypeUtils.TITLE);
        itemBeanTitle6.setTitle("世界那么大 - 我想去看看");
        adapters.add(new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle6,viewPool2,null));


        //一拖N
        ItemBean itemBeanGrid6= new ItemBean();
        itemBeanGrid6.setType(TypeUtils.CONTENT);
        itemBeanGrid6.setCount(10);

        OnePlusNLayoutHelper helper6 = new OnePlusNLayoutHelper();
        helper6.setAspectRatio(4.0f);
        helper6.setColWeights(new float[]{50f});
        helper6.setRowWeight(50f);
        helper6.setMargin(10, 10, 10, 10);
        helper6.setPadding(10, 10, 10, 10);
        adapters.add(new SubAdapter(this, helper6, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),4,itemBeanGrid6,viewPool3,onItemFocusListener) );

        ItemBean itemBeanTitle7= new ItemBean();
        itemBeanTitle7.setType(TypeUtils.CONTENT);
        adapters.add(new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100),1,itemBeanTitle7,viewPool3,onItemFocusListener));

        delegateAdapter.setAdapters(adapters);
    }

    SubAdapter.OnItemFocusListener onItemFocusListener = new SubAdapter.OnItemFocusListener() {
        @Override
        public void itemFoucs(View view, View view1, boolean hasFocus) {
            if (hasFocus){
                view.animate().scaleY(1.1f).scaleX(1.1f).setInterpolator(new OvershootInterpolator()).setDuration(400).start();
                view1.setBackgroundResource(R.drawable.bg_focus);
            }else {
                view.animate().scaleY(1.0f).scaleX(1.0f).setInterpolator(new OvershootInterpolator()).setDuration(400).start();
                view1.setBackgroundResource(R.drawable.bg_normal);
            }
        }
    };

}
