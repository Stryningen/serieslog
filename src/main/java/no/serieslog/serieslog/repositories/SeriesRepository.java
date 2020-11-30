package no.serieslog.serieslog.repositories;

import no.serieslog.serieslog.data.Series;
import org.springframework.data.repository.CrudRepository;

public interface SeriesRepository extends CrudRepository<Series, Integer> {
}
