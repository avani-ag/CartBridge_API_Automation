package TestCases;

import core.Base;
import core.StatusCodes;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.*;

import static io.restassured.RestAssured.given;

public class ViewProductsTests extends Base {

    @Test(description = "Verify View All Products API")
    public void viewAllProducts()
    {
        String endPoint = (String) JSONReader.GetJsonValue("viewAllProducts", testDataFilePath);

        Response response = given()
                .headers(HeaderManager.AuthorizedHeader())
                .when()
                .get(endPoint)
                .then()
                .statusCode(StatusCodes.SUCCESS.getStatusCode())
                .extract().response();

        String responseBody = response.body().asString();

        assertObj.assertTrue(!responseBody.isEmpty(), "Response body is empty");
        assertObj.assertTrue(response.jsonPath().get("products")!=null&&!responseBody.isEmpty(),"Products is empty or null") ;
        assertObj.assertTrue(response.jsonPath().getList("products").size()>0,"Products List is empty");
    }


    @Test(description = "Verify View Single Product by ID")
    public void viewSingleProduct()
    {
        String endPoint = (String) JSONReader.GetJsonValue("viewProductByID", testDataFilePath);
        Long productIDLong = (Long) JSONReader.GetJsonValue("productID", testDataFilePath);
        int productID = productIDLong.intValue();


        Response response = given()
                .headers(HeaderManager.AuthorizedHeader())
                .pathParam("productID", productID)
                .when()
                .get(endPoint)
                .then()
                .statusCode(StatusCodes.SUCCESS.getStatusCode())
                .extract().response();

        String responseBody = response.body().asString();

        assertObj.assertTrue(!responseBody.isEmpty(), "Response body is empty");
        assertObj.assertEquals(response.jsonPath().getInt("id"), productID, "Product ID does not match");

    }

    @Test(description = "Verify Search Product by Title using Query Param")
    public void searchProductByTitle()
    {
        String endPoint = (String) JSONReader.GetJsonValue("searchProduct", testDataFilePath);
        String searchTitle = (String) JSONReader.GetJsonValue("productTitle", testDataFilePath);


        Response response = given()
                .headers(HeaderManager.AuthorizedHeader())
                .queryParam("q", searchTitle)
                .when()
                .get(endPoint)
                .then()
                .statusCode(StatusCodes.SUCCESS.getStatusCode())
                .extract().response();

        String responseBody = response.body().asString();

        assertObj.assertTrue(!responseBody.isEmpty(), "Search response body is empty");
        assertObj.assertEquals(response.jsonPath().getList("products").size(),1,"Size of Products List is mismatched ");
        assertObj.assertEquals(response.jsonPath().getString("products[0].title"),searchTitle,"Search Title and Response Title mismatched");
    }


}
