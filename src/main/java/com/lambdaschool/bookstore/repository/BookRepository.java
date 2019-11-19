package com.lambdaschool.bookstore.repository;

import com.lambdaschool.bookstore.models.Book;
import com.lambdaschool.bookstore.view.JustTheCount;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookRepository extends CrudRepository<Book, Long> {
    @Transactional
    @Modifying
    // INSERT INTO `bookstore`.`wrote` (`authorid`, `bookid`) VALUES ('6', '2');
    @Query(value = "INSERT INTO wrote(bookid, authorid, created_by, created_date, last_modified_by, last_modified_date) VALUES (:bookid, :authorid, :uname, CURRENT_TIMESTAMP, :uname, CURRENT_TIMESTAMP)", nativeQuery = true)
    void insertWrote(long bookid, long authorid, String uname);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM wrote WHERE bookid = :bookId AND authorid = :authorId", nativeQuery = true)
    void deleteWrote(long bookId, long authorId);


    @Query(value = "SELECT COUNT(*) as count FROM wrote WHERE bookid = :bookId AND authorid = :authorId", nativeQuery = true)
    JustTheCount checkWrote(long bookId, long authorId);
}
