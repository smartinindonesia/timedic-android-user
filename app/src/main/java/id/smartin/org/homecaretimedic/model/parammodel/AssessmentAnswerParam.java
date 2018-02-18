package id.smartin.org.homecaretimedic.model.parammodel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hafid on 2/18/2018.
 */

public class AssessmentAnswerParam {
    @SerializedName("assessmentAnswer")
    private String assessmentAnswer;
    @SerializedName("filePath")
    private String filePath;
    @SerializedName("idAssessment")
    private IdAssessment idAssessment;

    public String getAssessmentAnswer() {
        return assessmentAnswer;
    }

    public void setAssessmentAnswer(String assessmentAnswer) {
        this.assessmentAnswer = assessmentAnswer;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public IdAssessment getIdAssessment() {
        return idAssessment;
    }

    public void setIdAssessment(IdAssessment idAssessment) {
        this.idAssessment = idAssessment;
    }

    class IdAssessment {
        @SerializedName("id")
        private Long id;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }
    }
}
