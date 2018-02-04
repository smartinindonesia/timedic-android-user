package id.smartin.org.homecaretimedic.tools.restservice;

import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.parammodel.RegisterParam;
import id.smartin.org.homecaretimedic.model.responsemodel.LoginResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Hafid on 1/2/2018.
 */

public interface UserAPIInterface {
    @POST(Constants.ROUTE_LOGIN)
    Call<LoginResponse> loginUser(@Query("username") String username, @Query("password") String password);
    @POST(Constants.ROUTE_RESGISTER)
    Call<ResponseBody> registerUser(@Body RegisterParam param);

}
