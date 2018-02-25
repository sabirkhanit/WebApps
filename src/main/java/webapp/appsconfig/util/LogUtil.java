package webapp.appsconfig.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import webapp.appsconfig.exception.WepAppsConfigRuntimeException;

public final class LogUtil {
	
	private static final Logger logger = LoggerFactory
	        .getLogger(AppConstants.LOGGER_WEBAPPS_CONFIG);

	public static void logErrorAndRethrow(Exception ex, String message)
    {
        logger.error(ex.getMessage(), ex);
        throw new WepAppsConfigRuntimeException(message);
    }
	
	public static void logOnly(Exception ex, String message,Level level)
    {
		if(null!= ex && Level.ERROR.equals(level)) {
			logger.error(message, ex);
		}else if (null!= ex && Level.WARN.equals(level)) {
			logger.warn(message, ex);
		}else if(Level.INFO.equals(level)) {
			logger.info(message);
		}else if(Level.DEBUG.equals(level)) {
			logger.debug(message);
		}else if(Level.TRACE.equals(level)) {
			logger.trace(message);
		}else {
			logger.error(message, ex);
		}
        
    }
	
	private LogUtil() {
		
	}
}
