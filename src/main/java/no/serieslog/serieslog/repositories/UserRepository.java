package no.serieslog.serieslog.repositories;

import no.serieslog.serieslog.data.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}