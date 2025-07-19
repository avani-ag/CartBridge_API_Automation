package TestCases;

import core.Base;
import core.StatusCodes;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.*;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CartManagementTests extends Base {


    @Test(description = "Verify View Cart API by CartID")
    public void viewCart()
    {
        String endPoint = (String) JSONReader.GetJsonValue("viewCart",testDataFilePath);

        Long cartIDLong = (Long) JSONReader.GetJsonValue("cartID", testDataFilePath);
        int cartID = cartIDLong.intValue();
        Response response = given()
                          .headers(HeaderManager.AuthorizedHeader())
                          .pathParam("cartID",cartID)
                          .when()
                          .get(endPoint)
                          .then()
                          .statusCode(StatusCodes.SUCCESS.getStatusCode())
                          .extract().response();

        assertObj.assertTrue(!response.body().asString().isEmpty(),"Response Body is empty");
        assertObj.assertEquals(response.jsonPath().get("id"),cartID,"Cart ID is not expected");

    }

    @Test(description = "Verify Add Cart API")
    public void addCart()
    {
        String endPoint = (String) JSONReader.GetJsonValue("addCart",testDataFilePath);


        Response response = given()
                .headers(HeaderManager.AuthorizedHeader())
                .body(RequestBuilder.addCart())
                .log().all()
                .when()
                .post(endPoint)
                .then()
                .statusCode(StatusCodes.CREATED.getStatusCode())
                .log().all()
                .extract().response();

        assertObj.assertTrue(!response.body().asString().isEmpty(),"Response Body is empty");

        int userId = response.jsonPath().getInt("userId");
        assertObj.assertEquals(userId, 1, "User ID mismatch");

        List<Map<String, Object>> products = response.jsonPath().getList("products");
        assertObj.assertTrue(products != null && !products.isEmpty(), "Product list is empty");


    }


    @Test(description = "Verify Update Cart by adding new product API using PUT")
    public void updateCart() {
        String endPoint = (String) JSONReader.GetJsonValue("updateCart",testDataFilePath);


        Long cartIDLong = (Long) JSONReader.GetJsonValue("cartID", testDataFilePath);
        int cartID = cartIDLong.intValue();

        Response response = given()
                .headers(HeaderManager.AuthorizedHeader())
                .pathParam("cartID",cartID)
                .body(RequestBuilder.updateCart(1,3))  // sending POJO body
                .when()
                .put(endPoint)
                .then()
                .statusCode(StatusCodes.SUCCESS.getStatusCode()) // usually 200
                .extract().response();

        String responseBody = response.getBody().asString();
        assertObj.assertTrue(!responseBody.isEmpty(), "Response Body is empty");

        // Basic Assertions
        int userId = response.jsonPath().getInt("userId");
        List<Map<String, Object>> products = response.jsonPath().getList("products");

        assertObj.assertEquals(userId, 1, "User ID mismatch");
        assertObj.assertTrue(products != null && !products.isEmpty(), "Product list is empty");
    }


    @Test(description = "Verify Delete Cart API by CartID")
    public void deleteCart()
    {
        String endPoint = (String) JSONReader.GetJsonValue("deleteCart", testDataFilePath);

        Long cartIDLong = (Long) JSONReader.GetJsonValue("cartID", testDataFilePath);
        int cartID = cartIDLong.intValue();

        Response response = given()
                .headers(HeaderManager.AuthorizedHeader())
                .pathParam("cartID", cartID)
                .when()
                .delete(endPoint)
                .then()
                .statusCode(StatusCodes.SUCCESS.getStatusCode())
                .extract().response();

        String responseBody = response.body().asString();

        assertObj.assertTrue(!responseBody.isEmpty(), "Response body is empty");

        assertObj.assertEquals(response.jsonPath().getInt("id"), cartID, "Cart ID does not match");

        assertObj.assertTrue(response.jsonPath().getBoolean("isDeleted"), "`isDeleted` should be true");

        String deletedOn = response.jsonPath().getString("deletedOn");
        assertObj.assertTrue(deletedOn != null && !deletedOn.isEmpty(), "`deletedOn` is missing");

    }





}
