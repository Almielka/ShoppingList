package almielka.shoppinglist.repository;

import almielka.shoppinglist.domain.Stat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Stat Repository interface
 * includes methods only for Stat
 *
 * @author Anna S. Almielka
 */

@Repository
public interface StatRepository extends JpaRepository<Stat, Long> {
    Optional<Stat> findById(Long id);
}
