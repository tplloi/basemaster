package vn.loitp.app.activity.home;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import loitp.basemaster.R;
import vn.loitp.app.activity.home.ad.FrmPhotoGift;
import vn.loitp.app.activity.home.allmanga.FrmAllManga;
import vn.loitp.app.activity.home.allwallpaper.FrmPhotoCategory;
import vn.loitp.app.activity.home.downloadedmanga.FrmDownloadedManga;
import vn.loitp.app.activity.home.favmanga.FrmFavManga;
import vn.loitp.app.activity.home.mangawallpaper.FrmPhotoManga;
import vn.loitp.app.activity.home.more.FrmPhotoMore;
import vn.loitp.app.data.ComicData;
import vn.loitp.app.util.AppUtil;
import vn.loitp.core.base.BaseActivity;
import vn.loitp.core.utilities.LDialogUtil;
import vn.loitp.core.utilities.LLog;
import vn.loitp.core.utilities.LPopupMenu;
import vn.loitp.core.utilities.LSocialUtil;
import vn.loitp.core.utilities.LUIUtil;
import vn.loitp.views.LAppBarLayout;

public class HomeMenuActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private List<String> stringList = new ArrayList<>();
    private ImageView toolbarImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isShowAdWhenExist = false;
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        toolbarImage = (ImageView) findViewById(R.id.toolbar_image);

        AppUtil.loadBackground(activity, toolbarImage);

        setCustomStatusBar(Color.TRANSPARENT, ContextCompat.getColor(activity, R.color.colorPrimary));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        /*CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(getString(R.string.list_comic));
        collapsingToolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(activity, R.color.White));
        collapsingToolbarLayout.setExpandedTitleColor(ContextCompat.getColor(activity, R.color.White));*/

        LAppBarLayout appBarLayout = (LAppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setOnStateChangeListener(new LAppBarLayout.OnStateChangeListener() {
            @Override
            public void onStateChange(LAppBarLayout.State toolbarChange) {
                //LLog.d(TAG, "toolbarChange: " + toolbarChange);
                if (toolbarChange.equals(LAppBarLayout.State.COLLAPSED)) {
                    //COLLAPSED appBarLayout min
                    LLog.d(TAG, "COLLAPSED toolbarChange: " + toolbarChange);
                } else if (toolbarChange.equals(LAppBarLayout.State.EXPANDED)) {
                    //EXPANDED appBarLayout max
                    LLog.d(TAG, "EXPANDED toolbarChange: " + toolbarChange);
                    AppUtil.loadBackground(activity, toolbarImage);
                } else {
                    //IDLE appBarLayout not min not max
                    LLog.d(TAG, "IDLE toolbarChange: " + toolbarChange);
                }
            }
        });

        stringList.add("Tất cả truyện");
        stringList.add("Truyện yêu thích");
        stringList.add("Truyện đã tải");
        stringList.add("Ảnh nền Manga-Anime");
        stringList.add("Ảnh nền");
        stringList.add("Qùa tặng");
        stringList.add("Phụ lục");

        findViewById(R.id.bt_menu).setOnClickListener(this);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        LUIUtil.setPullLikeIOSHorizontal(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected boolean setFullScreen() {
        return false;
    }

    @Override
    protected String setTag() {
        return getClass().getSimpleName();
    }

    @Override
    protected Activity setActivity() {
        return this;
    }

    @Override
    protected int setLayoutResourceId() {
        return R.layout.activity_home_menu;
    }

    private class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new FrmAllManga();
                case 1:
                    return new FrmFavManga();
                case 2:
                    return new FrmDownloadedManga();
                case 3:
                    return new FrmPhotoManga();
                case 4:
                    return new FrmPhotoCategory();
                case 5:
                    return new FrmPhotoGift();
                case 6:
                    return new FrmPhotoMore();
            }
            return null;
        }

        @Override
        public int getCount() {
            return stringList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return stringList.get(position);
        }
    }

    private boolean isExit;

    @Override
    public void onBackPressed() {
        if (isExit) {
            ComicData.getInstance().clearAll();
            super.onBackPressed();
        } else {
            LDialogUtil.showDialog3(activity, getString(R.string.app_name), getString(R.string.msg_exit_app), getString(R.string.yes), getString(R.string.no), getString(R.string.rate), new LDialogUtil.Callback3() {
                @Override
                public void onClick1() {
                    isExit = true;
                    onBackPressed();
                }

                @Override
                public void onClick2() {
                    //do nothing
                }

                @Override
                public void onClick3() {
                    LSocialUtil.rateApp(activity, getPackageName());
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_menu:
                LPopupMenu.show(activity, v, R.menu.menu_popup, new LPopupMenu.CallBack() {
                    @Override
                    public void clickOnItem(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.rate_app:
                                LSocialUtil.rateApp(activity, getPackageName());
                                break;
                            case R.id.more_app:
                                LSocialUtil.moreApp(activity);
                                break;
                            case R.id.share_app:
                                LSocialUtil.shareApp(activity);
                                break;
                            case R.id.like_facebook_fanpage:
                                LSocialUtil.likeFacebookFanpage(activity);
                                break;
                            case R.id.support_24_7:
                                LSocialUtil.chatMessenger(activity);
                                break;
                        }
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        ComicData.getInstance().clearAll();
        super.onDestroy();
    }
}
