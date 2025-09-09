package com.webflux.test.webflux.book.v2;

import com.webflux.test.webflux.book.mvc.Book;
import com.webflux.test.webflux.book.mvc.BookDto;
import com.webflux.test.webflux.book.v1.BookMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v2/reactive/books")
public class BookController {
    private final BookServiceV2 bookService;
    private final BookMapper mapper;

    public BookController(BookServiceV2 bookServiceV2, BookMapper mapper) {
        this.bookService = bookServiceV2;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto.Response> postBook(@RequestBody Mono<BookDto.Post> requestBody) {
        Mono<Book> result = bookService.createBook(requestBody);
//        Mono<BookDto.Response> response = mapper.bookToBookResponse(book);
        return result.flatMap(book -> Mono.just(mapper.bookToResponse(book)));
    }

    @PatchMapping("/{book-id}")
    public Mono patchBook(@PathVariable("book-id")long bookId,
                          @RequestBody Mono<BookDto.Patch> requestBody) {
//        requestBody.setBookId(bookId);
        Mono<Book> result = bookService.updateBook(bookId, requestBody);
//        return mapper.bookToBookResponse(book);
        return result.flatMap(book -> Mono.just(mapper.bookToResponse(book)));
    }

    @GetMapping("/{book-id}")
    public Mono getBook(@PathVariable("book-id") long bookId) {
//        Mono<Book> book = bookService.findBook(bookId);
//        return mapper.bookToBookResponse(book);
        return bookService.findBook(bookId)
                .flatMap(book -> Mono.just(mapper.bookToResponse(book)));
    }
}
