package app.dao;

import app.domain.Book;

import java.util.*;

//@Singleton
public class BookDao {
	Map<String, Book> books;

	public BookDao() {
		books = new HashMap<>();

		Book book1 = new Book();
		book1.setId("1");
		book1.setTitle("Title1");
		book1.setAuthor("Author1");
		book1.setIsbn("1234");
		book1.setPublished(new Date());

		Book book2 = new Book();
		book2.setId("2");
		book2.setTitle("Title2");
		book2.setAuthor("Author2");
		book2.setIsbn("2345");
		book2.setPublished(new Date());

		books.put(book1.getId(), book1);
		books.put(book2.getId(), book2);
	}

	public Optional<Book> getBook(String id) {
		return Optional.ofNullable(books.get(id));
	}


	public Collection<Book> getBooks() {
		return books.values();
	}

	public Book addBook(Book book) {
		book.setId(UUID.randomUUID().toString());
		books.put(book.getId(), book);

		return book;
	}
}
