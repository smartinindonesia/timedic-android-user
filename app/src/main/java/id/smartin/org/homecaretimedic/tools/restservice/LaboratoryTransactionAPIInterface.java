package id.smartin.org.homecaretimedic.tools.restservice;

import java.util.List;

import id.smartin.org.homecaretimedic.config.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Hafid on 2/25/2018.
 */

public interface LaboratoryTransactionAPIInterface {
    @GET(Constants.ROUTE_LABORATORY_ACTIVE_ORDER)
    Call<List<ResponseBody>> getAllActiveOrders();

    @GET(Constants.ROUTE_LABORATORY_HISTORY_ORDER)
    Call<List<ResponseBody>> getAllHistoryOrders();

    @POST(Constants.ROUTE_LABORATORY_TRANSACTION)
    Call<ResponseBody> insertNewTransaction();

}
