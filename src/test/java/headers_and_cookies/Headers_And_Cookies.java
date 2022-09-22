package headers_and_cookies;

import org.testng.annotations.Test;

import io.restassured.http.Cookie;
import io.restassured.http.Headers;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

public class Headers_And_Cookies {
  @Test
  public void sending_request_headers() {
	  given()
	  	.baseUri("http://data.fixer.io/api")
	  	.queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
	  	.queryParam("Symbols", "USD")
	  	.header("If-None-Match", "1872ade88f3013edeb33decd74a4f947") //name of header and value of header
	  	//.header("If-None-Match", "1872ade88f3013edeb33decd74a4f947","as54aqrf56","5489sa") //if header has multiple values just add them with a comma
	  	.header("If-Modified-Since", "Fri, 10 Apr 2015 12:10:04 GMT").
	  when()
	  	.get("/latest").
	  then()
	  	.log().all()
	  	.statusCode(200);  
  }
  
  @Test
  public void sending_request_headers_withObjects() { //when we have multiple headers
	  HashMap<String,Object> headers = new HashMap<String,Object>(); 		//if headers are used in multiple request we can create the HashMap at class level instead method level
	  headers.put("If-None-Match", "1872ade88f3013edeb33decd74a4f947");
	  headers.put("If-Modified-Since", "Fri, 10 Apr 2015 12:10:04 GMT");
	  headers.put("Accept-Encoding", "gzip, deflate, br");
	  headers.put("Connection", "keep-alive");
	  //headers.put(Key, value);
	  
	  given()
	  	.baseUri("http://data.fixer.io/api")
	  	.queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
	  	.queryParam("Symbols", "USD")
	  	.headers(headers).	//using .headers plural
	  when()
	  	.get("/latest").
	  then()
	  	.log().all()
	  	.statusCode(200);  
  }
  
  @Test
  public void sending_request_cookies() {
	  given()
	  	.baseUri("http://data.fixer.io/api")
	  	.queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
	  	.queryParam("Symbols", "USD")
	  	//.cookie(name,value).
	    .cookie("user","val1","val2","val3").
	  when()
	  	.get("/latest").
	  then()
	  	.log().all()
	  	.statusCode(200);  
  }
  
  @Test
  public void sending_cookies_using_builder() {
	  Cookie cookie = new Cookie.Builder("usertype","int").setSecured(true).setComment("test cookie").build();
	  
	  given()
	  	.baseUri("http://data.fixer.io/api")
	  	.queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
	  	.queryParam("Symbols", "USD")
	    .cookie(cookie).
	  when()
	  	.get("/latest").
	  then()
	  	.log().all()
	  	.statusCode(200);  
  }
  
  @Test
  public void validate_response_header() {
	  given()
	  	.baseUri("http://data.fixer.io/api")
	  	.queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
	  	.queryParam("Symbols", "USD").
	  when()
	  	.get("/latest").
	  then()
	  	.log().all()
	  	.statusCode(200)
	  	.header("access-control-allow-methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");  //checking header exists and has respective values
  }
  
  @Test
  public void extracting_all_response_headers() {
	  Headers headers = given()					//We can extract all headers from the response and keep them at the "headers" object
	  	.baseUri("http://data.fixer.io/api")
	  	.queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
	  	.queryParam("Symbols", "USD").
	  when()
	  	.get("/latest").
	  then()
	  	.statusCode(200)
	  	.extract().headers();
	  
	  System.out.println(headers.getValue("transfer-encoding")); //we will get value of respective header
	  System.out.println(headers.getValue("etag"));
  }
  
  @Test
  public void extracting_all_response_cookies() {
	  Map<String,String> cookies = given()			//We can extract all cookies from the response and keep them in a Map
	  	.baseUri("http://data.fixer.io/api")
	  	.queryParam("access_key", "b406c5d0bd55d77d592af69a930f4feb")
	  	.queryParam("Symbols", "USD").
	  when()
	  	.get("/latest").
	  then()
	  	.statusCode(200)
	  	.extract().cookies();
	  
	  System.out.println(cookies.get("__cfduid")); //we will get value of respective cookie
  }
  
  
  
  
}
