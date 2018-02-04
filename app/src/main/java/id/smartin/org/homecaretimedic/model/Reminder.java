package id.smartin.org.homecaretimedic.model;

import java.io.Serializable;

/**
 * Created by Hafid on 8/23/2017.
 */

public class Reminder implements Serializable {
    private String jenisObat;
    private String dosis;
    private String timeReminder;

    public Reminder() {
    }

    public Reminder(String jenisObat, String dosis, String timeReminder) {
        this.jenisObat = jenisObat;
        this.dosis = dosis;
        this.timeReminder = timeReminder;
    }

    public String getJenisObat() {
        return jenisObat;
    }

    public void setJenisObat(String jenisObat) {
        this.jenisObat = jenisObat;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getTimeReminder() {
        return timeReminder;
    }

    public void setTimeReminder(String timeReminder) {
        this.timeReminder = timeReminder;
    }

    @Override
    public String toString() {
        return "Reminder{" +
                "jenisObat='" + jenisObat + '\'' +
                ", dosis='" + dosis + '\'' +
                ", timeReminder='" + timeReminder + '\'' +
                '}';
    }
}

