package createCourier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static client.CreateCourier.*;
import static org.hamcrest.Matchers.equalTo;
import static utils.CourierUtils.getRandomString;

@RunWith(Parameterized.class)
public class CreateCourierWithNotValidDataTest {

    private final int expectStatusCode;
    private final String expectErrorMessage;
    private final Courier courier;
    private final String descriptionData;

    private int id;


    public CreateCourierWithNotValidDataTest(int expectStatusCode, String expectErrorMessage, Courier courier, String descriptionData) {
        this.expectStatusCode = expectStatusCode;
        this.expectErrorMessage = expectErrorMessage;
        this.courier = courier;
        this.descriptionData = descriptionData;
    }

    @Parameterized.Parameters(name = "Тестовые данные: {0} {1} {2} {3}")
    public static Object[][] getNitValidCourier() {
        return new Object[][]{
                {400, "Недостаточно данных для создания учетной записи", Courier.builder().password(getRandomString(7)).firstName(getRandomString(7)).build(), "Отсутствует логин"},
                {400, "Недостаточно данных для создания учетной записи", Courier.builder().login(getRandomString(7)).firstName(getRandomString(7)).build(), "Отсутствует пароль"},
                {400, "Недостаточно данных для создания учетной записи", Courier.builder().login(getRandomString(7)).password(getRandomString(7)).build(), "Отсутствует имя"},
                {400, "Недостаточно данных для создания учетной записи", Courier.builder().firstName(getRandomString(7)).build(), "Отсутствует лог/пасс"},
                {400, "Недостаточно данных для создания учетной записи", Courier.builder().build(), "Отсутствует лог/пасс и имя"}
        };
    }


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
    @DisplayName("Создание курьера с неполным набором данных")
    public void notValidCreateNewCourier(){
        Response createCourierResponse = createCourier(courier);
        createCourierResponse.then()
                .assertThat()
                .statusCode(expectStatusCode)
                .and()
                .body("message",equalTo(expectErrorMessage));
    }

}
