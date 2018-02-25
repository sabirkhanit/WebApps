package webapp.appsconfig.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Lists;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import webapp.appsconfig.util.AppConstants;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
    public Docket docket()
    {
        return new Docket(DocumentationType.SWAGGER_2).select()
            .apis(RequestHandlerSelectors.basePackage(AppConstants.JAVA_PACKAGE_CONTROLLER)).paths(PathSelectors.any())
            .build().apiInfo(generateApiInfo()).securitySchemes(Lists.newArrayList(apiKey()));
    }


    //In swagger UI - After clicking Authorize button, you need to enter Bearer {access_token} to create correct CURL request
    //There seems a bug in swagger UI that scope separation specified in below constructor is not working - last parameter
    @Bean
    public SecurityConfiguration securityInfo()
    {
        return new SecurityConfiguration(null, null, null, null, "", ApiKeyVehicle.HEADER, "Authorization", ": Bearer");
    }


    private ApiKey apiKey()
    {
        return new ApiKey("Authorization", "Authorization", "header");
    }


    private ApiInfo generateApiInfo()
    {
        Contact contact = new Contact("sabir.amu@gmail.com", "", "sabir.amu@gmail.com");

        return new ApiInfoBuilder()
            .title("Externalized App Configuration Service").description("This service is to provide a wep app's configuration mainly property file").contact(contact)
            .license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").build();
    }

	
}
