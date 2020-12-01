package no.serieslog.serieslog.data;

import javax.persistence.*;

@IdClass(UserSeriesListId.class)
@Entity
public class UserSeriesList {

    @Id
    private Integer userId;
    @Id
    private Integer seriesId;

    private Double userScore;
    private boolean favorite;

    public UserSeriesList() {
    }

    public UserSeriesList(Integer userId, Integer seriesId) {
        this.userId = userId;
        this.seriesId = seriesId;
    }

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

    @Override
    public String toString() {
        return "User: " + user.getName() +
                ", Series: " + series.getSeriesName() +
                ", Score: " + userScore;
    }

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

    public Double getUserScore() {
        return userScore;
    }

    public void setUserScore(Double userScore) {
        this.userScore = userScore;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
