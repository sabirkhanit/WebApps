package webapp.appsconfig.config;

import java.util.Map;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.lucene.index.IndexWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import webapp.appsconfig.util.AppConstants;
import webapp.appsconfig.util.IndexWriterUtil;

@Component
public class AppServletContextListener implements ServletContextListener{
	
	private final Map<String,IndexWriter> indexWriters ;
	
	
	@Autowired
	public AppServletContextListener(@Qualifier(AppConstants.BEAN_INDEX_WRITERS) Map<String,IndexWriter> indexWriters) {
		this.indexWriters=indexWriters;
		
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		//Close Lucene Writers Gracefully
		IndexWriterUtil.closeWriters(indexWriters);
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		//As of now , nothing is needed here
	}

}
