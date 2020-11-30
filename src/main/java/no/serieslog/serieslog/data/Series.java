package no.serieslog.serieslog.data;

import javax.persistence.*;

@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer mazeId;
    @Column(unique = true)
    private String seriesName;
    private String seriesDescription;
    private String seriesImageUrl;
    private String seriesLinkUrl;
    private Double averageScore;

    public Series() {
    }

    public Series(String seriesName) {
        this.seriesName = seriesName;
    }

    //getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getSeriesDescription() {
        return seriesDescription;
    }

    public void setSeriesDescription(String seriesDescription) {
        this.seriesDescription = seriesDescription;
    }

    public String getSeriesImageUrl() {
        return seriesImageUrl;
    }

    public void setSeriesImageUrl(String seriesImageUrl) {
        this.seriesImageUrl = seriesImageUrl;
    }

    public String getSeriesLinkUrl() {
        return seriesLinkUrl;
    }

    public void setSeriesLinkUrl(String seriesLinkUrl) {
        this.seriesLinkUrl = seriesLinkUrl;
    }

    public Double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Double averageScore) {
        this.averageScore = averageScore;
    }

    public Integer getMazeId() {
        return mazeId;
    }

    public void setMazeId(Integer mazeId) {
        this.mazeId = mazeId;
    }
}
