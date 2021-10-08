package chany.board2.repository;

import chany.board2.dto.BoardResponseDto;
import chany.board2.dto.QBoardResponseDto;
import chany.board2.dto.SearchCondition;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;

import static chany.board2.domain.QBoard.board;


public class BoardRepositoryImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BoardResponseDto> searchBoardByCondition(SearchCondition condition, Pageable pageable) {
        QueryResults<BoardResponseDto> results = queryFactory
                .select(new QBoardResponseDto(
                        board.id,
                        board.author,
                        board.title,
                        board.content,
                        board.createdDate
                ))
                .from(board)
                .where(
                        authorLike(condition.getAuthor()),
                        titleLike(condition.getTitle()),
                        contentLike(condition.getContent())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(results.getResults(), pageable, results.getTotal());
    }

    private BooleanExpression authorLike(String author) {
        return StringUtils.hasText(author) ? board.author.containsIgnoreCase(author) : null;
    }

    private BooleanExpression titleLike(String title) {
        return StringUtils.hasText(title) ? board.title.containsIgnoreCase(title) : null;
    }

    private BooleanExpression contentLike(String content) {
        return StringUtils.hasText(content) ? board.content.containsIgnoreCase(content) : null;
    }
}
