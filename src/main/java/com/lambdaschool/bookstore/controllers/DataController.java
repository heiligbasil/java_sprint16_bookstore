package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.logging.Loggable;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.models.Section;
import com.lambdaschool.bookstore.services.AuthorService;
import com.lambdaschool.bookstore.services.BookService;
import com.lambdaschool.bookstore.services.SectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@Loggable
@RequestMapping("/data")
public class DataController {
    private static final Logger logger = LoggerFactory.getLogger(DataController.class);

    @Autowired
    BookService bookService;

    @Autowired
    AuthorService authorService;

    @Autowired
    SectionService sectionService;

    // http://localhost:2019/data/authors
    /*
    {
        "fname": "Adam",
        "lname": "Smith"
    }
    */
    @PostMapping(value = "/authors", consumes = {"application/json"})
    public ResponseEntity<?> addNewAuthor(HttpServletRequest request, @Valid @RequestBody Author newAuthor) throws URISyntaxException {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        newAuthor = authorService.save(newAuthor);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newURI = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/authors/author/{authorId}").buildAndExpand(newAuthor.getAuthorid()).toUri();
        responseHeaders.setLocation(newURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    // http://localhost:2019/data/authors/{authorId}
    @PutMapping(value = "/authors/{authorId}", consumes = {"application/json"})
    /*
    {
       "fname": "Andrew"
    }
     */ public ResponseEntity<?> updateAuthorById(HttpServletRequest request, @RequestBody Author updateAuthor, @PathVariable long authorId) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        authorService.update(updateAuthor, authorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/data/authors/{authorId}
    @DeleteMapping(value = "/authors/{authorId}")
    public ResponseEntity<?> deleteAuthorById(HttpServletRequest request, @PathVariable long authorId) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        authorService.delete(authorId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/data/books
    /*
    {
        "title":"The End of the World or Not",
        "isbn":"1234567891231",
        "copy":1990,
        "section":{
            "sectionid":1,
        }
    }
    */
    @PostMapping(value = "/books", consumes = {"application/json"})
    public ResponseEntity<?> addNewBook(HttpServletRequest request, @Valid @RequestBody Book newBook) throws URISyntaxException {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        newBook = bookService.save(newBook);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newURI = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/books/book/{bookId}").buildAndExpand(newBook.getBookid()).toUri();
        responseHeaders.setLocation(newURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    // http://localhost:2019/data/books/{bookId}
    /*
    {
        "title": "DIGITALFORTRESS",
            "isbn": "1234567890123",
            "section": {
        "sectionid": 3
        }
    }
     */
    @PutMapping(value = "/books/{bookId}", consumes = {"application/json"})
    public ResponseEntity<?> updateBookById(HttpServletRequest request, @RequestBody Book updateBook, @PathVariable long bookId) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        bookService.update(updateBook, bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/data/books/{bookId}
    @DeleteMapping(value = "/books/{bookId}")
    public ResponseEntity<?> deleteBookById(HttpServletRequest request, @PathVariable long bookId) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        bookService.delete(bookId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/data/sections
    @PostMapping(value = "/sections", consumes = {"application/json"})
    /*
    {
        "name":"Current Events"
    }
     */

    public ResponseEntity<?> addNewSection(HttpServletRequest request, @Valid @RequestBody Section newSection) throws URISyntaxException {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        newSection = sectionService.save(newSection);

        // set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newURI = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/sections/section/{sectionId}").buildAndExpand(newSection.getSectionid()).toUri();
        responseHeaders.setLocation(newURI);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }


    // http://localhost:2019/data/sections/{sectionId}
    /*
    {
        "name":"Current Events"
    }
     */

    @PutMapping(value = "/sections/{sectionId}", consumes = {"application/json"})
    public ResponseEntity<?> updateSectionById(HttpServletRequest request, @RequestBody Section updateSection, @PathVariable long sectionId) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        sectionService.update(updateSection, sectionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/data/sections/{sectionId}
    @DeleteMapping(value = "/sections/{sectionId}")
    public ResponseEntity<?> deleteSectionById(HttpServletRequest request, @PathVariable long sectionId) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        sectionService.delete(sectionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/data/books/7/authors/2
    @DeleteMapping(value = "/books/{bookId}/authors/{authorId}")
    public ResponseEntity<?> deleteWrote(HttpServletRequest request, @PathVariable long bookId, @PathVariable long authorId) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        bookService.deleteWrote(bookId, authorId);

        return new ResponseEntity<>(HttpStatus.OK);
    }


    // http://localhost:2019/data/books/7/authors/2
    @PostMapping(value = "/books/{bookId}/authors/{authorId}")
    public ResponseEntity<?> addWrote(HttpServletRequest request, @PathVariable long bookId, @PathVariable long authorId) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        bookService.addWrote(bookId, authorId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
