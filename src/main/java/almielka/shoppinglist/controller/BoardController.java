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
 * @author Anna S. Almielka
 */

@RestController
@RequestMapping(value = "/boards", produces = MediaType.APPLICATION_JSON_VALUE)
public class BoardController {

    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    //create Board
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> createOne(@RequestBody Board newBoard) {
        return ResponseEntity.status(HttpStatus.CREATED).body(boardService.saveAndFlush(newBoard));
    }

    //read Board by ID
    @GetMapping("/{id}")
    public ResponseEntity<Board> readOneById(@PathVariable Long id) {
        Optional<Board> boardOptional = boardService.findById(id);
        return boardOptional.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    //read List Boards by Title containing
    @GetMapping("/search/{title}")
    public ResponseEntity<List<Board>> readListByTitleContaining(@PathVariable String title) {
        List<Board> boards = boardService.findByTitleContaining(title);
        return checkBoardList(boards);
    }

    //read All Boards by Title ASC
    @GetMapping("/")
    public ResponseEntity<List<Board>> readAllOrderByTitleAsc() {
        List<Board> boards = boardService.findAllByOrderByTitleAsc();
        return checkBoardList(boards);
    }

    //update Board by ID
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Board> updateOneById(@PathVariable Long id, @RequestBody Board updateBoard) {
        Optional<Board> boardOptional = boardService.findById(id);
        if (boardOptional.isPresent()) {
            updateBoard.setId(boardOptional.get().getId());
            boardService.saveAndFlush(updateBoard);
            return ResponseEntity.status(HttpStatus.OK).body(updateBoard);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    //delete Board by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOneById(@PathVariable Long id) {
        try {
            boardService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    private ResponseEntity<List<Board>> checkBoardList(List<Board> boards) {
        if (boards.size() > 0) {
            return ResponseEntity.status(HttpStatus.OK).body(boards);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}
