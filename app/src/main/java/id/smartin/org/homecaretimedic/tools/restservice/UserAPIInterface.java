package id.smartin.org.homecaretimedic.tools.restservice;

import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.User;
import id.smartin.org.homecaretimedic.model.parammodel.PasswordProfile;
import id.smartin.org.homecaretimedic.model.parammodel.RegisterParam;
import id.smartin.org.homecaretimedic.model.responsemodel.LoginResponse;
import id.smartin.org.homecaretimedic.model.parammodel.UserProfile;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Hafid on 1/2/2018.
 */

public interface UserAPIInterface {
    @POST(Constants.ROUTE_LOGIN)
    Call<LoginResponse> loginUser(@Query("username") String username, @Query("password") String password);

    @POST(Constants.ROUTE_LOGIN_FIREBASE)
    Call<LoginResponse> loginUserWithFirebase(@Query("firebaseId") String firebaseId, @Query("type") String type);

    @POST(Constants.ROUTE_RESGISTER)
    Call<ResponseBody> registerUser(@Body RegisterParam param);

    @PUT(Constants.ROUTE_USER_BY_ID + "{id}")
    Call<ResponseBody> updateUser(@Path(value = "id", encoded = true) Long id, @Body User user);

    @PUT(Constants.ROUTE_USER_BY_ID + "{id}")
    Call<ResponseBody> updateProfile(@Path(value = "id", encoded = true) Long id, @Body UserProfile user);

    @PUT(Constants.ROUTE_USER_BY_ID + "{id}")
    Call<ResponseBody> updatePassword(@Path(value = "id", encoded = true) Long id, @Body PasswordProfile pass);

    @GET(Constants.ROUTE_USER_BY_ID + "{id}")
    Call<User> getProfile(@Path(value = "id", encoded = true) Long id);
}
