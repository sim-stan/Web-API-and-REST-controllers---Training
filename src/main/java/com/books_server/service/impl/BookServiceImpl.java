package com.books_server.service.impl;

import com.books_server.model.dto.AuthorDTO;
import com.books_server.model.dto.BookDTO;
import com.books_server.model.entity.AuthorEntity;
import com.books_server.model.entity.BookEntity;
import com.books_server.repository.AuthorRepository;
import com.books_server.repository.BookRepository;
import com.books_server.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookServiceImpl::mapBookToDTO)
                .toList();
    }

    @Override
    public Optional<BookDTO> findBookById(Long id) {

      return   bookRepository.findById(id).
              map(BookServiceImpl::mapBookToDTO);

    }

    @Override
    public void deleteBookById(long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Long createBook(BookDTO bookDTO) {

      AuthorEntity author= authorRepository.findByName(bookDTO.getAuthor().getName()).orElse(null);

      if (author==null){
          author=new AuthorEntity()
                  .setName(bookDTO.getAuthor().getName());
          author=authorRepository.save(author);
      }

     BookEntity newBook= new BookEntity().
                setAuthor(author).
                setIsbn(bookDTO.getIsbn()).
                setTitle(bookDTO.getTitle());

        newBook=bookRepository.save(newBook);

        return newBook.getId();
    }

    private static BookDTO mapBookToDTO(BookEntity bookEntity) {

        AuthorDTO author = new AuthorDTO()
                .setName(bookEntity.getAuthor().getName());

        return new BookDTO().
                setId(bookEntity.getId()).
                setAuthor(author).
                setIsbn(bookEntity.getIsbn()).
                setTitle(bookEntity.getTitle());
    }
}
