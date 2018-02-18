package id.smartin.org.homecaretimedic.tools.restservice;

import java.util.List;

import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.Assessment;
import id.smartin.org.homecaretimedic.model.responsemodel.AssessmentResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Hafid on 1/11/2018.
 */

public interface HomecareAssessmentAPIInterface {
    @GET(Constants.ROUTE_ASSESTMENTS_BY_ID_SERVICES + "{id_service}")
    Call<List<AssessmentResponse>> getAssessmentByIdService(@Path(value = "id_service", encoded = true) Long id);

    @GET(Constants.ROUTE_ASSESTMENTLIST)
    Call<List<AssessmentResponse>> getAllAssessmentData();
}
