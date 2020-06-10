package almielka.shoppinglist.repository;

import almielka.shoppinglist.domain.Board;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Extension of {@link AbstractRepository} with concrete generic Board
 * includes additional methods only for Board
 *
 * @author Anna S. Almielka
 */

@Repository
public interface BoardRepository extends AbstractRepository<Board> {

//    /**
//     * Retrieve {@link Board}s from the database by {@code title}, returning all boards
//     * whose title <i>containing</i> with the given title.
//     *
//     * @param title Value to search for
//     * @return a Collection of matching boards (or an empty Collection if none found)
//     */
//    @Query(value = "SELECT * " +
//            "FROM board b " +
//            "WHERE b.title LIKE %:title%",
//            nativeQuery = true)
//    @Transactional(readOnly = true)
//    @Override
//    List<Board> findByTitleContaining(@Param("title")String title);

}
