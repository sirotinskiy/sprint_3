package createCourier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static client.Steps.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static utils.Utils.getRandomString;

public class CreateCourierTest {

    private Courier courier;
    private int id;


    @Before
    public void setUp(){
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @After
    public void tearDown(){
        id = signInCourier(courier).then().extract().path("id");
        deleteCourier(id);
    }

    @Test
    @DisplayName("Создание курьера с полными и валидными данными")
    public void successCreateNewCourier(){
        courier = Courier.builder()
                .login(getRandomString(7))
                .password(getRandomString(7))
                .firstName(getRandomString(7))
                .build();
        Response createCourierResponse = createCourier(courier);
        createCourierResponse.then()
                .assertThat()
                .statusCode(SC_CREATED)
                .and()
                .body("ok",equalTo(true));
    }

    @Test
    @DisplayName("Создание не уникального курьера")
    public void createNotUniqueCourier(){
        courier = Courier.builder()
                .login(getRandomString(7))
                .password(getRandomString(7))
                .firstName(getRandomString(7))
                .build();
        createCourier(courier);
        Courier notUnique = Courier.builder()
                .login(courier.getLogin())
                .password(courier.getPassword())
                .firstName(courier.getFirstName())
                .build();
        Response createCourierResponse = createCourier(notUnique);
        createCourierResponse.then()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message",equalTo("Этот логин уже используется"));
    }

}
