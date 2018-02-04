package id.smartin.org.homecaretimedic.customuicompt;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by Hafid on 2/3/2018.
 */

public class ParallaxPageTransformer implements ViewPager.PageTransformer {

    private final int viewToParallax;

    public ParallaxPageTransformer(final int viewToParallax) {
        this.viewToParallax = viewToParallax;

    }

    @Override
    public void transformPage(View view, float position) {
        int pageWidth = view.getWidth();


        if (position < -1) { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(1);

        } else if (position <= 1) { // [-1,1]

            view.findViewById(viewToParallax).setTranslationX(-position * (pageWidth / 2)); //Half the normal speed

        } else { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(1);
        }

    }

}