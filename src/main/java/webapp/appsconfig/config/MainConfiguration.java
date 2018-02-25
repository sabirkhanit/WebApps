package webapp.appsconfig.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import webapp.appsconfig.util.AppConstants;
import webapp.appsconfig.util.IndexWriterUtil;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContextListener;

import org.apache.lucene.index.IndexWriter;


@Configuration
public class MainConfiguration {

	
	@Autowired
	private AppServletContextListener appServletContextListener;
	
	@Bean(name=AppConstants.BEAN_INDEX_WRITERS)
	public Map<String,IndexWriter> indexWriters() throws IOException{
		return IndexWriterUtil.getExistingWriters();
	}
	
	
    @Bean
    public ServletListenerRegistrationBean<ServletContextListener> myServletListener() {
        ServletListenerRegistrationBean<ServletContextListener> srb =
                new ServletListenerRegistrationBean<>();
        srb.setListener(appServletContextListener);
        return srb;
    }
}
