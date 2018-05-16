package id.smartin.org.homecaretimedic.tools.restservice;

import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.responsemodel.ContactRes;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hafid on 02/05/2018.
 */

public interface ContactUsAPIInterface {
    @GET(Constants.ROUTE_CONTACT + "{id}")
    Call<ContactRes> getContactDetails(@Path(value = "id", encoded = true) Long id);
}
