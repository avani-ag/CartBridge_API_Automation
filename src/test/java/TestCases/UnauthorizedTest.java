package TestCases;

import core.Base;
import core.StatusCodes;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.ConfigReader;
import utils.HeaderManager;
import utils.JSONReader;
import utils.RequestBuilder;

import static io.restassured.RestAssured.given;

public class UnauthorizedTest extends Base {
    @Test(description = "Expected to be failed")
    public void invalidLogin()
    {
        String loginEndPoint = (String) ConfigReader.GetProperty("loginEndpoint");


        Response response = given()
                .headers(HeaderManager.BasicHeader())
                .body(RequestBuilder.invalidLogincreds())
                .when()
                .post(loginEndPoint)
                .then()
                .statusCode(StatusCodes.SUCCESS.getStatusCode())
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

}
