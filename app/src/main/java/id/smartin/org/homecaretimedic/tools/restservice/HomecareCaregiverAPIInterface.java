package id.smartin.org.homecaretimedic.tools.restservice;

import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.Caregiver;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hafid on 3/27/2018.
 */

public interface HomecareCaregiverAPIInterface {
    @GET(Constants.ROUTE_CAREGIVER + "{id}")
    Call<Caregiver> getCaregiver(@Path(value = "id", encoded = true) Long id);
}
