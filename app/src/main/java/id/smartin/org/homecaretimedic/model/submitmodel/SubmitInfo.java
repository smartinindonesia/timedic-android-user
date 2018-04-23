package id.smartin.org.homecaretimedic.model.submitmodel;

import java.util.ArrayList;
import java.util.List;

import id.smartin.org.homecaretimedic.model.HomecareClinic;
import id.smartin.org.homecaretimedic.model.HomecareService;
import id.smartin.org.homecaretimedic.model.LabPackageItem;
import id.smartin.org.homecaretimedic.model.LabServices;
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
    public static boolean FINISH_STATE = false;

    /*GLOBAL FOR EVERY SERVICE*/
    public static ServicePlace selectedServicePlace; //Lokasi layanan
    public static PlaceInfo selectedPlaceInfo; //HomecareService tempat

    public static String serviceAvailable;

    /*NURSERY*/
    public static HomecareService selectedHomecareService; //HomecareService yang dipilih HomeStay or HomeVisit
    public static ArrayList<Patient> registeredPatient = new ArrayList<>(); // User yang ingin didaftarkan
    public static PickedDateTime selectedDateTime; //Tanggal dan waktu layanan yg dipilih
    public static User selectedCareGiver;
    public static HomecareClinic selectedCareClinic;
    public static List<AssessmentAnswerParam> assessmentList = new ArrayList<>();
    public static List<LabPackageItem> selectedLabPackages = new ArrayList<>();
    public static List<LabServices> selectedLabServices = new ArrayList<>();

    public static void clearAllData(){
        selectedLabServices.clear();
        registeredPatient.clear();
        selectedDateTime = new PickedDateTime();
        selectedCareGiver = new User();
        selectedCareClinic = new HomecareClinic();
        assessmentList.clear();
        selectedLabPackages.clear();
        selectedLabServices.clear();
        FINISH_STATE = true;
    }

    public static boolean isFinishTransaction() {
        return FINISH_STATE;
    }

    public static double getAssessmentPrice(){
        double price = 0;
        if (assessmentList != null) {
            for (int i = 0; i < assessmentList.size(); i++) {
                price = price + assessmentList.get(i).getPrice();
            }
        }
        return price;
    }

    public static List<AssessmentAnswerParam> getPricedAssessment(){
        List<AssessmentAnswerParam> assessmentAnswerParamList = new ArrayList<>();
        for (int i = 0; i < assessmentList.size(); i++){
            if (assessmentList.get(i).getPrice() > 0){
                assessmentAnswerParamList.add(assessmentList.get(i));
            }
        }
        return assessmentAnswerParamList;
    }

}
