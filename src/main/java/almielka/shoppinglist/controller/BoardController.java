package almielka.shoppinglist.controller;

import almielka.shoppinglist.domain.Board;
import almielka.shoppinglist.service.BoardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Extension of {@link AbstractController}
 * with explicitly defined the entity {@link Board} and the service {@link BoardService},
 * Service then called in the abstract constructor {@link AbstractController#AbstractController}
 *
 * @author Anna S. Almielka
 */

@RestController
@RequestMapping(value = "/boards", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController extends AbstractController<Board, BoardService> {

    public BoardController(BoardService boardService) {
        super(boardService);
    }

    //create Board
    @Override
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> createOne(@RequestBody Board newBoard) {
        return super.createOne(newBoard);
    }

    //read Board by ID
    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Board> readOneById(@PathVariable Long id) {
        return super.readOneById(id);
    }

    //read List Boards by Title containing
    @Override
    @GetMapping("/search/{title}")
    public ResponseEntity<List<Board>> readListByTitleContaining(@PathVariable String title) {
        return super.readListByTitleContaining(title);
    }

    //read All Boards by Title ASC
    @Override
    @GetMapping("/")
    public ResponseEntity<List<Board>> readAllOrderByTitleAsc() {
        return super.readAllOrderByTitleAsc();
    }

    //update Board by ID
    @Override
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> updateOneById(@PathVariable Long id, @RequestBody Board updateBoard) {
        return super.updateOneById(id, updateBoard);
    }

    //delete Board by ID
    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        return super.deleteOneById(id);
    }

}
