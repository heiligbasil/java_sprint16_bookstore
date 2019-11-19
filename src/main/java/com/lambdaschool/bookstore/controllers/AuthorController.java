package com.lambdaschool.bookstore.controllers;

import com.lambdaschool.bookstore.logging.Loggable;
import com.lambdaschool.bookstore.models.Author;
import com.lambdaschool.bookstore.services.AuthorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@Loggable
@RequestMapping("/authors")
public class AuthorController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorController.class);

    @Autowired
    AuthorService authorService;

    // http://localhost:2019/authors/authors
    @GetMapping(value = "/authors", produces = {"application/json"})
    public ResponseEntity<?> listAllAuthors(HttpServletRequest request) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        List<Author> myAuthors = authorService.findAll();
        return new ResponseEntity<>(myAuthors, HttpStatus.OK);
    }


    // http://localhost:2019/authors/author/{authorId}
    @GetMapping(value = "/author/{authorId}", produces = {"application/json"})
    public ResponseEntity<?> getAuthorById(HttpServletRequest request, @PathVariable Long authorId) {
        logger.trace(request.getMethod().toUpperCase() + " " + request.getRequestURI() + " accessed");

        Author s = authorService.findAuthorById(authorId);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
