package id.smartin.org.homecaretimedic.adapter;

import android.content.Context;

import java.util.List;

import id.smartin.org.homecaretimedic.model.Assessment;
import id.smartin.org.homecaretimedic.model.Order;

/**
 * Created by Hafid on 11/25/2017.
 */

public class OrderAdapter {
    public static final String TAG = "[AssestmentAdapter]";

    private List<Order> orderList;
    private Context context;

    public OrderAdapter(Context context, List<Order> orders) {
        this.orderList = orders;
        this.context = context;
    }



}
