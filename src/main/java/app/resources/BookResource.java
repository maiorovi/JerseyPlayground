package app.resources;


import app.dao.BookDao;
import app.domain.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("books")
public class BookResource {


	BookDao bookDao = new BookDao();


	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Book> getBooks() {
		return bookDao.getBooks();
	}

	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Book getBook(@PathParam("id")String id) {
		return bookDao.getBook(id).orElseThrow(() -> new WebApplicationException("Incorect id", Response.Status.NOT_FOUND));
	}

}
