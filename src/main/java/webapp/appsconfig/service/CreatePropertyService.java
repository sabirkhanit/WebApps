package webapp.appsconfig.service;

import java.io.IOException;

import org.apache.lucene.document.Document;

import webapp.appsconfig.util.AppEnvironment;

public interface CreatePropertyService {

	public void createPropery(String appName,AppEnvironment environment,String propertyName,Document document) throws IOException;
}
