package id.pptik.org.homecaretimedic;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.org.homecaretimedic.customuicompt.ZoomOutPageTransformer;
import id.pptik.org.homecaretimedic.screenslidefragment.ScreenSlidePageFragment;

public class ScreenSlideActivity extends FragmentActivity {
    public static String TAG = "[ScreenSlideActivity]";

    @BindView(R.id.rollerView)
    ImageView roller;
    @BindView(R.id.nextAct)
    TextView nextAct;

    private static final int NUM_PAGES = 5;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_screen_slide);
        ButterKnife.bind(this);
        roller.setImageResource(R.drawable.dot_cnt_first);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                changeRollerView(position);
            }

            @Override
            public void onPageSelected(int position) {
                changeRollerView(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //mPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putInt("view_code",position);
            Fragment afragment = new ScreenSlidePageFragment();
            afragment.setArguments(bundle);
            Log.i(TAG, ""+position);
            return afragment;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    public void changeRollerView(final int position){
        switch (position){
            case 0:
                roller.setImageResource(R.drawable.dot_cnt_first);
                setNextPager(position);
                break;
            case 1:
                roller.setImageResource(R.drawable.dot_cnt_second);
                setNextPager(position);
                break;
            case 2:
                roller.setImageResource(R.drawable.dot_cnt_third);
                setNextPager(position);
                break;
            case 3:
                roller.setImageResource(R.drawable.dot_cnt_fourth);
                setNextPager(position);
                break;
            case 4:
                roller.setImageResource(R.drawable.dot_cnt_fifth);
                setNextPager(position);
                break;
            default:
                roller.setImageResource(R.drawable.dot_cnt_fifth);
                setNextPager(position);
                break;
        }
    }

    public void setNextPager(final int position){
        if (position != mPagerAdapter.getCount()-1) {
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


