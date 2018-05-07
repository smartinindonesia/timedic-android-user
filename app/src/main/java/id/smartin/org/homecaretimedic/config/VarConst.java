package id.smartin.org.homecaretimedic.config;

import java.util.ArrayList;
import java.util.List;

import id.smartin.org.homecaretimedic.R;
import id.smartin.org.homecaretimedic.model.GenderOption;
import id.smartin.org.homecaretimedic.model.MedicineType;
import id.smartin.org.homecaretimedic.model.Religion;

/**
 * Created by Hafid on 07/05/2018.
 */

public class VarConst {
    public static List<Religion> getReligionList() {
        List<Religion> religions = new ArrayList<>();
        religions.add(new Religion((long) 1, "Islam"));
        religions.add(new Religion((long) 2, "Kristen"));
        religions.add(new Religion((long) 3, "Katolik"));
        religions.add(new Religion((long) 4, "Hindu"));
        religions.add(new Religion((long) 5, "Budha"));
        religions.add(new Religion((long) 6, "Kong Hu Cu"));
        religions.add(new Religion((long) 7, "Tidak Ada"));
        return religions;
    }

    public static List<GenderOption> getGenders() {
        List<GenderOption> genderOptions = new ArrayList<>();
        genderOptions.add(new GenderOption(R.drawable.btn_laki_laki, "Laki-Laki"));
        genderOptions.add(new GenderOption(R.drawable.btn__perempuan, "Perempuan"));
        return genderOptions;
    }

    public static List<MedicineType> getMedType() {
        List<MedicineType> types = new ArrayList<>();
        types.add(new MedicineType(0,"Tablet",0));
        types.add(new MedicineType(1,"Kapsul",0));
        types.add(new MedicineType(2,"Kaplet",0));
        types.add(new MedicineType(3,"Sendok",0));
        return types;
    }
}
