package id.smartin.org.homecaretimedic.screenslidefragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.uimodel.Slider;

/**
 * Created by Hafid on 2/9/2018.
 */

public class ScreenSlideHomeFragment extends Fragment {
    public static String TAG = "[ScreenSlideHomeFg]";

    @BindView(R.id.bannerImage)
    ImageView bannerImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.banner_fragment_swipe, container, false);
        ButterKnife.bind(this,rootView);
        Slider code = (Slider) this.getArguments().getSerializable("view_code");
        bannerImage.setImageResource(code.getImage_source_id());
        return rootView;
    }
}
