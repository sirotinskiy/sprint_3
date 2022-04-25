import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static client.CreateCourier.*;
import static org.hamcrest.Matchers.equalTo;
import static utils.CourierUtils.getRandomString;

public class CreateCourierTest {

    private Courier сourier;
    private int id;


    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void tearDown(){
        id = signInCourier(сourier).then().extract().path("id");
        deleteCourier(id);
    }

    @Test
    public void successCreateNewCourier(){
        сourier = new Courier(getRandomString(7),getRandomString(7),getRandomString(7));
        Response createCourierResponse = createCourier(сourier);
        createCourierResponse.then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("ok",equalTo(true));
    }

}
