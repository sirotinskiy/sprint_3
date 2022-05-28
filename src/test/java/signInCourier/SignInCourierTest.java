package signInCourier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static client.CourierApi.*;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static utils.Utils.getRandomString;

public class SignInCourierTest {
    private Courier courier;
    private int id;

    private RequestSpecification spec;


    @Before
    public void setUp(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                .setContentType("application/json")
                .build();;

        courier = Courier.builder()
                .login(getRandomString(7))
                .password(getRandomString(7))
                .firstName(getRandomString(7))
                .build();
        createCourier(courier, spec);
    }

    @After
    public void tearDown(){
        id = signInCourier(courier, spec).then().extract().path("id");
        deleteCourier(id, spec);
    }

    @Test
    @DisplayName("Успешный вход в систему 200")
    public void successSignIn(){

        Response successSignInResponse = signInCourier(courier, spec);
        successSignInResponse.then()
                .assertThat()
                .body("id",notNullValue())
                .and()
                .statusCode(SC_OK);
    }
}
