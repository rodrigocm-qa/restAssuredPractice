package rest_logging;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*;

public class Rest_Logging {
  @Test
  public void log_all_details() {
	  given()
	  	.baseUri("https://restcountries.com/v2").
	  when()
	  	.get("/alpha/US").
	  then()
	  	.log().all();		//all the response is logged in the console (Headers, Cookies, etc, etc)
	  	//.log().everything(); //is the same as all();
	  
  }
  
  @Test
  public void log_body_or_header() {
	  given()
	  	.baseUri("https://restcountries.com/v2").
	  when()
	  	.get("/alpha/US").
	  then()
	  	//.log().headers();		//body and header the response is logged in the console
	  	.log().body();
  }
  
  @Test
  public void log_cookies_or_status() {
	  given()
	  	.baseUri("https://restcountries.com/v2").
	  when()
	  	.get("/alpha/US").
	  then()
	  	//.log().cookies();		//cookies or status the response is logged in the console
	  	.log().status();
  }
  
  @Test
  public void log_if_error() {
	  given()
	  	.baseUri("https://restcountries.com/v2").
	  when()
	  	.get("/alpha/UT").
	  then()
	  	//.log().ifError();		//only displays info in the console if there is an error 4xx - 5xx (client - Server errors)
	  	.log().ifStatusCodeIsEqualTo(404); //only displays info in the console if there is an error equal to the specified
  }
  
  @Test
  public void log_if_validation_fails() {
	  given()
	  	.baseUri("https://restcountries.com/v2").
	  when()
	  	.get("/alpha/UT").
	  then()
	  	.log().ifValidationFails() //this is a method that will be called first and then what needs to be validated
	  	.statusCode(200);		//only displays info in the console if validation fails, if is not 200 will display in the console is like an assertion
	  							//can be any validation, in the body, in the header, etc
  }
  
}
