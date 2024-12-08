import api.CourierAPI;
import model.CourierData;
import model.CourierGenerator;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class LoginCourierTest extends CourierBaseTest{

    @Before
    public void setUpLoginCourier() {
        courierData = CourierGenerator.getRandomCourier("TestLogon", "TestPass");
        courierApi.createCourier(courierData).log().all();
    }

    @Test
    public void courierCanLoginTest() {
        courierId = courierApi.loginCourier(courierData)
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(CourierAPI.LOGIN_ID_FIELD, notNullValue())
                .extract().path(CourierAPI.LOGIN_ID_FIELD);
    }

    @Test
    public void courierCantLoginWhithoutLoginTest() {
        courierApi.loginCourier(new CourierData(null, courierData.getPassword()))
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body(CourierAPI.MESSAGE_FIELD, is(CourierAPI.INSUFFICIENT_LOGIN_DATA));
    }

    @Test
    public void courierCannotLoginWhithoutPasswordTest() {
        courierApi.loginCourier(new CourierData(courierData.getLogin(), null))
                .log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body(CourierAPI.MESSAGE_FIELD, is(CourierAPI.INSUFFICIENT_LOGIN_DATA));
    }

    @Test
    public void courierCannotLoginWithWrongPasswordTest() {
        String wrongPassword = courierData.getPassword() + courierData.getPassword() ;
        courierApi.loginCourier(new CourierData(courierData.getLogin(), wrongPassword))
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .assertThat()
                .body(CourierAPI.MESSAGE_FIELD, is(CourierAPI.WRONG_LOGIN_DATA));
    }

    @Test
    public void courierCannotLoginWithWrongLoginTest() {
        String wrongLogin = courierData.getLogin() + courierData.getLogin();
        courierApi.loginCourier(new CourierData(wrongLogin, courierData.getPassword()))
                .log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .assertThat()
                .body(CourierAPI.MESSAGE_FIELD, is(CourierAPI.WRONG_LOGIN_DATA));
    }
}
