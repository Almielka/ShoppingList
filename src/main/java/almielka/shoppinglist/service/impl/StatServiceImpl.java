package almielka.shoppinglist.service.impl;

import almielka.shoppinglist.domain.Stat;
import almielka.shoppinglist.repository.StatRepository;
import almielka.shoppinglist.service.StatService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Anna S. Almielka
 */

@Service
public class StatServiceImpl implements StatService {

    private StatRepository statRepository;

    public StatServiceImpl(StatRepository statRepository) {
        this.statRepository = statRepository;
    }

    @Override
    public Optional<Stat> findById(Long id) {
        return statRepository.findById(id);
    }
}
