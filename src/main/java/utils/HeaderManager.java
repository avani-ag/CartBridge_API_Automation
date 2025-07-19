package utils;
import core.StatusCodes;
import java.util.HashMap;
import java.util.Map;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;


public class HeaderManager {

    public static Map<String,String> BasicHeader()
    {
        Map<String,String> BasicHeader = new HashMap<>();
        BasicHeader.put("Content-Type", "application/json");
        return BasicHeader;
    }

    public static Map<String,String> AuthorizedHeader()
    {
        //Login function
        String loginEndPoint = (String) ConfigReader.GetProperty("loginEndpoint");

        RestAssured.baseURI= (String) ConfigReader.GetProperty("baseURL");
        Response response = given()
                .headers(HeaderManager.BasicHeader())
                .body(RequestBuilder.validlogincreds())
                .when()
                .post(loginEndPoint)
                .then()
                .statusCode(StatusCodes.SUCCESS.getStatusCode())
                .extract().response();

        String actualResponse = response.body().asString();

        System.out.println(actualResponse);

        //Fetch token from login response

        String tokenKey = (String) JSONReader.GetJsonValue("tokenKey", "resources/testdata.json");
        String token = response.jsonPath().getString(tokenKey);

        Map<String,String> AuthorizedHeader = new HashMap<>();
        AuthorizedHeader.put("Content-Type", "application/json");
        AuthorizedHeader.put("Authorization","Bearer" + token);
        return AuthorizedHeader;
    }
}
