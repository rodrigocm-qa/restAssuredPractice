package request_parameters;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*;

import java.util.HashMap;

public class Request_Parameters { 

	//https://example.com.api/v1?paramname=paramvalue&paramname2=paramvalue2
	
  @Test
  public void handling_query_parameters() {
	  given()
	  	.baseUri("https://restcountries.com/v2")
	  	.param("fullText", true).
	  	//.queryParam("fullText", true).  //is the same that .param 
	  when()
	  	.get("/name/india").
	  then()
	  	.log().all()
	  	.statusCode(200);	
  }
  
  @Test
  public void handling_multiple_parameters() {
	  given()
	  	.baseUri("https://data.fixer.io/api")
	  	.param("access_key", "e942dccc4d2daa7835395a15118d3c8f")
	  	.param("Symbols", "USD").
	  	//.queryParam("fullText", true).  //is the same that .param 
	  when()
	  	.get("/latest").
	  then()
	  	.log().all()
	  	.statusCode(200);	
  }
  
  @Test
  public void handling_multiple_parameters_HashMap() {
	  HashMap<String,Object> params = new HashMap<String,Object>(); //instanciating hashmap object (ParameterName is always a String, ParamValue could be int, String, etc that is why we use Object)
	  params.put("access_key", "e942dccc4d2daa7835395a15118d3c8f");
	  params.put("Symbols", "USD");
	  
	  given()
	  	.baseUri("https://data.fixer.io/api")
	  	.queryParams(params). //we use query.params in plural
	  when()
	  	.get("/latest").
	  then()
	  	.log().all()
	  	.statusCode(200);	
  }
  
  @Test
  public void handling_multiple_values_for_one_parameter() {
	    
	  given()
	  	.baseUri("https://restcountries.com/v2")
	  	.queryParam("codes", "col,pe,at").   //depends how was developed the API, can be comma, semicolon, space, etc how we can segregate is in the API documentation
	  when()
	  	.get("/alpha").
	  then()
	  	.log().all()
	  	.statusCode(200);
  }
  
  @Test
  public void handling_path_parameter() {
	  //  https://restcountries.com/v2/currency/{currency}
	  //  https://restcountries.com/v2/currency/{currency}/name/{name}
	  
	  given()
	  	.baseUri("https://restcountries.com/v2")
	  	.pathParam("currency", "usd").	//using pathParam
	  	//.pathParam("name", "India").
	  when()
	  	.get("/currency/{currency}"). 	//endopint + path parameter
	  	//.get("/currency/{currency}/name/{name}").
	  then()
	  	.log().all()
	  	.statusCode(200);
  }
  
  @Test
  public void handling_form_parameter() {
	  //  https://postman-echo.com/post
	  //  multipart/form-data					
	  //  application/x-www-form-urlencoded		
	  //  application/json						
	  
	  given()
	  	.baseUri("https://postman-echo.com")
	  	.contentType("application/x-www-form-urlencoded;charset=UTF-8") //this info is provided in API documentation
	  	.formParam("First Name", "Luis")				//Also can be provided as HashMap
	  	.formParam("Last Name", "Veizaga").
	  when()
	  	.post("/post").		//also could be a "get", info provided in API documentation
	  then()
	  	.log().all()
	  	.statusCode(200);
  }
  
  
  
}
