package webapp.appsconfig.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import ch.qos.logback.classic.Level;
import webapp.appsconfig.dto.PropertyDto;

public final class IndexWriterUtil {

	private static String baseLoc = AppConstants.NETWROK_DRIVE+File.separator+AppConstants.INDEX_LOCATION_CONFIGS;
	
	private static FieldType stringFieldType = new FieldType(StringField.TYPE_STORED);
	//private static FieldType textFieldType = new FieldType(TextField.TYPE_STORED);
	
	public static Map<String,IndexWriter> getExistingWriters() throws IOException{
		
		Map<String,IndexWriter> map = new HashMap<>();
		
		for(File directory: directories()) {
			map.put(directory.getName(), indexWriter(directory));
		}
		
		return map;
		
	}
	
	public static IndexWriter createIndexWriter(String indexDir,AppEnvironment environment) throws IOException {
		
		File file = new File(baseLoc+File.separator+indexDir+File.separator+(indexDir+environment));
		
		if(!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		
		IndexWriter indexWriter = new IndexWriter(FSDirectory.open(file
	              .toPath()), indexWriterConfig());
		indexWriter.commit();
		return indexWriter;
		
		
	}
	
	public static void closeWriters(Map<String,IndexWriter> writers) {
		
		LogUtil.logOnly(null, "Closing all Lucene Writers at App Shutdown", Level.INFO);
		
		writers.forEach((key,value) -> {
			
				try {
					value.commit();
					value.close();
				} catch (IOException ex) {
					LogUtil.logOnly(ex, ex.getMessage(), Level.ERROR);
				}
			
		});
		
	}
	
	public static Document document(String appName,AppEnvironment environment,PropertyDto property) {
		
		Document document = new Document();
		
		document.add(new Field (DocumentFields.FIELD_DOC_ID,appName+environment+property.getPropertyName(),stringFieldType));
		document.add(new Field (DocumentFields.FIELD_APP_NAME,appName,stringFieldType));
		document.add(new Field (DocumentFields.FIELD_ENVIRONMENT_NAME,environment.name(),stringFieldType));
		document.add(new Field (DocumentFields.FIELD_PROPERTY_NAME,property.getPropertyName(),stringFieldType));
		document.add(new Field (DocumentFields.FIELD_PROPERTY_VALUE,property.getPropertyValue(),stringFieldType));
		
		return document;
	}
	
	
	//java.lang.IllegalStateException : do not share IndexWriterConfig instances across IndexWriters if indexWriterConfig instances are shared among writers
	private static IndexWriterConfig indexWriterConfig() {
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(simpleAnalyzer());
	    return indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
	}
	
	
	private  static SimpleAnalyzer simpleAnalyzer() {
		return new SimpleAnalyzer();
	}
	
	
	private static IndexWriter indexWriter(File indexDir) throws IOException {
		
		Directory directory = FSDirectory.open(indexDir.toPath());
		IndexWriter indexWriter =  new IndexWriter(directory, indexWriterConfig());
		indexWriter.commit();
		return indexWriter;
	}
	
	private static List<File> directories() {
		
		List<File> allDirs = new ArrayList<>();
		
		//this will not give sub directories
		File[] appDirectories =  new File(baseLoc).listFiles(File::isDirectory);
		
		for(File appDirectory: appDirectories) {
			File[] environmentDirs  =  appDirectory.listFiles(File::isDirectory);
			allDirs.addAll(Arrays.asList(environmentDirs));
		}
		
		return allDirs;
	}
	
	
	
	private IndexWriterUtil() {
		
	}
}
