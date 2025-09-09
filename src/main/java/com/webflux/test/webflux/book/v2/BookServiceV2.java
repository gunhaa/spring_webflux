package com.webflux.test.webflux.book.v2;

import com.webflux.test.webflux.book.mvc.Book;
import com.webflux.test.webflux.book.mvc.BookDto;
import com.webflux.test.webflux.book.v1.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BookServiceV2 {

    private final BookMapper mapper;

    public Mono<Book> createBook(Mono<BookDto.Post> book) {
        return book.flatMap(post -> Mono.just(mapper.bookPostToBook(post)));
    }

    public Mono<Book> updateBook(long bookId, Mono<BookDto.Patch> book) {
        return book.flatMap(patch -> {
            patch.setBookId(bookId);
            return Mono.just(mapper.bookPatchToBook(patch));
        });
    }

    public Mono<Book> findBook(long bookId) {
        return Mono.just(
                new Book(bookId,
                        "Java 고급",
                        "Advanced Java",
                        "Kevin",
                        "111-11-1111-111-1",
                        "Java 중급 프로그래밍 마스터",
                        "2022-03-22",
                        LocalDateTime.now(),
                        LocalDateTime.now())
        );
    }
}
