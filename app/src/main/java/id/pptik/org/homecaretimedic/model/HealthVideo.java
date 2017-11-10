package id.pptik.org.homecaretimedic.model;

/**
 * Created by Hafid on 8/23/2017.
 */

public class HealthVideo {
    private String judulVideo;
    private String videoUrl;
    private String ket;

    public HealthVideo() {
    }

    public HealthVideo(String judulVideo, String videoUrl, String ket) {
        this.judulVideo = judulVideo;
        this.videoUrl = videoUrl;
        this.ket = ket;
    }

    public String getJudulVideo() {
        return judulVideo;
    }

    public void setJudulVideo(String judulVideo) {
        this.judulVideo = judulVideo;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    @Override
    public String toString() {
        return "HealthVideo{" +
                "judulVideo='" + judulVideo + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", ket='" + ket + '\'' +
                '}';
    }
}
