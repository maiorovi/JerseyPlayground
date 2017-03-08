package app.config;

import app.dao.BookDao;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;

public class BookApplication extends ResourceConfig {

	public BookApplication(final BookDao bookDao) {
		packages("app.resources", "grizzly");
		register(new AbstractBinder() {
			@Override
			protected void configure() {
				bind(bookDao).to(BookDao.class);
			}
		});
	}

}
