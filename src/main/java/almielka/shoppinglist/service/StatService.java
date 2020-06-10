package almielka.shoppinglist.service;

import almielka.shoppinglist.domain.Stat;

import java.util.Optional;

/**
 * StatService interface includes additional methods only for Stat
 *
 * @author Anna S. Almielka
 */

public interface StatService {

    Optional<Stat> findById(Long id);
}
