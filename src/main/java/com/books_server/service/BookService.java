package com.books_server.service;

import com.books_server.model.dto.BookDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface BookService {

    List<BookDTO> getAllBooks();

    Optional<BookDTO> findBookById(Long id);

   void deleteBookById(long id);

   Long createBook(BookDTO bookDTO);
}
