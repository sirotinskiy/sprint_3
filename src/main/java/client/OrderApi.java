package client;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Order;

import static io.restassured.RestAssured.given;

public class OrderApi {

    private static final String CREATE_ORDER_URI = "/api/v1/orders";

    private static final String CANCEL_ORDER_URI = "/api/v1/orders/cancel";

    private static final String ACCEPT_ORDER_URI = "/api/v1/orders/accept/";
    private static final String GET_ORDER_BY_TRACK_URI = "/api/v1/orders/track";

    @Step("Создание заказа")
    public static Response createOrder(Order order, RequestSpecification spec) {

        return given()
                .spec(spec)
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_URI);
    }

    @Step("Отмена заказа")
    public static Response cancelOrder(int trackOrder, RequestSpecification spec) {
        Gson gson = new Gson();

        String json = gson.toJson("track: " + trackOrder);

        return given()
                .spec(spec)
                .and()
                .body(json)
                .when()
                .put(CANCEL_ORDER_URI);
    }

    @Step("Получение списка заказов без параметров")
    public static Response receivingListOrdersWithoutQuery(RequestSpecification spec) {
        return given()
                .spec(spec)
                .and()
                .when()
                .get(CREATE_ORDER_URI);
    }

    @Step("Получение списка заказов конкретного курьера(по id)")
    public static Response receivingListOrdersWithCourierId(int courierId, RequestSpecification spec) {
        return given()
                .spec(spec)
                .queryParam("courierId", courierId)
                .and()
                .when()
                .get(CREATE_ORDER_URI);
    }

    @Step("Принятие заказа курьером")
    public static Response acceptOrder(int courierId, int id, RequestSpecification spec) {
        String acceptOrderURIWithCourierId = ACCEPT_ORDER_URI + id;


        return given()
                .spec(spec)
                .queryParam("courierId", courierId)
                .when()
                .put(acceptOrderURIWithCourierId);
    }

    @Step("Получение заказа по его номеру(track)")
    public static Response getOrderByTrack(int track, RequestSpecification spec) {
        return given()
                .spec(spec)
                .queryParam("t", track)
                .and()
                .when()
                .get(GET_ORDER_BY_TRACK_URI);
    }
}
