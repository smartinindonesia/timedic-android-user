package id.smartin.org.homecaretimedic.model;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.Video;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * Created by Hafid on 8/23/2017.
 */

public class HealthVideo implements Serializable {
    private String id;
    private String title;
    private DateTime publishedAt;
    private String description;
    private BigInteger like;
    private BigInteger dislike;
    private BigInteger view;

    public HealthVideo(Video v) {
        this.id = v.getId();
        this.title = v.getSnippet().getTitle();
        this.publishedAt = v.getSnippet().getPublishedAt();
        this.like = v.getStatistics().getLikeCount();
        this.dislike = v.getStatistics().getDislikeCount();
        this.view = v.getStatistics().getViewCount();
        this.description = v.getSnippet().getDescription();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(DateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigInteger getLike() {
        return like;
    }

    public void setLike(BigInteger like) {
        this.like = like;
    }

    public BigInteger getDislike() {
        return dislike;
    }

    public void setDislike(BigInteger dislike) {
        this.dislike = dislike;
    }

    public BigInteger getView() {
        return view;
    }

    public void setView(BigInteger view) {
        this.view = view;
    }
}
