package id.smartin.org.homecaretimedic.mainfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.HealthCalculatorActivity;
import id.smartin.org.homecaretimedic.HealthProductActivity;
import id.smartin.org.homecaretimedic.HealthVideoActivity;
import id.smartin.org.homecaretimedic.HomecareActivity;
import id.smartin.org.homecaretimedic.MedicalRecordActivity;
import id.smartin.org.homecaretimedic.MedicineReminderActivity;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.uimodel.Slider;
import id.smartin.org.homecaretimedic.screenslidefragment.ScreenSlideHomeFragment;
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
    @BindView(R.id.tabDots)
    TabLayout tabLayout;

    private PagerAdapter mPagerAdapter;

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
        List<Slider> sliders = new ArrayList<>();
        sliders.add(new Slider(R.drawable.ads_01,""));
        sliders.add(new Slider(R.drawable.ads_02,""));
        sliders.add(new Slider(R.drawable.ads_03,""));
        sliders.add(new Slider(R.drawable.ads_04,""));
        sliders.add(new Slider(R.drawable.ads_05,""));
        mPagerAdapter = new HomeFragment.ScreenSlidePagerAdapter(getChildFragmentManager(), sliders);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.canScrollHorizontally(1);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager, true);
        btnMedicalRec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MedicalRecordActivity.class);
                startActivity(intent);
            }
        });
        btnMedicalRec.setEnabled(false);
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
        btnHealthProd.setEnabled(false);
        btnHealthVid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HealthVideoActivity.class);
                startActivity(intent);
            }
        });
        btnHealthVid.setEnabled(false);
        btnMedicineRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MedicineReminderActivity.class);
                startActivity(intent);
            }
        });
        btnMedicineRemind.setEnabled(false);
        // Inflate the layout for this fragment
        return vwInflater;
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private List<Slider> sliderList;

        public ScreenSlidePagerAdapter(FragmentManager fm, List<Slider> sliders) {
            super(fm);
            this.sliderList = sliders;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("view_code", sliderList.get(position));
            Fragment afragment = new ScreenSlideHomeFragment();
            afragment.setArguments(bundle);
            return afragment;
        }

        @Override
        public int getCount() {
            return sliderList.size();
        }
    }

}
