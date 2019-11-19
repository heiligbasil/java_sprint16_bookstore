package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.exceptions.ResourceFoundException;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.logging.Loggable;
import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.repository.AuthorRepository;
import com.lambdaschool.bookstore.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Loggable
@Transactional
@Service("bookService")
public class BookServiceImpl implements BookService {
    @Autowired
    UserAuditing userAuditing;

    @Autowired
    BookRepository bookrepos;

    @Autowired
    SectionService sectionService;

    @Autowired
    AuthorRepository authorrepos;

    @Override
    public List<Book> findAll() {
        List<Book> list = new ArrayList<>();
        bookrepos.findAll().iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Book findBookById(long id) {
        return bookrepos.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " Not Found!"));
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (bookrepos.findById(id).isPresent()) {
            bookrepos.deleteById(id);
        } else {
            throw new ResourceNotFoundException("Book with id " + id + " Not Found!");
        }
    }

    @Transactional
    @Override
    public Book save(Book book) {
        if (book.getWrotes().size() > 0) {
            throw new ResourceFoundException("Wrotes are not added through Book.");
        }

        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setIsbn(book.getIsbn());
        newBook.setCopy(book.getCopy());
        if (book.getSection() != null) {
            newBook.setSection(sectionService.findSectionById(book.getSection().getSectionid()));
        }

        return bookrepos.save(newBook);
    }

    @Transactional
    @Override
    public Book update(Book book, long id) {
        if (book.getWrotes().size() > 0) {
            throw new ResourceFoundException("Wrotes are not added through Book.");
        }

        Book currentBook = findBookById(id);
        if (book.getTitle() != null) {
            currentBook.setTitle(book.getTitle());
        }

        if (book.getIsbn() != null) {
            currentBook.setIsbn(book.getIsbn());
        }

        if (book.getCopy() > 0) {
            currentBook.setCopy(book.getCopy());
        }

        if (book.getSection() != null) {
            currentBook.setSection(sectionService.findSectionById(book.getSection().getSectionid()));
        }

        return bookrepos.save(currentBook);
    }

    @Transactional
    @Override
    public void deleteWrote(long bookId, long authorId) {
        bookrepos.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book Id " + bookId + " not found!"));
        authorrepos.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Book Id " + bookId + " not found!"));

        if (bookrepos.checkWrote(bookId, authorId).getCount() > 0) {
            bookrepos.deleteWrote(bookId, authorId);
        } else {
            throw new ResourceFoundException("Book, Author combination does not exists");
        }
    }

    @Transactional
    @Override
    public void addWrote(long bookId, long authorId) {
        bookrepos.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book Id " + bookId + " not found!"));
        authorrepos.findById(authorId).orElseThrow(() -> new ResourceNotFoundException("Book Id " + bookId + " not found!"));

        if (bookrepos.checkWrote(bookId, authorId).getCount() <= 0) {
            bookrepos.insertWrote(bookId, authorId, userAuditing.getCurrentAuditor().get());
        } else {
            throw new ResourceFoundException("Book, Author combination already exists");
        }

    }
}
