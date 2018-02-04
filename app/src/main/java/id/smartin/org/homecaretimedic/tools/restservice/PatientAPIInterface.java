package id.smartin.org.homecaretimedic.tools.restservice;

import java.util.ArrayList;
import java.util.List;

import id.smartin.org.homecaretimedic.config.Constants;
import id.smartin.org.homecaretimedic.model.Patient;
import id.smartin.org.homecaretimedic.model.parammodel.RegPatientParam;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Hafid on 1/7/2018.
 */

public interface PatientAPIInterface {

    @GET(Constants.ROUTE_ALL_PATIENTS_LIST)
    Call<ArrayList<Patient>> getAllPatients();

    @GET(Constants.ROUTE_PATIENTS_BY_ID_APP_USER + "{idAppUser}")
    Call<ArrayList<Patient>> getPatientByUserId(@Path(value = "idAppUser", encoded = true) Long id);

    @GET(Constants.ROUTE_UTILS_PATIENTS_BY_ID_PATIENT + "{patient_id}")
    Call<Patient> getPatientDetailByIdPatient(@Path(value = "patient_id", encoded = true) Long id);

    @DELETE(Constants.ROUTE_UTILS_PATIENTS_BY_ID_PATIENT + "{patient_id}")
    Call<ResponseBody> deletePatientById(@Path(value = "patient_id", encoded = true) Long id);

    @PUT(Constants.ROUTE_UTILS_PATIENTS_BY_ID_PATIENT + "{patient_id}")
    Call<ResponseBody> editUser(@Path(value = "patient_id", encoded = true) Long id, @Body RegPatientParam patient);

    @POST(Constants.ROUTE_UTILS_PATIENTS_BY_ID_PATIENT)
    Call<Patient> insertNewPatient(@Body RegPatientParam patient);

}
