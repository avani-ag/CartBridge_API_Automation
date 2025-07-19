package core;
import io.restassured.RestAssured;
import org.testng.annotations.*;
import utils.ConfigReader;
import utils.SoftAssertionUtil;

public class Base {
    protected SoftAssertionUtil assertObj;
    protected String testDataFilePath;
    protected String baseURL;


        @BeforeClass
        public void setUpBeforeClass() {

            testDataFilePath = "resources/testdata.json";
            baseURL= (String) ConfigReader.GetProperty("baseURL");


        }

        @BeforeMethod
        public void setUpBeforeMethod() {
            RestAssured.baseURI= baseURL;
            assertObj=SoftAssertionUtil.getClassObj();

        }

        @AfterMethod
        public void tearDownAfterMethod() {
            assertObj.assertAll();
            
        }

        @AfterClass
        public void tearDownAfterClass() {
        }
    }


