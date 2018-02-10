package id.smartin.org.homecaretimedic.config;

/**
 * Created by Hafid on 9/23/2017.
 */

public class Constants {
    //Action constants
    public static final String actionPosition = "device_pos";

    //RestApi constants
    //public static final String BASE_URL = "https://timedic.id:8443";
    public static final String BASE_URL = "http://192.168.1.4:7778";

    //USER
    public static final String ROUTE_LOGIN = "/authenticate/user?";
    public static final String ROUTE_RESGISTER = "/register/user";

    //AUTH CAREGIVER
    public static final String ROUTE_LOGIN_CAREGIVER = "/authenticate/caregiver";
    public static final String ROUTE_REGISTER_CAREGIVER = "/register/caregiver";

    //HOMESTAY HOMEVISIT
    public static final String ROUTE_HOMECARE_SERVICES_GET = "/api/homecareservices";
    public static final String ROUTE_HOMECARE_SERVICES_UTIL = "/api/homecareservices/"; //WITH ID PARAM INCLUDED BEHIND get,put,delete
    public static final String ROUTE_HOMECARE_SERVICES_POST = "/api/homecareservices";

    //PATIENT
    public static final String ROUTE_ALL_PATIENTS_LIST = "/api/patients";
    public static final String ROUTE_UTILS_PATIENTS_BY_ID_PATIENT = "/api/patients/"; //WITH ID PATIENT PARAM PUT GET DELETE POST
    public static final String ROUTE_PATIENTS_BY_ID_APP_USER = "/api/patients/findbyiduser/"; //WITH ID USER APP PARAM

    //HOMECARE TRANSACTION
    public static final String ROUTE_TRANSACTION = "/api/transactions/homecare/";
    public static final String ROUTE_ACTIVE_ORDER = "/api/orderactive/homecare/";
    public static final String ROUTE_HISTORY_ORDER = "/api/history/homecare/";

    public static final String ROUTE_ASSESTMENTLIST = "/api/assessments/";

    //API managing
    public static final String YOUTUBE_API_KEY = "AIzaSyCs06OoysZqT-UN1-er9Ob7q6cXzVPnViY";
    public static final String YOUTUBE_CHANNEL = "UCjDX29cI6EMpGwAIOER4vvg";
    public static final String YOUTUBE_PROJECT_NAME = "TimedicApps";
}
