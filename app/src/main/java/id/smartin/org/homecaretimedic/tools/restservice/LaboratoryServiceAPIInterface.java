package id.smartin.org.homecaretimedic.tools.restservice;

import java.util.List;

import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.LabPackageItem;
import id.smartin.org.homecaretimedic.model.LabServices;
import id.smartin.org.homecaretimedic.model.responsemodel.AssessmentResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hafid on 2/25/2018.
 */

public interface LaboratoryServiceAPIInterface {
    @GET(Constants.ROUTE_LABORATORY_PACKAGES)
    Call<List<LabPackageItem>> getAllPackages();

    @GET(Constants.ROUTE_LABORATORY_SERVICES)
    Call<List<LabServices>> getAllServices();
}
