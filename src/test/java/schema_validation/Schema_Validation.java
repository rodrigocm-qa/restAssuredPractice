package schema_validation;

import java.io.File;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*; //importing libraries to validate json schema
import static io.restassured.matcher.RestAssuredMatchers.*; //importing library to validate xml schema, it is not necessary a dependency in POM


public class Schema_Validation {
  
	@Test
  public void json_schema_validation() {
	File file = new File("API_Resources/json_schema_validation.json"); //File that contains the valid schema	
		
	  given()
	  	.baseUri("http://data.fixer.io/api")
	  	.queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
	  	.queryParam("Symbols", "USD").
	  when()
	  	.get("/latest").
	  then()
	  	.log().all()
	  	.statusCode(200)
	  	.body(matchesJsonSchema(file)); //validating schema
  }
	
	
	@Test
	  public void xml_dtd_schema_validation() {
		File file = new File("API_Resources/xml_schema_validation.dtd"); //File that contains the valid schema	
			
		given()
	  	.baseUri("https://api.openweathermap.org/data/2.5")
	  	.queryParam("q","London,uk") 
	  	.queryParam("APPID","3f7393c569d314987f3b072d260d7142")
	  	.queryParam("mode","xml").
	  when()
	  	.get("/weather").
	  then()
	  	.body(matchesDtd(file))
	  	.statusCode(200)
	  	.log().all();
	  }
	
	@Test
	  public void xml_xsd_schema_validation() {
		File file = new File("API_Resources/xml_schema_validation.xsd"); //File that contains the valid schema	
			
		given()
	  	.baseUri("https://api.openweathermap.org/data/2.5")
	  	.queryParam("q","London,uk") 
	  	.queryParam("APPID","3f7393c569d314987f3b072d260d7142")
	  	.queryParam("mode","xml").
	  when()
	  	.get("/weather").
	  then()
	  	.body(matchesXsd(file))
	  	.statusCode(200)
	  	.log().all();
	  }	

}
