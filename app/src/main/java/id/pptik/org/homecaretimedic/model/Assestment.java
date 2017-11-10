package id.pptik.org.homecaretimedic.model;

import android.graphics.Path;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hafid on 9/24/2017.
 */

public class Assestment {
    @SerializedName("id")
    private int id;
    @SerializedName("questions")
    private String questions;
    @SerializedName("root_id")
    private int root_id;
    private int id_multiple_choice;
    private String answer;
    private String path_file;
    private AssestmentType assestmentType;
    private List<OptionAssestment> optionAssestments;

    public Assestment(){
        optionAssestments = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getRoot_id() {
        return root_id;
    }

    public void setRoot_id(int root_id) {
        this.root_id = root_id;
    }

    public int getId_multiple_choice() {
        return id_multiple_choice;
    }

    public void setId_multiple_choice(int id_multiple_choice) {
        this.id_multiple_choice = id_multiple_choice;
    }

    public String getPath_file() {
        return path_file;
    }

    public void setPath_file(String path_file) {
        this.path_file = path_file;
    }

    public List<OptionAssestment> getOptionAssestments() {
        return optionAssestments;
    }

    public void setOptionAssestments(List<OptionAssestment> optionAssestments) {
        this.optionAssestments = optionAssestments;
    }

    public void addOptionAssestment(OptionAssestment optionAssestment){
        this.optionAssestments.add(optionAssestment);
    }

    public void clearOptionAssestment(){
        this.optionAssestments.clear();
    }

    public AssestmentType getAssestmentType() {
        return assestmentType;
    }

    public void setAssestmentType(AssestmentType assestmentType) {
        this.assestmentType = assestmentType;
    }

    public static class AssestmentType {
        @SerializedName("id")
        private int id;
        @SerializedName("type")
        private String type;

        public AssestmentType(){

        }

        public AssestmentType(int id, String type) {
            this.id = id;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class OptionAssestment{
        @SerializedName("id")
        private int id;
        @SerializedName("option")
        private String option;

        public OptionAssestment(){}

        public OptionAssestment(int id, String option) {
            this.id = id;
            this.option = option;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getOption() {
            return option;
        }

        public void setOption(String option) {
            this.option = option;
        }
    }
}
