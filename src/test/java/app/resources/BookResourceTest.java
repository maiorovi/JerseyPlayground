package app.resources;

import app.config.BookApplication;
import app.dao.BookDao;
import app.domain.Book;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BookResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
//		enable(TestProperties.RECORD_LOG_LEVEL);

		final BookDao bookDao = new BookDao();

		return new BookApplication(bookDao);
	}

	@Test
	public void returnsSingleBookByID() throws Exception {
		Book response = target("books").path("1").request().get(Book.class);
		assertThat(response).isNotNull();
	}

	@Test
	public void returnsAllBooks() throws Exception {
		List<Book> books = target("books").request().get(new GenericType<List<Book>>() {});
		assertThat(books.size()).isEqualTo(2);
	}

	//TODO:rename this test
	@Test
	public void testDaoTest() throws Exception {
		Book response1 = target("books").path("1").request().get(Book.class);
		Book response2 = target("books").path("1").request().get(Book.class);

		assertThat(response1.getPublished()).isEqualTo(response2.getPublished());
	}

	@Test
	public void addsBook() throws Exception {
		Book book = new Book();
		book.setTitle("title");
		book.setAuthor("author");

		Entity<Book> bookEntity = Entity.entity(book, MediaType.APPLICATION_JSON_TYPE);
		Response response = target("books").request().post(bookEntity);
		Response withEntity = target(response.getHeaderString("Location").replace("http://localhost:9998/", "")).request().get();
		Book actualBook = withEntity.readEntity(Book.class);

		assertThat(response.getStatus()).isEqualTo(Response.Status.CREATED.getStatusCode());
		assertThat(actualBook.getId()).isNotNull();
		assertThat(actualBook.getTitle()).isEqualTo("title");
		assertThat(actualBook.getAuthor()).isEqualTo("author");
	}
}
