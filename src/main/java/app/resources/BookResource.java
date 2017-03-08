package app.resources;


import app.dao.BookDao;
import app.domain.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import java.util.Collection;

@Path("books")
public class BookResource {
	//jersey is bundled with hk2 dependency injection framework
	@Context
	private Request request;
	//@Context provides
	//1.application object
	//2.uriInfo
	//3.Request
	//4.HttpHeaders
	//5.SecurityContext
	//6.Providers (ExceptionsMappers, MessageBodyReader/Writer, ContextResolver)

	@Context
	private BookDao bookDao;


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
