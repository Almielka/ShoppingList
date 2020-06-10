package almielka.shoppinglist.service.impl;

import almielka.shoppinglist.domain.Board;
import almielka.shoppinglist.repository.BoardRepository;
import almielka.shoppinglist.service.BoardService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Extension of {@link AbstractServiceImpl}
 * with explicitly defined the repository,
 * which is then called in the abstract constructor {@link AbstractServiceImpl#AbstractServiceImpl}
 * entity {@link Board}, repository {@link BoardRepository}
 *
 * @author Anna S. Almielka
 */

@Service
public class BoardServiceImpl extends AbstractServiceImpl<Board, BoardRepository>
        implements BoardService {

    public BoardServiceImpl(BoardRepository boardRepository) {
        super(boardRepository);
    }

//    @Override
//    public List<Board> findByTitleContaining(String name) {
//        return repository.findByTitleContaining(name);
//    }

}
