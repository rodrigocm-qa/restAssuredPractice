package getting_started;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*; //Important to import this static for test cases BDD approach
import static org.hamcrest.Matchers.*; //To validate responses / Assertions

public class Getting_Started_TestNGClass {
  @Test
  public void simple_get_request() {
	  //We follow BDD approach
	  //Given                  Base URI                When      ENDPOINT  Then   Result=Ok 
	  //given().baseUri("https://restcountries.com/v3.1").when().get("/all").then().statusCode(200);
	  given()
	  	.baseUri("https://restcountries.com/v2").
	  when()
	  	.get("/all").
	  then()
	  	.statusCode(200);
  }
  
  @Test
  public void validate_json_response() {
	  given()
	  	.baseUri("https://restcountries.com/v2").
	  when()
	  	.get("/alpha/USA").
	  then()
	  	.statusCode(200)
	  	.body(
	  			"name", equalTo("United States of America"),
	  			"alpha3Code", equalTo("USA"),
	  			"altSpellings", hasItem("US"), //validate an array, if the array contains US will pass
	  			"currencies[0].symbol", equalTo("$"), //validating first item from array
	  			"languages[0].name",equalTo("English")
	  		 );
  }
  
  @Test
  public void validate_xml_response() { //Using also Query Params
	  given()
	  	.baseUri("https://api.openweathermap.org/data/2.5")
	  	.queryParam("q","London,uk")  // Query params -> Key, Values
	  	.queryParam("APPID","3f7393c569d314987f3b072d260d7142")
	  	.queryParam("mode","xml").
	  when()
	  	.get("/weather").
	  then()
	  	.statusCode(200)
	  	.body(	   
	  			"current.city.@name", equalTo("London"), //nodes.@attribute
	  			"current.city.country", equalTo("GB")   //only nodes
	  		 );
  }
  
  @Test
  public void extract_response_data() {
	  Response res = given()
	  	.baseUri("https://restcountries.com/v2").
	  when()
	  	.get("/alpha/USA").
	  then()
	  	.extract().response();
	  
	  System.out.println("API Response is: \n" + res.asString());
  }
  
  @Test
  public void extract_response_single_value() {  //Also Validating 
	  String temperature = given()
	  	.baseUri("https://api.openweathermap.org/data/2.5")
	  	.queryParam("q","London,uk") 
	  	.queryParam("APPID","3f7393c569d314987f3b072d260d7142")
	  	.queryParam("mode","xml").
	  when()
	  	.get("/weather").
	  then()
	  	.statusCode(200)
	  	.body(	   
	  			"current.city.@name", equalTo("London"),
	  			"current.city.country", equalTo("GB")
	  		 )
	  	.extract().path("current.temperature.@value");
	  
	  System.out.println("Temperature value is: \n" + temperature);
  }
  
  @Test
  public void verify_status_line() { //
	  given()
	  	.baseUri("https://api.printful.com").
	  when()
	  	.get("/variant/1").
	  then()
	  	.statusCode(404)
	  	.statusLine("HTTP/1.1 404 Not Found"); // HTTP/1.1 -> Protocol that API is going to use, we need to confirm with the developer APIStatus line
  
  }
  
  
}

