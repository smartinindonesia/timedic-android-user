package id.smartin.org.homecaretimedic.mainfragment.yourorderchildfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.adapter.ActiveOrderAdapter;
import id.smartin.org.homecaretimedic.adapter.AssestmentAdapter;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.Assessment;
import id.smartin.org.homecaretimedic.model.Order;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.HomecareAssessmentAPIInterface;
import id.smartin.org.homecaretimedic.tools.restservice.HomecareTransactionAPIInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Hafid on 11/25/2017.
 */

public class ActiveOrder extends Fragment {
    public static String TAG = "[ActiveOrder]";

    @BindView(R.id.active_order_list)
    RecyclerView recyclerView;

    private HomecareSessionManager homecareSessionManager;
    private List<Order> orderList = new ArrayList<>();
    private ActiveOrderAdapter activeOrderAdapter;
    private HomecareTransactionAPIInterface homecareTransactionAPIInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_active_order, container, false);
        homecareSessionManager = new HomecareSessionManager(getActivity(), getContext());
        homecareTransactionAPIInterface = APIClient.getClientWithToken(homecareSessionManager, getContext()).create(HomecareTransactionAPIInterface.class);
        ButterKnife.bind(this, view);
        homecareSessionManager = new HomecareSessionManager(getActivity(), getContext());
        activeOrderAdapter = new ActiveOrderAdapter(getActivity(), getContext(), orderList);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(activeOrderAdapter);
        getActiveOrder();
        // Inflate the layout for this fragment
        return view;
    }

    public void getActiveOrder() {
        Call<List<Order>> services = homecareTransactionAPIInterface.getActiveOrder(homecareSessionManager.getUserDetail().getId());
        services.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.code() == 200) {
                    List<Order> responseOrder = response.body();
                    orderList.clear();
                    for (int i = 0; i < responseOrder.size(); i++) {
                        orderList.add(responseOrder.get(i));
                    }
                    activeOrderAdapter.notifyDataSetChanged();
                } else {
                    Log.i(TAG, "Code FAILURE");
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                call.cancel();
                homecareSessionManager.logout();
            }
        });
    }
}
