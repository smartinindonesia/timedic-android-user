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
import id.smartin.org.homecaretimedic.model.MedicineType;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

/**
 * Created by Hafid on 07/05/2018.
 */

public class MedicineTypeSpinner extends BaseAdapter {

    Context context;
    Activity activity;
    List<MedicineType> medicineTypes;
    LayoutInflater inflter;

    public MedicineTypeSpinner(Context applicationContext, Activity activity, List<MedicineType> medicineTypes) {
        this.context = applicationContext;
        this.medicineTypes = medicineTypes;
        this.activity = activity;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return medicineTypes.size();
    }

    @Override
    public Object getItem(int i) {
        return medicineTypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return medicineTypes.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        MedicineType medicineType = medicineTypes.get(i);
        view = inflter.inflate(R.layout.item_medicine_type, null);
        TextView names = (TextView) view.findViewById(R.id.spinnerText);
        names.setText(medicineType.getMedicineType());
        ViewFaceUtility.applyFont(names, activity,"fonts/Dosis-Medium.otf");
        return view;
    }
}
