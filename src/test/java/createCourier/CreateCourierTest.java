package createCourier;

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
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static utils.Utils.getRandomString;

public class CreateCourierTest {

    private Courier courier;
    private int id;

    private RequestSpecification spec;


    @Before
    public void setUp(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                .setContentType("application/json")
                .build();
    }

    @After
    public void tearDown(){
        id = signInCourier(courier, spec).then().extract().path("id");
        deleteCourier(id, spec);
    }

    @Test
    @DisplayName("Создание курьера с полными и валидными данными")
    public void successCreateNewCourier(){
        courier = Courier.builder()
                .login(getRandomString(7))
                .password(getRandomString(7))
                .firstName(getRandomString(7))
                .build();
        Response createCourierResponse = createCourier(courier, spec);
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
        createCourier(courier, spec);
        Courier notUnique = Courier.builder()
                .login(courier.getLogin())
                .password(courier.getPassword())
                .firstName(courier.getFirstName())
                .build();
        Response createCourierResponse = createCourier(notUnique, spec);
        createCourierResponse.then()
                .assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message",equalTo("Этот логин уже используется"));
    }

}
