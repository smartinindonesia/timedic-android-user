package id.pptik.org.homecaretimedic.model.submitmodel;

import java.util.ArrayList;
import java.util.List;

import id.pptik.org.homecaretimedic.model.Assestment;
import id.pptik.org.homecaretimedic.model.Klinik;
import id.pptik.org.homecaretimedic.model.Layanan;
import id.pptik.org.homecaretimedic.model.ServicePlace;
import id.pptik.org.homecaretimedic.model.User;

/**
 * Created by Hafid on 10/29/2017.
 */

public class SubmitInfo {
    public static Layanan selectedLayanan;
    public static ServicePlace selectedServicePlace;
    public static PlaceInfo selectedPlaceInfo;
    public static PickedDateTime selectedDateTime;
    public static User selectedCareGiver;
    public static Klinik selectedCareClinic;
    public static List<Assestment> assestmentList = new ArrayList<>();
}
