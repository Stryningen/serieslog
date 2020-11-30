package no.serieslog.serieslog.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Series {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String seriesName;
    private String seriesDescription;
    private String seriesImageUrl;
    private String seriesLinkUrl;

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
}
