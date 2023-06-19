package chany.board.repository;

import chany.board.dto.BoardDto;
import chany.board.dto.BoardSearchCondition;
import chany.board.dto.QBoardDto;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static chany.board.domain.QBoard.board;

public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BoardDto> search(BoardSearchCondition condition, Pageable pageable) {

        QueryResults<BoardDto> results = queryFactory
                .select(new QBoardDto(
                        board.id,
                        board.author,
                        board.title,
                        board.content,
                        board.createdDate,
                        board.modifiedDate
                ))
                .from(board)
                .where(
                        authorLike(condition.getAuthor()),
                        titleLike(condition.getTitle())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<BoardDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression authorLike(String author) {
        return StringUtils.hasText(author) ? board.author.containsIgnoreCase(author) : null;
    }

    private BooleanExpression titleLike(String title) {
        return StringUtils.hasText(title) ? board.title.containsIgnoreCase(title) : null;
    }
}
