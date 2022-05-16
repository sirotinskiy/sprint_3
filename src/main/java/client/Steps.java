package client;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.Courier;
import model.Order;

import static io.restassured.RestAssured.given;

public class Steps {

    private static final String CREATE_COURIER_URI = "/api/v1/courier";
    private static final String SIGN_IN_COURIER_URI = "/api/v1/courier/login";
    private static final String DELETE_COURIER_URI = "/api/v1/courier/";
    private static final String CREATE_ORDER_URI = "/api/v1/orders";

    private static final String CANCEL_ORDER_URI = "/api/v1/orders/cancel";

    private static final String ACCEPT_ORDER_URI = "/api/v1/orders/accept/";
    private static final String GET_ORDER_BY_TRACK_URI = "/api/v1/orders/track";

    @Step
    public static Response createCourier(Courier userCourier) {
        Gson gson = new Gson();
        String json = gson.toJson(userCourier);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(CREATE_COURIER_URI);
    }


    @Step
    public static Response signInCourier(Courier signInCourier) {
        Gson gson = new Gson();

        String json = gson.toJson(signInCourier);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(SIGN_IN_COURIER_URI);
    }

    @Step
    public static Response deleteCourier(int idCourier) {
        String deleteCourierURIWithId = DELETE_COURIER_URI + idCourier;


        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(deleteCourierURIWithId);
    }

    @Step
    public static Response createOrder(Order order) {
        Gson gson = new Gson();

        String json = gson.toJson(order);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post(CREATE_ORDER_URI);
    }

    @Step
    public static Response cancelOrder(int trackOrder) {
        Gson gson = new Gson();

        String json = gson.toJson("track: " + trackOrder);

        return given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .put(CANCEL_ORDER_URI);
    }

    @Step
    public static Response receivingListOrdersWithoutQuery() {
        return given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .get(CREATE_ORDER_URI);
    }

    @Step
    public static Response receivingListOrdersWithCourierId(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .queryParam("courierId", courierId)
                .and()
                .when()
                .get(CREATE_ORDER_URI);
    }

    @Step
    public static Response acceptOrder(int courierId, int id) {
        String acceptOrderURIWithCourierId = ACCEPT_ORDER_URI + id;


        return given()
                .header("Content-type", "application/json")
                .queryParam("courierId", courierId)
                .when()
                .put(acceptOrderURIWithCourierId);
    }

    @Step
    public static Response getOrderByTrack(int track) {
        return given()
                .header("Content-type", "application/json")
                .queryParam("t", track)
                .and()
                .when()
                .get(GET_ORDER_BY_TRACK_URI);
    }
}
