package no.serieslog.serieslog.data;

import java.io.Serializable;

public class UserSeriesListId implements Serializable {

    private Integer userId;
    private Integer seriesId;

    public UserSeriesListId() {
    }

    public UserSeriesListId(Integer userId, Integer seriesId) {
        this.userId = userId;
        this.seriesId = seriesId;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj.getClass() != UserSeriesListId.class){
            return false;
        }
        UserSeriesListId toCheck = (UserSeriesListId) obj;
        return toCheck.userId.equals(this.userId) && toCheck.seriesId.equals(this.seriesId);
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
}
