package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.GenderOption;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

/**
 * Created by Hafid on 2/5/2018.
 */

public class GenderSpinnerAdapter extends BaseAdapter {
    Context context;
    Activity activity;
    List<GenderOption> genderOptionList;
    LayoutInflater inflter;

    public GenderSpinnerAdapter(Context applicationContext, Activity activity, List<GenderOption> genderOptions) {
        this.context = applicationContext;
        this.genderOptionList = genderOptions;
        this.activity = activity;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return genderOptionList.size();
    }

    @Override
    public Object getItem(int i) {
        return genderOptionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return genderOptionList.get(i).getIconId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        GenderOption genderOption = genderOptionList.get(i);
        view = inflter.inflate(R.layout.item_spinner_icon_blue, null);
        ImageView icon = (ImageView) view.findViewById(R.id.spinnerIcon);
        TextView names = (TextView) view.findViewById(R.id.spinnerText);
        icon.setImageResource(genderOption.getIconId());
        names.setText(genderOption.getGender());
        ViewFaceUtility.applyFont(names, activity,"fonts/Dosis-Medium.otf");
        return view;
    }
}