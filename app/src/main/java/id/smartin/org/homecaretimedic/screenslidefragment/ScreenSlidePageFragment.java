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

/**
 * Created by Hafid on 8/21/2017.
 */

public class ScreenSlidePageFragment extends Fragment {
    public static String TAG = "[ScreenSlidePageFg]";

    @BindView(R.id.screenLogo)
    ImageView screenLogo;
    @BindView(R.id.screenMessage)
    TextView screenMessage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.screen_fragment_swipe, container, false);
        ButterKnife.bind(this,rootView);
        Integer code = this.getArguments().getInt("view_code");
        switch (code){
            case 0:
                screenLogo.setImageResource(R.drawable.slide_intro_01);
                screenMessage.setText(Html.fromHtml("<html><b>Selamat datang di TiMedic</b><br>Kami rawat di rumah Anda</html>"));
                break;
            case 1:
                screenLogo.setImageResource(R.drawable.slide_intro_02);
                screenMessage.setText(Html.fromHtml("<html>Nikmati layanan <b>homecare perawat,<br>bidan </b>dan <b>check laboratorium</b><br>dengan nyaman di rumah anda</html>"));
                break;
            case 2:
                screenLogo.setImageResource(R.drawable.slide_intro_03);
                screenMessage.setText(Html.fromHtml("<html>Gunakan fitur <b>Health Calculator</b> dari <b>TiMedic</b><br>untuk mengetahui kondisi kesehatan tubuh anda</html>"));
                break;
        }
        return rootView;
    }

}
