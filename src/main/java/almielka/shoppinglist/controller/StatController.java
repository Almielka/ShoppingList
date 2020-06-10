package almielka.shoppinglist.controller;

import almielka.shoppinglist.domain.Stat;
import almielka.shoppinglist.service.StatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Anna S. Almielka
 */


@RestController
@RequestMapping(value = "/statistics", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatController {

    private StatService statService;
    private Long defaultId = 1L;

    public StatController(StatService statService) {
        this.statService = statService;
    }

    //read Stat by ID
    @GetMapping("/")
    public ResponseEntity<Stat> readStatById() {
        Optional<Stat> statOptional = statService.findById(defaultId);
        return statOptional.map(value -> ResponseEntity.status(HttpStatus.OK).body(value))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

    }

}
