package grizzly;

import app.dao.BookDao;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {
	// Base URI the Grizzly HTTP server will listen on
	public static final String BASE_URI = "http://localhost:8080/myapp/";
	/**
	 * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
	 * @return Grizzly HTTP server.
	 */
	public static HttpServer startServer() {
		final BookDao bookDao = new BookDao();

		// create a resource config that scans for JAX-RS resources and providers
		// in test-grizzly package
		final ResourceConfig rc = new ResourceConfig().packages("grizzly", "app").register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(bookDao).to(BookDao.class);
			}
		});

		// create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
	}

	/**
	 * Main method.
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final HttpServer server = startServer();
		System.out.println(String.format("Jersey app started with WADL available at "
				+ "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
		System.in.read();
		server.stop();
	}
}
