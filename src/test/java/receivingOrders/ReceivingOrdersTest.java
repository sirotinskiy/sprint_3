package receivingOrders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Courier;
import model.Order;
import org.junit.Before;
import org.junit.Test;

import static client.CourierApi.*;
import static client.OrderApi.*;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static utils.Utils.*;

public class ReceivingOrdersTest {

    private RequestSpecification spec;

    @Before
    public void setUp(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                .setContentType("application/json")
                .build();
    }

    @Test
    @DisplayName("Получение списка заказов без параметров")
    public void ordersListWithoutQuery(){
        Response successReceivingListOrders = receivingListOrdersWithoutQuery(spec);

        successReceivingListOrders.then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("size()", greaterThanOrEqualTo(0))
                .and()
                .body("orders", notNullValue());
    }

    @Test
    @DisplayName("Получение списка заказов у конкретного курьера")
    public void ordersListWithCourierId(){
        Courier courier = Courier.builder()
                .login(getRandomString(7))
                .password(getRandomString(7))
                .firstName(getRandomString(7)).build();
        createCourier(courier, spec);

        Order order = Order.builder()
                .color(new String[]{"BLACK"})
                .firstName(getRandomString(7))
                .lastName(getRandomString(7))
                .address(getRandomString(15))
                .metroStation(getRandomString(1))
                .phone("+7 233 123 98 92")
                .rentTime(getRandomNumber(0,10))
                .deliveryDate(getRandomDate())
                .comment(getRandomString(24)).build();

        int track = createOrder(order, spec).then().extract().path("track");

        int orderId = getOrderByTrack(track, spec).then().extract().path("order.id");

        int courierId = signInCourier(courier, spec).then().extract().path("id");

        acceptOrder(courierId,orderId, spec);

        receivingListOrdersWithCourierId(courierId, spec).then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders.id", equalTo(orderId))
                .and()
                .body("orders.courierId", equalTo(courierId));

        cancelOrder(track, spec);

        deleteCourier(courierId, spec);
    }

}
