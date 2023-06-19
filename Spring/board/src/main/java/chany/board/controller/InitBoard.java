package chany.board.controller;

import chany.board.domain.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
@RequiredArgsConstructor
public class InitBoard {

    private final InitBoardService initBoardService;

    @PostConstruct
    public void init() {
        initBoardService.init();
    }

    @Component
    static class InitBoardService {
        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
            for (int i = 0; i < 100; i++) {
                em.persist(new Board("Author" + i, "Title" + i, "Content" + i));
            }
        }
    }

}
