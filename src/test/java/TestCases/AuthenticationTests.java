package TestCases;
import io.restassured.response.Response;
import org.testng.annotations.*;
import utils.*;
import core.*;
import static io.restassured.RestAssured.*;

public class AuthenticationTests extends Base {

    String loginEndPoint = (String) ConfigReader.GetProperty("loginEndpoint");


    @Test(description = "Verify Login API with valid credentials")
    public void validLogin()
    {
        Response response = given()
                .headers(HeaderManager.BasicHeader())
                .body(RequestBuilder.validlogincreds())
                           .when()
                           .post(loginEndPoint)
                           .then()
                           .statusCode(StatusCodes.SUCCESS.getStatusCode())
                           .extract().response();

        String actualResponse = response.body().asString();

        String tokenKey = (String) JSONReader.GetJsonValue("tokenKey", testDataFilePath);
        String token = response.jsonPath().getString(tokenKey);

        assertObj.assertTrue(!actualResponse.isEmpty(),"Response body is empty");
        assertObj.assertTrue(token != null && !token.isEmpty(), "Token is null or empty with valid credentials");

    }

    @Test(description = "Verify Login API with invalid credentials")
    public void invalidLogin()
    {
        Response response = given()
                .headers(HeaderManager.BasicHeader())
                .body(RequestBuilder.invalidLogincreds())
                .when()
                .post(loginEndPoint)
                .then()
                .statusCode(StatusCodes.BAD_REQUEST.getStatusCode())
                .extract()
                .response();

        String actualResponse = response.body().asString();

        String messageKey=(String) JSONReader.GetJsonValue("messageKey",testDataFilePath);

        assertObj.assertTrue(actualResponse
                .contains(messageKey),"Response does not contains message");


        assertObj.assertEquals(response.jsonPath().get(messageKey),
                (String)JSONReader.GetJsonValue("invalidMessageValue",testDataFilePath),
                "Expected message not received");

    }


    @Test(description = "Verify Login API with missing credentials")
    public void missingCredentials()
    {
        Response response = given()
                .headers(HeaderManager.BasicHeader())
                .when()
                .post(loginEndPoint)
                .then()
                .statusCode(StatusCodes.BAD_REQUEST.getStatusCode())
                .extract()
                .response();

        String actualResponse = response.body().asString();

        String messageKey=(String) JSONReader.GetJsonValue("messageKey",testDataFilePath);

        assertObj.assertTrue(actualResponse
                .contains(messageKey),"Response does not contains message");


        assertObj.assertEquals(response.jsonPath().get(messageKey),
                (String)JSONReader.GetJsonValue("requiredMessageValue",testDataFilePath),
                "Expected message not received");

    }



}
