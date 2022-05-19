package signInCourier;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import model.Courier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static client.CourierApi.*;
import static io.restassured.RestAssured.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static utils.Utils.getRandomString;

@RunWith(Parameterized.class)
public class SignInCourierWithNotValidDataTest {

    private  final int expectStatusCode;
    private static Courier courier  = Courier.builder().
            login(getRandomString(7))
            .password(getRandomString(7))
            .firstName(getRandomString(7))
            .build();
    private final String expectErrorMessage;

    private final Courier courierSignIn;

    private final String descriptionData;

    private int id;

    private RequestSpecification spec;


    public SignInCourierWithNotValidDataTest(int expectStatusCode, String expectErrorMessage, Courier courierSignIn, String descriptionData) {
        this.expectStatusCode = expectStatusCode;
        this.expectErrorMessage = expectErrorMessage;
        this.courierSignIn = courierSignIn;
        this.descriptionData = descriptionData;
    }

    @Parameterized.Parameters(name = "test data: {0} {3}")
    public static Object[][] getNotValidData(){
        return new Object[][]{
                {SC_BAD_REQUEST, "Недостаточно данных для входа", Courier.builder().login(courier.getLogin()).password("").build(), "Отсутствует пароль"},
                {SC_BAD_REQUEST, "Недостаточно данных для входа", Courier.builder().login("").password(courier.getPassword()).build(), "Отсутствует логин"},
                {SC_BAD_REQUEST, "Недостаточно данных для входа", Courier.builder().login("").password("").build(), "Отсутствует лог/пасс"},
                {SC_NOT_FOUND, "Учетная запись не найдена", Courier.builder().login(getRandomString(7)).password(getRandomString(7)).build(), "Несуществующий аккаунт"}
        };
    }

    @Before
    public void setUp(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://qa-scooter.praktikum-services.ru/")
                .setContentType("application/json")
                .build();

        createCourier(courier, spec);
    }

    @After
    public void tearDown(){
        id = signInCourier(courier, spec).then().extract().path("id");
        deleteCourier(id, spec);
    }

    @Test
    @DisplayName("Вход в систему с неполными или с не существующими данными 400, 404")
    public void unSuccessSignIn(){
        Response successSignInResponse = signInCourier(courierSignIn, spec);
        successSignInResponse.then()
                .assertThat()
                .statusCode(expectStatusCode)
                .and()
                .body("message",equalTo(expectErrorMessage));
    }

}
