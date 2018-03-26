package id.smartin.org.homecaretimedic.mainfragment.yourorderchildfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.adapter.ActiveOrderAdapter;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.HomecareOrder;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
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
    private List<HomecareOrder> homecareOrderList = new ArrayList<>();
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
        activeOrderAdapter = new ActiveOrderAdapter(getActivity(), getContext(), homecareOrderList);
        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(activeOrderAdapter);
        getActiveOrder();
        // Inflate the layout for this fragment
        return view;
    }

    public void getActiveOrder() {
        Call<List<HomecareOrder>> services = homecareTransactionAPIInterface.getActiveOrder(homecareSessionManager.getUserDetail().getId());
        services.enqueue(new Callback<List<HomecareOrder>>() {
            @Override
            public void onResponse(Call<List<HomecareOrder>> call, Response<List<HomecareOrder>> response) {
                if (response.code() == 200) {
                    List<HomecareOrder> responseHomecareOrder = response.body();
                    homecareOrderList.clear();
                    for (int i = 0; i < responseHomecareOrder.size(); i++) {
                        homecareOrderList.add(responseHomecareOrder.get(i));
                    }
                    activeOrderAdapter.notifyDataSetChanged();
                } else {
                    Log.i(TAG, "Code FAILURE");
                }
            }

            @Override
            public void onFailure(Call<List<HomecareOrder>> call, Throwable t) {
                call.cancel();
                homecareSessionManager.logout();
            }
        });
    }
}
