package client;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;

import static io.restassured.RestAssured.given;

public class Steps {

    private static final String createCourierURI =  "/api/v1/courier";
    private static final String signInCourierURI = "/api/v1/courier/login";
    private static final String deleteCourierURI = "/api/v1/courier/";

    @Step
    public static Response createCourier(Courier userCourier){
        Gson gson = new Gson();
        String json = gson.toJson(userCourier);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(createCourierURI);
    }



    @Step
    public static Response signInCourier(Courier signInCourier){
        Gson gson = new Gson();

        String json = gson.toJson(signInCourier);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(signInCourierURI);
    }

    @Step
    public static Response deleteCourier(int idCourier){
        String deleteURIWithId = deleteCourierURI + idCourier;


        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(deleteURIWithId);
    }
}
