package webapp.appsconfig.service.impl;

import java.io.IOException;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import webapp.appsconfig.service.CreatePropertyService;
import webapp.appsconfig.util.AppConstants;
import webapp.appsconfig.util.AppEnvironment;
import webapp.appsconfig.util.DocumentFields;
import webapp.appsconfig.util.IndexWriterUtil;

@Service
public class DefaultCreatePropertyServiceImpl implements CreatePropertyService{

	private final Map<String,IndexWriter> indexWriters ;
	
	
	@Autowired
	public DefaultCreatePropertyServiceImpl(@Qualifier(AppConstants.BEAN_INDEX_WRITERS) Map<String,IndexWriter> indexWriters) {
		this.indexWriters=indexWriters;
		
	}
	
	@Override
	public void createPropery(String appName,AppEnvironment environment,String propertyName,Document document) throws IOException {
		
		IndexWriter indexWriter = indexWriters.get(appName+environment);
		
		if(null != indexWriter) {
			indexDoc(indexWriter,appName,environment,propertyName,document);
		}else {
			
			indexWriter = IndexWriterUtil.createIndexWriter(appName,environment);
			indexWriters.put(appName+environment, indexWriter);
			
			indexDoc(indexWriter,appName,environment,propertyName,document);
		}
		
		
	}
	
	private void indexDoc(IndexWriter indexWriter,String appName, AppEnvironment environment,String propertyName,Document document) throws IOException{
		
		indexWriter.updateDocument(new Term(DocumentFields.FIELD_DOC_ID,appName+environment+propertyName), document);
		
		indexWriter.commit();
		indexWriter.flush();
		
	}

}
