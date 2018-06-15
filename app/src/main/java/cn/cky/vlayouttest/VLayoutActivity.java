package cn.cky.vlayouttest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.ColumnLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.open.androidtvwidget.leanback.recycle.RecyclerViewTV;
import com.open.androidtvwidget.view.ReflectItemView;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * Created by office on 2018/6/13.
 */

public class VLayoutActivity extends AppCompatActivity {

    private static final String TAG = "VLayoutActivity";
    RecyclerViewTV recyclerView;

    private ViewPager viewPagerV;
    private ItemBean itemBeanV;

    SubAdapter subAdapter;
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

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                switch (newState){
//                    case SCROLL_STATE_IDLE: // The RecyclerView is not currently scrolling.
//                        //对于滚动不加载图片的尝试
//                        subAdapter.setScrolling(false);
//                        subAdapter.notifyDataSetChanged();
//                        break;
//                    case SCROLL_STATE_DRAGGING: // The RecyclerView is currently being dragged by outside input such as user touch input.
//                        subAdapter.setScrolling(false);
//                        break;
//                    case SCROLL_STATE_SETTLING: // The RecyclerView is currently animating to a final position while not under
//                        subAdapter.setScrolling(true);
//                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

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
        itemBeanBanner.setData(new int[]{R.mipmap.mountain,R.mipmap.sunset,R.mipmap.sunrise,R.mipmap.snow,R.mipmap.huangshan});
        subAdapter = new SubAdapter(this, new LinearLayoutHelper(), new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(300,this)), 1,itemBeanBanner,viewPool,onItemFocusListener);
        subAdapter.setBannerScrollListener(new SubAdapter.BannerScrollListener() {
            @Override
            public void scroll(final ViewPager viewPager, final ItemBean itemBean) {

                if (mHandler!=null){
                    mHandler.removeMessages(0);
                }

                viewPagerV = viewPager;
                itemBeanV = itemBean;

                mHandler.sendEmptyMessageDelayed(0,3000);

            }
        });
        adapters.add(subAdapter);


