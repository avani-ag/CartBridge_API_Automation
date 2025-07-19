package utils;
import RequestsPOJO.*;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import com.google.gson.*;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RequestBuilder {

    public static String validlogincreds()
    {
        LoginCredsPOJO validlogin = new LoginCredsPOJO();
        validlogin.setUsername("emilys");
        validlogin.setPassword("emilyspass");
        String loginrequest= new Gson().toJson(validlogin);
        return loginrequest;
    }

    public static String invalidLogincreds() {

        LoginCredsPOJO invalidLogin = new LoginCredsPOJO();
        invalidLogin.setUsername("invaliduser");
        invalidLogin.setPassword("invalidpass");

        String loginrequest = new Gson().toJson(invalidLogin);
        return loginrequest;
    }

    public static String addCart()
    {
        AddCartRequestPOJO addcart = new AddCartRequestPOJO();
        addcart.setUserId(1);

        ProductsPOJO product1 = new ProductsPOJO();
        product1.setQuantity(144);
        product1.setQuantity(1);

        ProductsPOJO product2 = new ProductsPOJO();
        product2.setId(98);
        product2.setQuantity(4);

        List<ProductsPOJO> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        addcart.setProducts(products);

        String request = new Gson().toJson(addcart);
        return request;

    }

    public static String updateCart(int pId,int quantity) {


        AddCartRequestPOJO updateCartObj = new AddCartRequestPOJO();

        // Product 1
        ProductsPOJO productNew = new ProductsPOJO();
        productNew.setId(pId);
        productNew.setQuantity(quantity);

        // List of products
        List<ProductsPOJO> productList = new ArrayList<>();
        productList.add(productNew);

        // Set values in main request
        updateCartObj.setUserId(1); //Updated for PUT Request
        updateCartObj.setProducts(productList);

        String updateCartRequest= new Gson().toJson(updateCartObj);

        return updateCartRequest;

    }

}
