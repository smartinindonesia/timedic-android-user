package id.smartin.org.homecaretimedic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.LayananLab;

/**
 * Created by Hafid on 11/12/2017.
 */

public class LayananLabAutoCompleteAdapter extends ArrayAdapter<LayananLab> {
    private final Context mContext;
    private final List<LayananLab> mDepartments;
    private final List<LayananLab> mDepartments_All;
    private final List<LayananLab> mDepartments_Suggestion;
    private final int mLayoutResourceId;

    public LayananLabAutoCompleteAdapter(Context context, int resource, List<LayananLab> departments) {
        super(context, resource, departments);
        this.mContext = context;
        this.mLayoutResourceId = resource;
        this.mDepartments = new ArrayList<>(departments);
        this.mDepartments_All = departments;
        this.mDepartments_Suggestion = new ArrayList<>();
    }

    public int getCount() {
        return mDepartments.size();
    }

    public LayananLab getItem(int position) {
        return mDepartments.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            LayananLab department = getItem(position);
            LayananLabAutoCompleteAdapter.MyViewHolder holder = new MyViewHolder();
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
                convertView = inflater.inflate(mLayoutResourceId, null);//parent, false);

                holder.servicename = (TextView)convertView.findViewById(R.id.serviceName);
                holder.servicePrice = (TextView) convertView.findViewById(R.id.servicePrice);
                holder.servicename.setText(department.getNamaLayanan());
                holder.servicePrice.setText("Rp. "+department.getHargaLayanan());
                convertView.setTag(holder);
            } else {
                holder = (MyViewHolder)convertView.getTag();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            public String convertResultToString(Object resultValue) {
                return ((LayananLab) resultValue).getNamaLayanan();
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                if (constraint != null) {
                    mDepartments_Suggestion.clear();
                    for (LayananLab department : mDepartments_All) {
                        if (department.getNamaLayanan().toLowerCase().contains(constraint.toString().toLowerCase())) {
                            mDepartments_Suggestion.add(department);
                        }
                    }
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = mDepartments_Suggestion;
                    filterResults.count = mDepartments_Suggestion.size();
                    return filterResults;
                } else {
                    return new FilterResults();
                }
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDepartments.clear();
                if (results != null && results.count > 0) {
                    // avoids unchecked cast warning when using mDepartments.addAll((ArrayList<Department>) results.values);
                    List<?> result = (List<?>) results.values;
                    for (Object object : result) {
                        if (object instanceof LayananLab) {
                            mDepartments.add((LayananLab) object);
                        }
                    }
                } else if (constraint == null) {
                    // no filter, add entire original list back in
                    mDepartments.addAll(mDepartments_All);
                }
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataChange(){

    }

    public class MyViewHolder {
        public TextView servicename;
        public TextView servicePrice;
    }
}
