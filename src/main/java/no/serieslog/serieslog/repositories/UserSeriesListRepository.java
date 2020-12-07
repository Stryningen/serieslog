package no.serieslog.serieslog.repositories;

import no.serieslog.serieslog.data.Series;
import no.serieslog.serieslog.data.User;
import no.serieslog.serieslog.data.UserSeriesList;
import no.serieslog.serieslog.data.UserSeriesListId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserSeriesListRepository extends CrudRepository<UserSeriesList, UserSeriesListId> {
    List<UserSeriesList> findByUser(User user);

    List<UserSeriesList> findBySeries(Series series);

    UserSeriesList findByUserAndSeries(User user, Series series);
}
