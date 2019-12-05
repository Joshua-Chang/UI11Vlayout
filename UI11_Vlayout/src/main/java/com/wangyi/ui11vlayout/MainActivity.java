package com.wangyi.ui11vlayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.bumptech.glide.Glide;
import com.sunfusheng.marqueeview.MarqueeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    String[] ITEM_NAMES={"天猫","聚划算","天猫国际","外卖","天猫超市","充值中心","飞猪旅行","领金币","阿里拍卖","鲜美菜场"};
    int[]IMG_URLS={R.mipmap.icon_mypage_concern,R.mipmap.icon_mypage_friendsb,R.mipmap.icon_mypage_home,R.mipmap.icon_mypage_like,R.mipmap.icon_mypage_like,R.mipmap.icon_mypage_money,R.mipmap.icon_mypage_partner,R.mipmap.icon_mypage_sales,R.mipmap.icon_mypage_sold,R.mipmap.icon_mypage_waitingpublish};
    int[]ITEM_URL={R.mipmap.ban1,R.mipmap.ban4,R.mipmap.ban3,R.mipmap.ban2};
    int[]GRID_URL={R.mipmap.grid1,R.mipmap.grid2,R.mipmap.grid3,R.mipmap.grid4};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mRecyclerView=findViewById(R.id.recycler);
        VirtualLayoutManager virtualLayoutManager=new VirtualLayoutManager(this);
        mRecyclerView.setLayoutManager(virtualLayoutManager);
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        mRecyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0,10);
        BaseDelegateAdapter bannerAdapter=new BaseDelegateAdapter(this,new LinearLayoutHelper(),R.layout.vlayout_banner,1,1){
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
            ArrayList<String>arrayList=new ArrayList<>();
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372158120&di=55bd1c0414cc98ae1ae9074476c88fd2&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3453505458%2C3248035561%26fm%3D214%26gp%3D0.jpg");
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372157650&di=6dec7a87ae1f9af9bbfee3b0a40edfb0&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d9b056f1169232f875a944a0fcb3.jpg");
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372157650&di=723480eb67aeb426f13370240f20e12e&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F10%2F58%2F70%2F01b1OOOPIC34.jpg");
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372248319&di=bc14ac3e105dc946d1097b99dbe435e9&imgtype=jpg&src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D2142038356%2C1287466040%26fm%3D214%26gp%3D0.jpg");
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372157649&di=d9cb4505b8190201ccfa409d2c70d3d9&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0183cb5859e975a8012060c82510f6.jpg");
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372157648&di=c456dd9487d633b594a80dfc2ee4648d&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01654f5777e1a70000012e7eefe905.jpg");
            Banner mBanner=holder.getView(R.id.banner);
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            mBanner.setImages(arrayList);
            mBanner.setBannerAnimation(Transformer.DepthPage);
            mBanner.isAutoPlay(true);
            mBanner.setImageLoader(new GlideImageLoader());
            mBanner.setDelayTime(3000);
            mBanner.setIndicatorGravity(BannerConfig.CENTER);
            mBanner.start();
            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    Toast.makeText(getApplicationContext(), "banner is click:"+position, Toast.LENGTH_SHORT).show();
                }
            });
            }
        };

        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(5);
        gridLayoutHelper.setPadding(0,16,0,0);
        gridLayoutHelper.setVGap(10);
        gridLayoutHelper.setHGap(0);
        BaseDelegateAdapter menuAdapter=new BaseDelegateAdapter(this, gridLayoutHelper,R.layout.vlayout_menu,10,0){
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
                holder.setText(R.id.tv_menu_title_home,ITEM_NAMES[position]+"");
                holder.setImageResourse(R.id.iv_menu_home,IMG_URLS[position]);
                holder.getView(R.id.ll_menu_home).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), ITEM_NAMES[position], Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        BaseDelegateAdapter newsAdapter=new BaseDelegateAdapter(this,new LinearLayoutHelper(),R.layout.vlayout_news,1,0){
            @Override
            public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
                MarqueeView marqueeView1=holder.getView(R.id.marqueeView1);
                MarqueeView marqueeView2=holder.getView(R.id.marqueeView2);
                List<String>info1=new ArrayList<>();
                List<String>info2=new ArrayList<>();
                info1.add("写在菜单上的神秘鸡蛋，是南京女孩们抬头挺胸的秘密");
                info1.add("这部被严重低估的国剧赶紧翻");
                info1.add("黄晓明带领的《中餐厅》,离倒闭还有多远?");
                info2.add("海清公开为自己求好处，真丑！");
                info2.add("第一次靠写作有满意的收入");
                info2.add("颜值最高的女科学家，一生只发明一件东西，现在每个人都在用");

                marqueeView1.startWithList(info1,R.anim.anim_bottom_in,R.anim.anim_top_out);
                marqueeView2.startWithList(info2,R.anim.anim_bottom_in,R.anim.anim_top_out);
                MarqueeView.OnItemClickListener listener = new MarqueeView.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position, TextView textView) {
                        Toast.makeText(getApplicationContext(), ""+textView.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                };
                marqueeView1.setOnItemClickListener(listener);
                marqueeView2.setOnItemClickListener(listener);
            }
        };


        DelegateAdapter delegateAdapter=new DelegateAdapter(virtualLayoutManager,true);
        delegateAdapter.addAdapter(bannerAdapter);
        delegateAdapter.addAdapter(menuAdapter);
        delegateAdapter.addAdapter(newsAdapter);
        mRecyclerView.setAdapter(delegateAdapter);
    }

