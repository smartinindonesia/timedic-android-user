package id.smartin.org.homecaretimedic.model.submitmodel;

import java.util.ArrayList;
import java.util.List;

import id.smartin.org.homecaretimedic.model.Assessment;
import id.smartin.org.homecaretimedic.model.HomecareService;
import id.smartin.org.homecaretimedic.model.Klinik;
import id.smartin.org.homecaretimedic.model.Patient;
import id.smartin.org.homecaretimedic.model.ServicePlace;
import id.smartin.org.homecaretimedic.model.User;
import id.smartin.org.homecaretimedic.model.parammodel.AssessmentAnswerParam;

/**
 * Created by Hafid on 10/29/2017.
 */

public class SubmitInfo {
    /*
     * Submit State
     */
    public static boolean EDIT_STATE = false;

    /*GLOBAL FOR EVERY SERVICE*/
    public static ServicePlace selectedServicePlace; //Lokasi layanan
    public static PlaceInfo selectedPlaceInfo; //HomecareService tempat

    public static String serviceAvailable;

    /*NURSERY*/
    public static HomecareService selectedHomecareService; //HomecareService yang dipilih HomeStay or HomeVisit
    public static ArrayList<Patient> registeredPatient = new ArrayList<>(); // User yang ingin didaftarkan
    public static PickedDateTime selectedDateTime; //Tanggal dan waktu layanan yg dipilih
    public static User selectedCareGiver;
    public static Klinik selectedCareClinic;
    public static List<AssessmentAnswerParam> assessmentList = new ArrayList<>();

    public void clearAllData(){

    }
}
