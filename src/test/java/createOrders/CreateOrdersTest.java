package createOrders;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static client.CourierApi.*;
import static client.OrderApi.cancelOrder;
import static client.OrderApi.createOrder;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;
import static utils.Utils.*;

@RunWith(Parameterized.class)
public class CreateOrdersTest {


    private final int expectStatusCode;
    private final String descriptionData;
    private final Order order;

    private int trackOrder;

    private RequestSpecification spec;

    public CreateOrdersTest(int expectStatusCode, String descriptionData, Order order) {
        this.expectStatusCode = expectStatusCode;
        this.descriptionData = descriptionData;
        this.order = order;
    }


    @Parameterized.Parameters(name = "test data: {0} {1}")
    public static Object[][] getNotValidData(){
        return new Object[][]{
                {SC_CREATED, "Только чёрный", Order.builder().color(new String[]{"BLACK"}).firstName(getRandomString(7)).lastName(getRandomString(7)).address(getRandomString(15)).metroStation(getRandomString(1)).phone("+7 233 123 98 92").rentTime(getRandomNumber(0,10)).deliveryDate(getRandomDate()).comment(getRandomString(24)).build()},
                {SC_CREATED, "Только серый", Order.builder().color(new String[]{"GREY"}).firstName(getRandomString(7)).lastName(getRandomString(7)).address(getRandomString(15)).metroStation(getRandomString(1)).phone("+7 233 123 98 92").rentTime(getRandomNumber(0,10)).deliveryDate(getRandomDate()).comment(getRandomString(24)).build()},
                {SC_CREATED, "Серый и черный", Order.builder().color(new String[]{"GREY", "BLACK"}).firstName(getRandomString(7)).lastName(getRandomString(7)).address(getRandomString(15)).metroStation(getRandomString(1)).phone("+7 233 123 98 92").rentTime(getRandomNumber(0,10)).deliveryDate(getRandomDate()).comment(getRandomString(24)).build()},
                {SC_CREATED, "Цвет отсутствует", Order.builder().color(new String[]{}).firstName(getRandomString(7)).lastName(getRandomString(7)).address(getRandomString(15)).metroStation(getRandomString(1)).phone("+7 233 123 98 92").rentTime(getRandomNumber(0,10)).deliveryDate(getRandomDate()).comment(getRandomString(24)).build()}
        };
    }

    @Before
    public void setUp(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                .setContentType("application/json")
                .build();
    }

    @After
    public  void tearDown(){
        trackOrder = createOrder(order, spec).then().extract().path("track");
        cancelOrder(trackOrder, spec);

    }

    @Test
    @DisplayName("Создание заказ с разными вариациями цвета")
    public void successCreateOrder(){
        Response createOrderResponse = createOrder(order, spec);
        createOrderResponse.then()
                .assertThat()
                .statusCode(expectStatusCode)
                .and()
                .body("track",notNullValue());
    }
}
