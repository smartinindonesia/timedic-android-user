package id.smartin.org.homecaretimedic;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.customuicompt.ParallaxPageTransformer;
import id.smartin.org.homecaretimedic.screenslidefragment.ScreenSlidePageFragment;

public class ScreenSlideActivity extends FragmentActivity {
    public static String TAG = "[ScreenSlideActivity]";

    @BindView(R.id.nextAct)
    TextView nextAct;
    @BindView(R.id.pager)
    ViewPager mPager;
    @BindView(R.id.tabDots)
    TabLayout tabLayout;
    private static final int NUM_PAGES = 3;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_screen_slide);
        ButterKnife.bind(this);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        ParallaxPageTransformer pageTransformer = new ParallaxPageTransformer()
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.screenMessage, 1.5f, 0.5f))
                .addViewToParallax(new ParallaxPageTransformer.ParallaxTransformInformation(R.id.screenLogo, 2f, 2f));
        mPager.setPageTransformer(true, pageTransformer);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                setNextPager(position);
            }

            @Override
            public void onPageSelected(int position) {
                setNextPager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(mPager, true);
        //mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("view_code", position);
            Fragment afragment = new ScreenSlidePageFragment();
            afragment.setArguments(bundle);
            Log.i(TAG, "" + position);
            return afragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void setNextPager(final int position) {
        if (position != mPagerAdapter.getCount() - 1) {
            nextAct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPager.setCurrentItem(position + 1);
                }
            });
        } else {
            nextAct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}