//    class BannerAdapter extends DelegateAdapter.Adapter<BaseViewHolder>{
//        private Context context;
//
//        public BannerAdapter(Context context) {
//            this.context = context;
//        }
//
//        @Override
//        public LayoutHelper onCreateLayoutHelper() {
//            return new LinearLayoutHelper();
//        }
//
//        @NonNull
//        @Override
//        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return new BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.vlayout_banner,parent,false));
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
//            ArrayList<Integer>arrayList=new ArrayList<>();
////            ArrayList<String>arrayList=new ArrayList<>();
////            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372158120&di=55bd1c0414cc98ae1ae9074476c88fd2&imgtype=jpg&src=http%3A%2F%2Fimg3.imgtn.bdimg.com%2Fit%2Fu%3D3453505458%2C3248035561%26fm%3D214%26gp%3D0.jpg");
////            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372157650&di=6dec7a87ae1f9af9bbfee3b0a40edfb0&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d9b056f1169232f875a944a0fcb3.jpg");
////            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372157650&di=723480eb67aeb426f13370240f20e12e&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F10%2F58%2F70%2F01b1OOOPIC34.jpg");
////            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372248319&di=bc14ac3e105dc946d1097b99dbe435e9&imgtype=jpg&src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D2142038356%2C1287466040%26fm%3D214%26gp%3D0.jpg");
////            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372157649&di=d9cb4505b8190201ccfa409d2c70d3d9&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0183cb5859e975a8012060c82510f6.jpg");
////            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575372157648&di=c456dd9487d633b594a80dfc2ee4648d&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01654f5777e1a70000012e7eefe905.jpg");
//            arrayList.add(R.mipmap.ban1);
//            arrayList.add(R.mipmap.ban2);
//            arrayList.add(R.mipmap.ban3);
//            arrayList.add(R.mipmap.ban4);
//            Banner mBanner=holder.getView(R.id.banner);
//            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
//            mBanner.setImages(arrayList);
//            mBanner.setBannerAnimation(Transformer.DepthPage);
//            mBanner.isAutoPlay(true);
//            mBanner.setImageLoader(new GlideImageLoader());
//            mBanner.setDelayTime(3000);
//            mBanner.setIndicatorGravity(BannerConfig.CENTER);
//            mBanner.start();
//            mBanner.setOnBannerListener(new OnBannerListener() {
//                @Override
//                public void OnBannerClick(int position) {
//                    Toast.makeText(getApplicationContext(), "banner is click:"+position, Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//
//        @Override
//        public int getItemCount() {
//            return 1;
//        }
//    }
}
