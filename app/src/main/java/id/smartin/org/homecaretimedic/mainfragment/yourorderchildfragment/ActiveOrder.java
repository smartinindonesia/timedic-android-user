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
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.adapter.ActiveOrderAdapter;
import id.smartin.org.homecaretimedic.customuicompt.EndlessScrollListener;
import id.smartin.org.homecaretimedic.manager.HomecareSessionManager;
import id.smartin.org.homecaretimedic.model.HomecareOrder;
import id.smartin.org.homecaretimedic.model.responsemodel.HomecareListResponse;
import id.smartin.org.homecaretimedic.tools.restservice.APIClient;
import id.smartin.org.homecaretimedic.tools.restservice.HomecareTransactionAPIInterface;
import okhttp3.ResponseBody;
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
    @BindView(R.id.loadingBar)
    ProgressBar loadingBar;

    private HomecareSessionManager homecareSessionManager;
    private List<HomecareOrder> homecareOrderList = new ArrayList<>();
    private ActiveOrderAdapter activeOrderAdapter;
    private HomecareTransactionAPIInterface homecareTransactionAPIInterface;

    //PAGINATION
    private Integer page = 0;
    private Integer sizePerPage = 6;
    private Integer maxPage = 0;
    private boolean isLoading = false;

    private boolean _hasLoadedOnce = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        resetData();
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
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(activeOrderAdapter);
        recyclerView.setOnScrollListener(new EndlessScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                fetchNext();
            }
        });
        getActiveOrder();
        // Inflate the layout for this fragment
        return view;
    }

    public void getActiveOrderPagination() {
        loadingBar.setVisibility(View.VISIBLE);
        Call<ResponseBody> services = homecareTransactionAPIInterface.getActiveOrderPage(page, sizePerPage, "DESC", "id", homecareSessionManager.getUserDetail().getId());
        services.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        HomecareListResponse homecareListResponse = new HomecareListResponse();
                        homecareListResponse.setResponse(response.body());
                        homecareListResponse.convertResponse();
                        homecareOrderList.addAll(homecareListResponse.getHomecareOrders());
                        activeOrderAdapter.notifyDataSetChanged();
                        maxPage = (int) Math.round(homecareListResponse.getNumberOfRow() / sizePerPage);
                        Log.i(TAG, maxPage + "Number of pages");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, response.body().toString());
                } else {
                    Log.i(TAG, "Code FAILURE");
                }
                loadingBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                call.cancel();
                loadingBar.setVisibility(View.GONE);
                homecareSessionManager.logout();
            }
        });
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

    public void fetchPrev() {
        if (this.page > 0) {
            this.page--;
            this.getActiveOrderPagination();
        }
    }

    public void fetchNext() {
        if (this.page < (this.maxPage)) {
            this.page++;
            this.getActiveOrderPagination();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isFragmentVisible_) {
        super.setUserVisibleHint(true);

        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isFragmentVisible_ && !_hasLoadedOnce) {
                resetData();
                _hasLoadedOnce = true;
            }
        }
    }

    private void resetData(){
        page = 0;
        homecareOrderList.clear();
        getActiveOrderPagination();
    }
}
