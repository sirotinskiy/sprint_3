package signInCourier;

import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static client.Steps.*;

import static org.hamcrest.Matchers.*;
import static utils.CourierUtils.getRandomString;

public class SignInCourierTest {
    private Courier courier;
    private int id;


    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";

        courier = Courier.builder()
                .login(getRandomString(7))
                .password(getRandomString(7))
                .firstName(getRandomString(7))
                .build();
        createCourier(courier);
    }

    @After
    public void tearDown(){
        id = signInCourier(courier).then().extract().path("id");
        deleteCourier(id);
    }

    @Test
    @DisplayName("Успешный вход в систему 201")
    public void successSignIn(){

        Response successSignInResponse = signInCourier(courier);
        successSignInResponse.then()
                .assertThat()
                .body("id",greaterThanOrEqualTo(0))
                .and()
                .statusCode(200);
    }
}