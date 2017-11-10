package id.pptik.org.homecaretimedic.screenslidefragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.pptik.org.homecaretimedic.LoginActivity;
import id.pptik.org.homecaretimedic.R;
import id.pptik.org.homecaretimedic.ScreenSlideActivity;

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
                screenLogo.setImageResource(R.drawable.act_1);
                screenMessage.setText(getResources().getString(R.string.first_msg));
                break;
            case 1:
                screenLogo.setImageResource(R.drawable.act_2);
                screenMessage.setText(getResources().getString(R.string.second_msg));
                break;
            case 2:
                screenLogo.setImageResource(R.drawable.act_3);
                screenMessage.setText(getResources().getString(R.string.third_msg));
                break;
            case 3:
                screenLogo.setImageResource(R.drawable.act_4);
                screenMessage.setText(getResources().getString(R.string.fourth_msg));
                break;
            case 4:
                screenLogo.setImageResource(R.drawable.act_5);
                screenMessage.setText(getResources().getString(R.string.fifth_msg));
                break;
        }

        return rootView;
    }

}
