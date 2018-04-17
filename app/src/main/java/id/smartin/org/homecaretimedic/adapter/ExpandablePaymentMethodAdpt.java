package id.smartin.org.homecaretimedic.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.PaymentMethodChild;
import id.smartin.org.homecaretimedic.model.PaymentMethod;
import id.smartin.org.homecaretimedic.tools.ViewFaceUtility;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Hafid on 4/13/2018.
 */

public class ExpandablePaymentMethodAdpt extends BaseExpandableListAdapter {
    public static final String TAG = "[ExpandableAdpt]";

    private LayoutInflater vi;
    private Activity _activity;
    private Context _context;
    private List<PaymentMethod> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<PaymentMethod, List<PaymentMethodChild>> _listDataChild;

    public ExpandablePaymentMethodAdpt(Context context, Activity activity,
                                       List<PaymentMethod> listDataHeader,
                                       HashMap<PaymentMethod, List<PaymentMethodChild>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this._activity = activity;
        this.vi = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return this._listDataChild.get(this._listDataHeader.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return this._listDataHeader.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return this._listDataChild.get(this._listDataHeader.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final PaymentMethod childText = (PaymentMethod) getGroup(groupPosition);
        ParentViewHolder viewHolder;
        if (convertView == null) {
            convertView = vi.inflate(R.layout.item_payment_methods, null);
            convertView.setPadding(5, 15, 5, 15);
            viewHolder = new ParentViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ParentViewHolder) convertView.getTag();
        }
        Log.i(TAG, "group view");
        viewHolder.paymentMethod.setText(childText.getPaymentMethodName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View convertView, ViewGroup viewGroup) {
        final PaymentMethodChild childText = (PaymentMethodChild) getChild(groupPosition, childPosition);
        ChildViewHolder viewHolder;
        if (convertView == null) {
            convertView = vi.inflate(R.layout.item_payment_method_child, null);
            convertView.setPadding(5, 10, 5, 10);
            viewHolder = new ChildViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ChildViewHolder) convertView.getTag();
        }
        Log.i(TAG, "child view");
        viewHolder.paymentMethod.setText(childText.getMethodName());
        //viewHolder.paymentLogo.setImageDrawable(ContextCompat.getDrawable(_context, childText.getLogoDrawable()));
        Glide.with(getApplicationContext()).load(childText.getLogoDrawable()).apply(new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE))
                .thumbnail(0.7f)
                .into(viewHolder.paymentLogo);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public class ChildViewHolder {
        @BindView(R.id.paymentMethod)
        public TextView paymentMethod;
        @BindView(R.id.paymentLogo)
        public ImageView paymentLogo;

        public ChildViewHolder(View view) {
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(paymentMethod, _activity, "fonts/Dosis-Regular.otf");
        }
    }

    public class ParentViewHolder {
        @BindView(R.id.paymentMethod)
        public TextView paymentMethod;

        public ParentViewHolder(View view) {
            ButterKnife.bind(this, view);
            ViewFaceUtility.applyFont(paymentMethod, _activity, "fonts/Dosis-Regular.otf");
        }
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        Log.i(TAG, this._listDataChild.get(this._listDataHeader.get(0)).size() + " Size of child");
    }
}
