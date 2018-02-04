package id.smartin.org.homecaretimedic.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.HealthCalculatorActivity;
import id.smartin.org.homecaretimedic.HealthProductActivity;
import id.smartin.org.homecaretimedic.HealthVideoActivity;
import id.smartin.org.homecaretimedic.HomecareActivity;
import id.smartin.org.homecaretimedic.MedicalRecordActivity;
import id.smartin.org.homecaretimedic.MedicineReminderActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.screenslidefragment.ScreenSlidePageFragment;

/**
 * Created by Hafid on 8/22/2017.
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.btnHomecare)
    ImageButton btnHomecare;
    @BindView(R.id.btnMedicalRecord)
    ImageButton btnMedicalRec;
    @BindView(R.id.btnHealthCalculator)
    ImageButton btnHealthCalc;
    @BindView(R.id.btnHealthProduct)
    ImageButton btnHealthProd;
    @BindView(R.id.btnHealthVideo)
    ImageButton btnHealthVid;
    @BindView(R.id.btnMedicineReminder)
    ImageButton btnMedicineRemind;
    @BindView(R.id.pager)
    ViewPager viewPager;
    @BindView(R.id.rollerView)
    ImageView roller;

    private PagerAdapter mPagerAdapter;
    private int NUM_PAGES = 5;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vwInflater = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, vwInflater);
        mPagerAdapter = new HomeFragment.ScreenSlidePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mPagerAdapter);
        viewPager.canScrollHorizontally(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        btnMedicalRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MedicalRecordActivity.class);
                startActivity(intent);
            }
        });
        btnHomecare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomecareActivity.class);
                startActivity(intent);
            }
        });
        btnHealthCalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthCalculatorActivity.class);
                startActivity(intent);
            }
        });
        btnHealthProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthProductActivity.class);
                startActivity(intent);
            }
        });
        btnHealthVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthVideoActivity.class);
                startActivity(intent);
            }
        });
        btnMedicineRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MedicineReminderActivity.class);
                startActivity(intent);
            }
        });
        // Inflate the layout for this fragment
        return vwInflater;
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
                break;
            case 1:
                roller.setImageResource(R.drawable.dot_cnt_second);
                break;
            case 2:
                roller.setImageResource(R.drawable.dot_cnt_third);
                break;
            case 3:
                roller.setImageResource(R.drawable.dot_cnt_fourth);
                break;
            case 4:
                roller.setImageResource(R.drawable.dot_cnt_fifth);
                break;
            default:
                roller.setImageResource(R.drawable.dot_cnt_fifth);
                break;
        }
    }

}
