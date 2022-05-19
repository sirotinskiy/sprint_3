package createCourier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Courier;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static client.CourierApi.*;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.hamcrest.Matchers.equalTo;
import static utils.Utils.getRandomString;

@RunWith(Parameterized.class)
public class CreateCourierWithNotValidDataTest {

    private final int expectStatusCode;
    private final String expectErrorMessage;
    private final Courier courier;
    private final String descriptionData;

    private RequestSpecification spec;

    public CreateCourierWithNotValidDataTest(int expectStatusCode, String expectErrorMessage, Courier courier, String descriptionData) {
        this.expectStatusCode = expectStatusCode;
        this.expectErrorMessage = expectErrorMessage;
        this.courier = courier;
        this.descriptionData = descriptionData;
    }

    @Parameterized.Parameters(name = "test data: {0} {3}")
    public static Object[][] getNotValidCourier() {
        return new Object[][]{
                {SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи", Courier.builder().login("").password(getRandomString(7)).firstName(getRandomString(7)).build(), "Отсутствует логин"},
                {SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи", Courier.builder().login(getRandomString(7)).password("").firstName(getRandomString(7)).build(), "Отсутствует пароль"},
                {SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи", Courier.builder().login(getRandomString(7)).password(getRandomString(7)).firstName("").build(), "Отсутствует имя"},
                {SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи", Courier.builder().login("").password("").firstName(getRandomString(7)).build(), "Отсутствует лог/пасс"},
                {SC_BAD_REQUEST, "Недостаточно данных для создания учетной записи", Courier.builder().login("").password("").firstName("").build(), "Отсутствует лог/пасс и имя"}
        };
    }


    @Before
    public void setUp(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                .setContentType("application/json")
                .build();
    }


    @Test
    @DisplayName("Создание курьера с неполным набором данных")
    public void notValidCreateNewCourier(){
        Response createCourierResponse = createCourier(courier, spec);
        createCourierResponse.then()
                .assertThat()
                .statusCode(expectStatusCode)
                .and()
                .body("message",equalTo(expectErrorMessage));
    }

}
