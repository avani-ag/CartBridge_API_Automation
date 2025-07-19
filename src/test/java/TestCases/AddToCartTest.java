package TestCases;

import core.Base;
import core.StatusCodes;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class AddToCartTest extends Base {


    @Test(description = "Verifying APIs Add to Cart Flow : Login > Search Product > View Product > Update Cart")
    public void testAddToCartFlow() {
        System.out.println("Login and Fetch Token");

        // Step 1: LOGIN and fetch TOKEN manually
        String loginEndPoint = (String) ConfigReader.GetProperty("loginEndpoint");

        Response loginResponse = given()
                .headers(HeaderManager.BasicHeader())
                .body(RequestBuilder.validlogincreds())
                .log().all() //Log full request
                .when()
                .post(loginEndPoint)
                .then()
                .log().all() // Log full response
                .statusCode(StatusCodes.SUCCESS.getStatusCode())
                .extract().response();

        String tokenKey = (String) JSONReader.GetJsonValue("tokenKey", testDataFilePath);
        String token = loginResponse.jsonPath().getString(tokenKey);
        assertObj.assertTrue(token != null && !token.isEmpty(), "Token is missing from login response");


        System.out.println("Adding token to headers");

        // Step 2: Prepare authorized headers using the token
        Map<String, String> authorizedHeaders = new HashMap<>();
        authorizedHeaders.put("Content-Type", "application/json");
        authorizedHeaders.put("Authorization", "Bearer " + token);


        System.out.println("Searching Product - Apple and fetch product ID");

        // Step 3: SEARCH product
        String searchEndPoint = (String) JSONReader.GetJsonValue("searchProduct", testDataFilePath);
        String searchTitle = (String) JSONReader.GetJsonValue("searchTitleAdd", testDataFilePath);

        Response response_SearchProduct = given().headers(authorizedHeaders)
                .queryParam("q", searchTitle)
                .log().all() //
                .when()
                .get(searchEndPoint)
                .then()
                .log().all() //
                .statusCode(StatusCodes.SUCCESS.getStatusCode())
                .extract().response();

        int searchedProductID = response_SearchProduct.jsonPath().getInt("products[0].id");

        System.out.println("SEARCHED PRODUCT ID :"+searchedProductID);

        System.out.println("View Same Product");

        // Step 4: VIEW product
        String viewProductEndPoint = (String) JSONReader.GetJsonValue("viewProductByID", testDataFilePath);

        Response response_ViewProduct = given().headers(authorizedHeaders)
                .pathParam("productID", searchedProductID)
                .log().all()
                .when()
                .get(viewProductEndPoint)
                .then()
                .log().all()
                .statusCode(StatusCodes.SUCCESS.getStatusCode())
                .extract().response();

        int productIDFromView = response_ViewProduct.jsonPath().getInt("id");
        assertObj.assertEquals(productIDFromView, searchedProductID, "Product ID mismatch between search & view");




        // Step 5: UPDATE cart
        String updateCartEndpoint = (String) JSONReader.GetJsonValue("updateCart", testDataFilePath);
        int cartID = ((Long) JSONReader.GetJsonValue("cartID", testDataFilePath)).intValue();
        System.out.println("UPDATE CART REQUEST");
        Response updatedCartResponse = given().headers(authorizedHeaders)
                .pathParam("cartID", cartID)
                .body(RequestBuilder.updateCart(searchedProductID, 1))
                .log().all()
                .when()
                .put(updateCartEndpoint)
                .then()
                .log().all()
                .statusCode(StatusCodes.SUCCESS.getStatusCode())
                .extract().response();


        // Step 6: Assertions
        String responseBody = updatedCartResponse.getBody().asString();
        assertObj.assertTrue(!responseBody.isEmpty(), "Cart response body is empty");

        List<Map<String, Object>> products = updatedCartResponse.jsonPath().getList("products");
        assertObj.assertTrue(products != null && !products.isEmpty(), "Product list is empty in cart");

        int productIdInCart = (int) products.get(0).get("id");
        assertObj.assertEquals(productIdInCart, searchedProductID, "Cart product ID mismatch");


        System.out.println("Product successfully added to cart: " + products);

    }


}
