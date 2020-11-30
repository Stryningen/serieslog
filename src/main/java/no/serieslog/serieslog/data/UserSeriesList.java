package no.serieslog.serieslog.data;

import javax.persistence.*;

@IdClass(UserSeriesListId.class)
@Entity
public class UserSeriesList {

    @Id
    private Integer userId;
    @Id
    private Integer seriesId;

    @ManyToOne
    @JoinColumn(
            name = "userId",
            referencedColumnName = "id",
            insertable = false,
            updatable = false
    )
    private User user;

    @ManyToOne
    @JoinColumn(
            name = "seriesId",
            referencedColumnName = "id",
            insertable = false,
            updatable = false
    )
    private Series series;

    //getters and setters

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
