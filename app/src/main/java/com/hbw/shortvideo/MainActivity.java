package com.hbw.shortvideo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aliyun.player.AliPlayer;
import com.aliyun.player.AliPlayerFactory;
import com.aliyun.player.IPlayer;
import com.aliyun.player.nativeclass.CacheConfig;
import com.aliyun.player.source.UrlSource;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import fr.castorflex.android.verticalviewpager.VerticalViewPager;

public class MainActivity extends AppCompatActivity {

    private VerticalViewPager vp;
    private MyPagerAdapter myPagerAdapter;
    private int currentFlagPostion;//传递过来播放第几个
    private List<Video> list = new ArrayList<>();//播放列表
    private int mCurrentPosition;//当前正在播放第几个
    private AliPlayer aliPlayer;//当前正在播放的播放器

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vp = findViewById(R.id.main_vp);
        initViewPager();
    }

    private void initViewPager() {
        currentFlagPostion = getIntent().getIntExtra("currentPostion", 0);

        //注意我这里的封面图是随便加的，和数据不匹配。
        Video video1 = new Video();
        video1.setVideoUrl("https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc56b6a0ed9f32df14604bde732710f2eda9644e2bfb18eec9f1232fd4b71224ec0216e173b4aacd81ee240dbc65959d8c70&line=0");
        video1.setTitle("荒野大镖客2");
        video1.setImageUrl("https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/crop%3D17%2C0%2C605%2C399%3Bc0%3Dbaike80%2C5%2C5%2C80%2C26/sign=029c43860124ab18f459bb7708cbd1e1/43a7d933c895d143fdb142837bf082025baf074e.jpg");

        Video video2 = new Video();
        video2.setVideoUrl("https://aweme.snssdk.com/aweme/v1/playwm/?s_vid=93f1b41336a8b7a442dbf1c29c6bbc5689d5e7d2923ac98e1d95d67ae3f7060bd0bf8ad408b8022c90c915fc12ca513261454be41297235a40cbeaa918596787&line=0");
        video2.setTitle("小龙虾");
        video2.setImageUrl("https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/crop%3D17%2C0%2C605%2C399%3Bc0%3Dbaike80%2C5%2C5%2C80%2C26/sign=029c43860124ab18f459bb7708cbd1e1/43a7d933c895d143fdb142837bf082025baf074e.jpg");

        list.add(video1);
        list.add(video2);
        list.add(video1);
        list.add(video2);
        list.add(video1);
        list.add(video2);
        list.add(video1);
        list.add(video2);

        myPagerAdapter = new MyPagerAdapter();
        vp.setAdapter(myPagerAdapter);
        vp.setOffscreenPageLimit(3);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int postition) {
                mCurrentPosition = postition;
                // 滑动界面，首先让之前的播放器暂停，并seek到0
                if (aliPlayer != null) {
                    aliPlayer.seekTo(0);
                    aliPlayer.pause();
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        vp.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                if (position != 0) {
                    return;
                }
                PlayerInfo playerInfo = myPagerAdapter.findPlayerInfo(mCurrentPosition);
                if (playerInfo != null) {
                    if (playerInfo.getAliPlayer() != null) {
                        playerInfo.getAliPlayer().start();
                        aliPlayer = playerInfo.getAliPlayer();
                    }
                }
            }
        });
        vp.setCurrentItem(currentFlagPostion);
    }


    class MyPagerAdapter extends PagerAdapter {

        ArrayList<PlayerInfo> playerInfoList = new ArrayList<>();
        private LinkedList<View> mViewCache = new LinkedList<>();

        protected PlayerInfo instantiatePlayerInfo(int position) {
            AliPlayer aliyunVodPlayer = AliPlayerFactory.createAliPlayer(getApplicationContext());
            PlayerInfo playerInfo = new PlayerInfo();
            playerInfo.setPlayURL(list.get(position).getVideoUrl());
            playerInfo.setAliPlayer(aliyunVodPlayer);
            playerInfo.setPosition(position);
            playerInfoList.add(playerInfo);
            return playerInfo;
        }

        public PlayerInfo findPlayerInfo(int position) {
            for (int i = 0; i < playerInfoList.size(); i++) {
                PlayerInfo playerInfo = playerInfoList.get(i);
                if (playerInfo.getPosition() == position) {
                    return playerInfo;
                }
            }
            return null;
        }

        public void mOnDestroy() {
            for (PlayerInfo playerInfo : playerInfoList) {
                if (playerInfo.getAliPlayer() != null) {
                    playerInfo.getAliPlayer().release();
                }
            }
            playerInfoList.clear();
        }

        protected void destroyPlayerInfo(int position) {
            while (true) {
                PlayerInfo playerInfo = findPlayerInfo(position);
                if (playerInfo == null)
                    break;
                if (playerInfo.getAliPlayer() == null)
                    break;
                playerInfo.getAliPlayer().release();
                playerInfoList.remove(playerInfo);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, final int position) {
            View view;
            if (mViewCache.size() == 0) {
                view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_main_viewpage, null, false);
            } else {
                view = mViewCache.removeFirst();
            }
            view.setId(position);

            TextView videoTitle = view.findViewById(R.id.item_main_video_title);
            final ImageView coverPicture = view.findViewById(R.id.item_main_cover_picture);
            SurfaceView surfaceView = view.findViewById(R.id.item_main_surface_view);

            surfaceView.setZOrderOnTop(true);

            if (!TextUtils.isEmpty(list.get(position).getImageUrl())) {
                coverPicture.setVisibility(View.VISIBLE);
                Glide.with(MainActivity.this).load(list.get(position).getImageUrl()).into(coverPicture);
            }
            videoTitle.setText("<  " + list.get(position).getTitle());
            videoTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnDestroy();
                    finish();
                }
            });

            final PlayerInfo playerInfo = instantiatePlayerInfo(position);
            surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    playerInfo.getAliPlayer().setDisplay(holder);
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    playerInfo.getAliPlayer().redraw();
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    playerInfo.getAliPlayer().setDisplay(null);
                }
            });
            UrlSource urlSource = new UrlSource();
            urlSource.setUri(list.get(position).getVideoUrl());
            //设置播放源
            playerInfo.getAliPlayer().setDataSource(urlSource);
            //准备播放
            playerInfo.getAliPlayer().prepare();

            //开启缓存
            CacheConfig cacheConfig = new CacheConfig();
            //开启缓存功能
            cacheConfig.mEnable = true;
            //能够缓存的单个文件最大时长。超过此长度则不缓存
            cacheConfig.mMaxDurationS = 300;
            //缓存目录的位置
            cacheConfig.mDir = "hbw";
            //缓存目录的最大大小。超过此大小，将会删除最旧的缓存文件
            cacheConfig.mMaxSizeMB = 200;
            //设置缓存配置给到播放器
            playerInfo.getAliPlayer().setCacheConfig(cacheConfig);

            playerInfo.getAliPlayer().setLoop(true);
            playerInfo.getAliPlayer().setOnPreparedListener(new IPlayer.OnPreparedListener() {
                @Override
                public void onPrepared() {
                    // 视频准备成功之后影响封面图
                    if (!TextUtils.isEmpty(list.get(position).getImageUrl())) {
                        coverPicture.setVisibility(LinearLayout.GONE);
                    }
                }
            });
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            destroyPlayerInfo(position);
            View contentView = (View) object;
            container.removeView(contentView);
            mViewCache.add(contentView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aliPlayer != null) {
            aliPlayer.release();
        }
        if (myPagerAdapter != null) {
            myPagerAdapter.mOnDestroy();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (aliPlayer != null) {
            aliPlayer.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (aliPlayer != null) {
            aliPlayer.start();
        }
    }
}