        //推荐title
        ItemBean itemBeanTitle1 = new ItemBean();
        itemBeanTitle1.setType(TypeUtils.TITLE);
        itemBeanTitle1.setTitle("江山如此多娇");
        subAdapter = new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle1,viewPool ,null);
        adapters.add(subAdapter);
        //one row
        ItemBean itemBeanGrid1 = new ItemBean();
        itemBeanGrid1.setType(TypeUtils.CONTENT);
        itemBeanGrid1.setCount(4);
        itemBeanGrid1.setData(new int[]{R.mipmap.sunrise,R.mipmap.snow,R.mipmap.huangshan,R.mipmap.gui});
        final GridLayoutHelper helper = new GridLayoutHelper(2, 4);
        subAdapter = new SubAdapter(this,helper,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(220,this)),4,itemBeanGrid1,viewPool,onItemFocusListener);

        adapters.add(subAdapter);
        //更多精彩影片
        ItemBean itemBeanTitle2 = new ItemBean();
        itemBeanTitle2.setType(TypeUtils.TITLE);
        itemBeanTitle2.setTitle("人物 - 不一样的烟火");
        subAdapter = new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle2,viewPool ,null);

        adapters.add(subAdapter);
        //one row 5
        ItemBean itemBeanGrid2 = new ItemBean();
        itemBeanGrid2.setType(TypeUtils.CONTENT);
        itemBeanGrid2.setCount(5);
        itemBeanGrid2.setData(new int[]{R.mipmap.person1,R.mipmap.person2,R.mipmap.person3,R.mipmap.person4,R.mipmap.person5});
        final GridLayoutHelper helper2 = new GridLayoutHelper(5, 5);
        subAdapter = new SubAdapter(this,helper2,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(150,this)),5,itemBeanGrid2,viewPool,onItemFocusListener);
        adapters.add(subAdapter);
        //一周热映
        ItemBean itemBeanTitle3= new ItemBean();
        itemBeanTitle3.setType(TypeUtils.TITLE);
        itemBeanTitle3.setTitle("建筑 - 智慧与艺术的结合");
        subAdapter = new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle3,viewPool,null);
        adapters.add(subAdapter);
        //two row 10
        ItemBean itemBeanGrid3 = new ItemBean();
        itemBeanGrid3.setType(TypeUtils.CONTENT);
        itemBeanGrid3.setCount(6);
        itemBeanGrid3.setData(new int[]{R.mipmap.build1,R.mipmap.build2,R.mipmap.build3,R.mipmap.build4,R.mipmap.build5,R.mipmap.build6,R.mipmap.build7});
        final GridLayoutHelper helper3 = new GridLayoutHelper(5, 7);
        subAdapter = new SubAdapter(this,helper3,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(300,this)),7,itemBeanGrid3,viewPool,onItemFocusListener);
        adapters.add(subAdapter);
        //热播动漫
        ItemBean itemBeanTitle4= new ItemBean();
        itemBeanTitle4.setType(TypeUtils.TITLE);
        itemBeanTitle4.setTitle("风景 - 向往的诗与远方");

        subAdapter = new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle4,viewPool,null);

        adapters.add(subAdapter);

        ItemBean itemBeanGrid4= new ItemBean();
        itemBeanGrid4.setType(TypeUtils.CONTENT);
        itemBeanGrid4.setCount(5);
        itemBeanGrid4.setData(new int[]{R.mipmap.bea1,R.mipmap.bea2,R.mipmap.bea3,R.mipmap.bea4,R.mipmap.bea5});
        final GridLayoutHelper helper4 = new GridLayoutHelper(5, 5);
        subAdapter = new SubAdapter(this,helper4,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(300,this)),5,itemBeanGrid4,viewPool,onItemFocusListener);

        adapters.add(subAdapter);
        //精彩热播剧
        ItemBean itemBeanTitle5= new ItemBean();
        itemBeanTitle5.setType(TypeUtils.TITLE);
        itemBeanTitle5.setTitle("美食 - 味蕾与心情的共舞");
        subAdapter = new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle5,viewPool,null);
        adapters.add(subAdapter);
        ItemBean itemBeanGrid5= new ItemBean();
        itemBeanGrid5.setType(TypeUtils.CONTENT);
        itemBeanGrid5.setCount(5);
        itemBeanGrid5.setData(new int[]{R.mipmap.food1,R.mipmap.food2,R.mipmap.food3,R.mipmap.food4,R.mipmap.food5});
        final GridLayoutHelper helper5 = new GridLayoutHelper(5, 5);
        subAdapter = new SubAdapter(this,helper5,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(300,this)),5,itemBeanGrid5,viewPool,onItemFocusListener);
        adapters.add(subAdapter);

        //世界那么大 - 去看看
        ItemBean itemBeanTitle6= new ItemBean();
        itemBeanTitle6.setType(TypeUtils.TITLE);
        itemBeanTitle6.setTitle("世界那么大 - 去看看");
        subAdapter = new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle6,viewPool,null);
        adapters.add(subAdapter);

        //一拖N
        ItemBean itemBeanGrid6= new ItemBean();
        itemBeanGrid6.setType(TypeUtils.CONTENT);
        itemBeanGrid6.setCount(4);
        itemBeanGrid6.setData(new int[]{R.mipmap.world1,R.mipmap.world2,R.mipmap.world3,R.mipmap.world4});

        OnePlusNLayoutHelper helper6 = new OnePlusNLayoutHelper();
        helper6.setAspectRatio(4.0f);
        helper6.setColWeights(new float[]{50f});
        helper6.setRowWeight(50f);
        helper6.setMargin(10, 10, 10, 10);
        subAdapter = new SubAdapter(this, helper6, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT),4,itemBeanGrid6,viewPool,onItemFocusListener);
        adapters.add(subAdapter);

        ItemBean itemBeanTitle7= new ItemBean();
        itemBeanTitle7.setType(TypeUtils.CONTENT);
        itemBeanTitle7.setData(new int[]{R.mipmap.world5});
        subAdapter = new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(250,this)),1,itemBeanTitle7,viewPool,onItemFocusListener);

        adapters.add(subAdapter);

        ItemBean itemBeanTitle8= new ItemBean();
        itemBeanTitle8.setType(TypeUtils.TITLE);
        itemBeanTitle8.setTitle("丰富多彩");
        subAdapter = new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle8,viewPool,null);
        adapters.add(subAdapter);
        ItemBean itemBeanGrid8= new ItemBean();
        itemBeanGrid8.setType(TypeUtils.CONTENT);
        itemBeanGrid8.setCount(20);
        itemBeanGrid8.setData(new int[]{
                R.mipmap.food1,R.mipmap.food2,R.mipmap.food3,R.mipmap.food4,R.mipmap.food5,
                R.mipmap.bea1,R.mipmap.bea2,R.mipmap.bea3,R.mipmap.bea4,R.mipmap.bea5,
                R.mipmap.food1,R.mipmap.food2,R.mipmap.food3,R.mipmap.food4,R.mipmap.food5,
                R.mipmap.bea1,R.mipmap.bea2,R.mipmap.bea3,R.mipmap.bea4,R.mipmap.bea5});
        final GridLayoutHelper helper8 = new GridLayoutHelper(5, 20);
        subAdapter = new SubAdapter(this,helper8,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(300,this)),20,itemBeanGrid8,viewPool,onItemFocusListener);

        adapters.add(subAdapter);
        ItemBean itemBeanTitle9= new ItemBean();
        itemBeanTitle9.setType(TypeUtils.TITLE);
        itemBeanTitle9.setTitle("随心看看");
        subAdapter = new SubAdapter(this,new SingleLayoutHelper(),new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT),1,itemBeanTitle9,viewPool,null);

        adapters.add(subAdapter);
        ItemBean itemBeanGrid9= new ItemBean();
        itemBeanGrid9.setType(TypeUtils.CONTENT);
        itemBeanGrid9.setCount(5);
        itemBeanGrid9.setData(new int[]{R.mipmap.sunrise,R.mipmap.bea2,R.mipmap.bea3,R.mipmap.bea4,R.mipmap.bea5});
        ColumnLayoutHelper columnLayoutHelper = new ColumnLayoutHelper();
        columnLayoutHelper.setWeights(new float[]{40.0f,15f,15f,15f,20f});
        subAdapter = new SubAdapter(this, columnLayoutHelper,new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(200,this)), 5,itemBeanGrid9,viewPool,onItemFocusListener);

        adapters.add(subAdapter);
        delegateAdapter.setAdapters(adapters);
    }

    SubAdapter.OnItemFocusListener onItemFocusListener = new SubAdapter.OnItemFocusListener() {
        @Override
        public void itemFoucs(View view, View view1, boolean hasFocus) {
            if (hasFocus){
//                flash(view);
                view.animate().scaleY(1.1f).scaleX(1.1f).setInterpolator(new OvershootInterpolator()).setDuration(400).start();
                view1.setBackgroundResource(R.drawable.bg_focus);
            }else {
                view.animate().scaleY(1.0f).scaleX(1.0f).setInterpolator(new OvershootInterpolator()).setDuration(400).start();
                view1.setBackgroundResource(R.drawable.bg_normal);
            }
        }
    };




    public int dip2px(float dpValue, Context context) {
        final float scale =getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public  int px2dip(Context context, float pxValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    private void flash(final View v){
        final ImageView imageView =new ImageView(this);
        FrameLayout.LayoutParams layoutParams =new FrameLayout.LayoutParams(v.getWidth()*2,v.getHeight());
        imageView.setImageResource(R.mipmap.focus_hl);
        layoutParams.gravity = Gravity.LEFT|Gravity.TOP;
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setLayoutParams(layoutParams);
        ((CardView) v).addView(imageView);
        imageView.bringToFront();

        ObjectAnimator objectAnimatorX =ObjectAnimator.ofFloat(imageView,"X",-v.getWidth(),v.getWidth());
        objectAnimatorX.setDuration(800);
        objectAnimatorX.start();
        objectAnimatorX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                ((CardView) v).removeView(imageView);
            }
        });
    }


    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == 0){
                if (itemBeanV == null || viewPagerV == null){
                    return false;
                }
                if (viewPagerV.getCurrentItem() == itemBeanV.getData().length-1){
                    viewPagerV.setCurrentItem(0);
                }else {
                    viewPagerV.setCurrentItem(viewPagerV.getCurrentItem()+1);
                }
                mHandler.sendEmptyMessageDelayed(0,3000);
            }
            return false;
        }
    });

}
