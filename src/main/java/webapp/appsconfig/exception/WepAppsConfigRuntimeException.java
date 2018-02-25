package webapp.appsconfig.exception;

public class WepAppsConfigRuntimeException extends RuntimeException{

	
	private static final long serialVersionUID = 1L;
	
	public WepAppsConfigRuntimeException(String message) {
		super(message);
	}
	
	public WepAppsConfigRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
