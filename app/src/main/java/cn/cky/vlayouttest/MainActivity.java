package cn.cky.vlayouttest;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv);

        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(16, 16, 16, 16);
            }
        });

        final GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(5);
        gridLayoutHelper.setItemCount(5);

        final List<LayoutHelper> helpers = new LinkedList<>();
        helpers.add(DefaultLayoutHelper.newHelper(2));
        helpers.add(gridLayoutHelper);

        layoutManager.setLayoutHelpers(helpers);

        recyclerView.setAdapter(
                new VirtualLayoutAdapter(layoutManager) {
                    @Override
                    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                        View view;
                        if (viewType == 0){
                             view = LayoutInflater.from(MainActivity.this).inflate(R.layout.content_item_layout,parent,false);
                        }else {
                            view = LayoutInflater.from(MainActivity.this).inflate(R.layout.title_layout,parent,false);
                        }

                        return new MainViewHolder(view);
                    }

                    @Override
                    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {

                        if (position == 1){
                            VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            holder.itemView.setLayoutParams(layoutParams);
                            TextView textView = (TextView) holder.itemView.findViewById(R.id.title);
                            textView.setText("一周精彩影片");
                        }else {
                            VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
                            holder.itemView.setLayoutParams(layoutParams);

                            ImageView imageView = (ImageView) holder.itemView.findViewById(R.id.img);
                            imageView.setImageResource(R.mipmap.one);
                            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                                @Override
                                public void onFocusChange(View v, boolean hasFocus) {
                                    if (hasFocus){
                                        v.animate().scaleY(1.1f).scaleX(1.1f).setDuration(300).start();
                                        Toast.makeText(MainActivity.this, position+"", Toast.LENGTH_SHORT).show();
                                    }else {
                                        v.animate().scaleY(1.0f).scaleX(1.0f).setDuration(300).start();
                                    }
                                }
                            });
                        }


                    }

                    @Override
                    public int getItemCount() {
                        List<LayoutHelper> helpers = getLayoutHelpers();
                        if (helpers == null) {
                            return 0;
                        }
                        int count = 0;
                        for (int i = 0, size = helpers.size(); i < size; i++) {
                            count += helpers.get(i).getItemCount();
                        }
                        return count;
                    }

                    @Override
                    public int getItemViewType(int position) {
                        if(position==1){
                            return 1;
                        }else {
                            return 0;
                        }


                    }
                });
    }

    static class MainViewHolder extends RecyclerView.ViewHolder {
        public MainViewHolder(View itemView) {
            super(itemView);
        }
    }
}
