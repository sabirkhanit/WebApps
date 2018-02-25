package webapp.appsconfig.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import webapp.appsconfig.dto.CreatePropertyDto;

import webapp.appsconfig.service.CreatePropertyService;
import webapp.appsconfig.util.AppConstants;
import webapp.appsconfig.util.AppMessages;
import webapp.appsconfig.util.ErrorMessages;
import webapp.appsconfig.util.IndexWriterUtil;
import webapp.appsconfig.util.LogUtil;

@RestController
@RequestMapping(AppConstants.SLASH_FORWARD+AppConstants.BASE_API_URL+AppConstants.SLASH_FORWARD+AppConstants.BASE_CREATE_API_URL)
public class CreatePropertyController {

	
	private final CreatePropertyService importService;
	
	@Autowired
    public CreatePropertyController(CreatePropertyService importService)
    {
        this.importService = importService;
    }
	
	@ApiOperation(value = "Create an app property into FileSystem", notes = "Create an app property into FileSystem", consumes = "application/json", produces = "application/json", response = String.class, nickname = "All")
    @ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 201, message = AppMessages.MESSAGE_SUCCESS, response = String.class)//,
        //@io.swagger.annotations.ApiResponse(code = 500, message = AppMessages.MESSAGE_CONSTRAINTVIOLATION_EXCEPTION, response = MyTaxiServerErrorResponse.class),
        //@io.swagger.annotations.ApiResponse(code = 500, message = AppMessages.MESSAGE_INTERNAL_SERVER_ERROR, response = MyTaxiServerErrorResponse.class)
        })
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> createProperty(@ApiParam @RequestBody CreatePropertyDto createProperty) {
		
		try {
			importService.createPropery(createProperty.getAppName(),createProperty.getEnvironment(),createProperty.getProperty().getPropertyName(),IndexWriterUtil.document(createProperty.getAppName(),createProperty.getEnvironment(),createProperty.getProperty()));
			
		}catch(Exception ex) {
			LogUtil.logErrorAndRethrow(ex, ErrorMessages.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>("SUCCESS",HttpStatus.OK);
	}
	
	
	
	
}
