package client;

import io.qameta.allure.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Courier;

import static io.restassured.RestAssured.given;

public class CourierApi {

    private static final String CREATE_COURIER_URI = "/api/v1/courier";
    private static final String SIGN_IN_COURIER_URI = "/api/v1/courier/login";
    private static final String DELETE_COURIER_URI = "/api/v1/courier/";


    @Step("Создание нового курьера")
    public static Response createCourier(Courier userCourier, RequestSpecification spec) {

        return given()
                .spec(spec)
                .and()
                .body(userCourier)
                .when()
                .post(CREATE_COURIER_URI);
    }


    @Step("Логин курьера в системе")
    public static Response signInCourier(Courier signInCourier, RequestSpecification spec) {

        return given()
                .spec(spec)
                .and()
                .body(signInCourier)
                .when()
                .post(SIGN_IN_COURIER_URI);
    }

    @Step("Удаление курьера")
    public static Response deleteCourier(int idCourier, RequestSpecification spec) {
        String deleteCourierURIWithId = DELETE_COURIER_URI + idCourier;


        return given()
                .spec(spec)
                .when()
                .delete(deleteCourierURIWithId);
    }
}
