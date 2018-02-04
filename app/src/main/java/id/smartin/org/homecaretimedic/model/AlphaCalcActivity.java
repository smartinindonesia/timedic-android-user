package id.smartin.org.homecaretimedic.model;

/**
 * Created by Hafid on 1/14/2018.
 */

public class AlphaCalcActivity {
    private String actname;
    private float alphaValue;

    public AlphaCalcActivity() {
    }

    public AlphaCalcActivity(String actname, float alphaValue) {
        this.actname = actname;
        this.alphaValue = alphaValue;
    }

    public String getActname() {
        return actname;
    }

    public void setActname(String actname) {
        this.actname = actname;
    }

    public void setAlphaValue(float alphaValue) {
        this.alphaValue = alphaValue;
    }

    public float getAlphaValue(){
        return this.alphaValue;
    }

    @Override
    public String toString() {
        return actname;
    }
}
