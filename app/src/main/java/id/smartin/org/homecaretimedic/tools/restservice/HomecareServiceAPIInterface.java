package id.smartin.org.homecaretimedic.tools.restservice;

import java.util.ArrayList;

import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.HomecareService;
import id.smartin.org.homecaretimedic.model.parammodel.RegisterParam;
import id.smartin.org.homecaretimedic.model.responsemodel.LoginResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Hafid on 1/2/2018.
 */

public interface HomecareServiceAPIInterface {
    @GET(Constants.ROUTE_HOMECARE_SERVICES_GET)
    Call<ArrayList<HomecareService>> getHomecareServices();
}
