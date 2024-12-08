import api.CourierAPI;
import io.restassured.response.ValidatableResponse;
import model.CourierGenerator;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest extends CourierBaseTest {

    @Test
    public void courierCanBeCreatedTest() {
        courierData = CourierGenerator.getRandomCourier("User", "Pass", "Name");

        //вызываем метод
        ValidatableResponse response = courierApi.createCourier(courierData);

        //проверка
        response.assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body(CourierAPI.OK_FIELD, is(true));
    }

    @Test
    public void courierWithoutNameCanBeCreatedTest() {
        courierData = CourierGenerator.getRandomCourier("User", "Pass");

        //вызываем метод
        ValidatableResponse response = courierApi.createCourier(courierData);

        //проверка
        response.assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body(CourierAPI.OK_FIELD, is(true));

    }

    @Test
    public void cannotCreateSameCourierTest() {
        courierData = CourierGenerator.getRandomCourier("User", "Pass");

        //вызываем метод
        ValidatableResponse responseFirst = courierApi.createCourier(courierData);
        ValidatableResponse responseSecond = courierApi.createCourier(courierData);
        responseSecond.assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body(CourierAPI.MESSAGE_FIELD, is(CourierAPI.LOGIN_EXISTS));

    }

    @Test
    public void cannotCreateCourierWithoutLoginTest() {
        courierData = CourierGenerator.getRandomCourier(null, "Pass");
        //вызываем метод
        ValidatableResponse response = courierApi.createCourier(courierData);

        //проверка
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(CourierAPI.MESSAGE_FIELD, is(CourierAPI.INSUFFICIENT_INFORMATION));

    }

    @Test
    public void cannotCreateCourierWithoutPasswordTest() {
        courierData = CourierGenerator.getRandomCourier("User", null);
        //вызываем метод
        ValidatableResponse response = courierApi.createCourier(courierData);

        //проверка
        response.assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(CourierAPI.MESSAGE_FIELD, is(CourierAPI.INSUFFICIENT_INFORMATION));

    }


}
