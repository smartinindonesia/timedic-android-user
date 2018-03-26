package id.smartin.org.homecaretimedic.tools.restservice;

import java.util.List;

import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.HomecareOrder;
import id.smartin.org.homecaretimedic.model.parammodel.HomecareTransParam;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Hafid on 1/20/2018.
 */

public interface HomecareTransactionAPIInterface {

    @GET(Constants.ROUTE_TRANSACTION)
    Call<ResponseBody> getAllTransaction();

    @GET(Constants.ROUTE_TRANSACTION + "{id}")
    Call<ResponseBody> getTransactionById(@Path(value = "id", encoded = true) Long id);

    @DELETE(Constants.ROUTE_TRANSACTION + "{id}")
    Call<ResponseBody> deleteTransactionById(@Path(value = "id", encoded = true) Long id);

    @POST(Constants.ROUTE_TRANSACTION)
    Call<ResponseBody> insertNewTransaction(@Body HomecareTransParam param);

    @GET(Constants.ROUTE_HISTORY_ORDER + "{idUser}")
    Call<List<HomecareOrder>> getHistoryOrderByIdUser(@Path(value = "idUser", encoded = true) Long id);

    @GET(Constants.ROUTE_ACTIVE_ORDER + "{id}")
    Call<List<HomecareOrder>> getActiveOrder(@Path(value = "id", encoded = true) Long id);
}
