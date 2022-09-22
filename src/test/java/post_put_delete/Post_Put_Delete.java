package post_put_delete;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*; 
import static org.hamcrest.Matchers.*;

import java.io.File;

public class Post_Put_Delete {
  @Test
  public void post_request_usingFile() {
	  File file = new File("API_Resources/create_employee.json"); //could be json or xml or the one that is used
	  
	  int id = given()
	  	.baseUri("https://dummy.restapiexample.com/api/v1")
	  	.contentType(ContentType.JSON)					//could be json or xml or the one that is used
	  	.body(file).
	  when()
	  .post("/create").   //endpoint
	  then()
	  	.statusCode(200)						//Verifying status code
	  	.body("data.name", equalTo("Maria"))	//Verifying that body response has same name we sent in the post method
	  	.extract().path("data.id");				//Verifiyng that an ID was created for that employee
	  
	  System.out.println("The new employee Id is: \n" + id);
  }
  
  @Test
  public void post_request_usingJSONObject() {
	  
	  JSONObject body = new JSONObject(); //Instanciating
	  body.put("name","Edgar"); //key, value
	  body.put("salary","1478");
	  body.put("age","17");
	  
	  int id = given()
	  	.baseUri("https://dummy.restapiexample.com/api/v1")
	  	.contentType(ContentType.JSON)					
	  	.body(body.toString()).
	  when()
	  .post("/create").
	  then()
	  	.statusCode(200)						//Verifying status code
	  	.body("data.name", equalTo("Edgar"))	//Verifying that body response has same name we sent in the post method
	  	.extract().path("data.id");				//Verifying that an ID was created for that employee
	  
	  System.out.println("The new employee id with JSON Object is: \n" + id);
  }
  
  @Test
  public void put_request_usingJSONObject() {
	  
	  JSONObject body = new JSONObject(); 
	  body.put("name","Fernando"); 
	  body.put("salary","1500");
	  body.put("age","20");
	  
	  String name = given()
	  	.baseUri("https://dummy.restapiexample.com/api/v1")
	  	.contentType(ContentType.JSON)					
	  	.body(body.toString()).
	  when()
	  .put("/update/24").   //endpoint
	  then()
	  	.statusCode(200)						//Verifying status code
	  	.body("data.name", equalTo("Fernando"))	//Verifying that body response has same name we sent in the put method
	  	.extract().path("data.name");				//Verifying that name was updated for that employee
	  
	  System.out.println("The new name is: \n" + name);
  }
  
  @Test
  public void delete_request() {
	  
	  String msg = given()
	  	.baseUri("https://dummy.restapiexample.com/api/v1").				
	  when()
	  	.delete("/delete/24").   //endpoint
	  then()
	  	.statusCode(200)						//Verifying status code
	  	.extract().path("message");			//Verifying message
	  
	  System.out.println("The delete msg is: \n" + msg);
  }
  
  
}
