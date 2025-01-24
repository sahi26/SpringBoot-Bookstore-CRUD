package com.crud.spring;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
	
	@Autowired
	BookRepository bookRepository;
	
	@PostMapping("/books")
	public Book createBook(@RequestBody Book book) {
		 Book savedBook = bookRepository.save(book);
		 return savedBook;
	}
    
	@GetMapping("/books")
	public List<Book> retrieveAllBooks(){
		return bookRepository.findAll();
	}
	
	@PutMapping("/books/{id}")
	public Book updateBook(@PathVariable int id,@RequestBody Book book) {
		book.setId(id);
		return bookRepository.save(book);
	}
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<?> deleteBook(@PathVariable int id) {
	    try {
	        if (bookRepository.existsById(id)) {
	            bookRepository.deleteById(id);
	            return ResponseEntity.ok().build();
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting the book");
	    }
	}
	
	@GetMapping("/books/author/{author}")
	public List<Book> getBooksByAuthor(@PathVariable String author) {
	    return bookRepository.findByAuthor(author);
	}

	@GetMapping("/books/title/{keyword}")
	public List<Book> getBooksByTitleContaining(@PathVariable String keyword) {
	    return bookRepository.findByTitleContaining(keyword);
	}

	@GetMapping("/books/price")
	public List<Book> getBooksByPriceRange(@RequestParam double minPrice, @RequestParam double maxPrice) {
	    return bookRepository.findByPriceBetween(minPrice, maxPrice);
	}


}
