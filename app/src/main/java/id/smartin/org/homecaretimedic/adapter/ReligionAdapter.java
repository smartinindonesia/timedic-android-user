package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.Religion;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

/**
 * Created by Hafid on 02/05/2018.
 */

public class ReligionAdapter extends BaseAdapter {
    Context context;
    Activity activity;
    List<Religion> religionList;
    LayoutInflater inflter;

    public ReligionAdapter(Context applicationContext, Activity activity, List<Religion> religionList) {
        this.context = applicationContext;
        this.religionList = religionList;
        this.activity = activity;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return religionList.size();
    }

    @Override
    public Object getItem(int i) {
        return religionList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return religionList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Religion religion = religionList.get(i);
        view = inflter.inflate(R.layout.item_religion_adapter, null);
        TextView names = (TextView) view.findViewById(R.id.spinnerText);
        names.setText(religion.getReligion());
        ViewFaceUtility.applyFont(names, activity,"fonts/Dosis-Medium.otf");
        return view;
    }
}
