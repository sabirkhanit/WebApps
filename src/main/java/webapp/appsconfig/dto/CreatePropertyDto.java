package webapp.appsconfig.dto;

import lombok.Data;
import webapp.appsconfig.util.AppEnvironment;

@Data
public class CreatePropertyDto {

	private String appName;
	private AppEnvironment environment;
	private PropertyDto property;
}
