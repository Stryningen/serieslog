package no.serieslog.serieslog.repositories;

import no.serieslog.serieslog.data.UserSeriesList;
import no.serieslog.serieslog.data.UserSeriesListId;
import org.springframework.data.repository.CrudRepository;

public interface UserSeriesListRepository extends CrudRepository<UserSeriesList, UserSeriesListId> {
}
