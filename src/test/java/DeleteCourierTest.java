import api.CourierAPI;
import model.CourierGenerator;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class DeleteCourierTest extends CourierBaseTest {

    @After
    @Override
    public void cleanData() {
        if (courierId != CourierAPI.EMPTY_ID) {
            courierApi.deleteCourier(courierId);
        }
    }

    @Test
    public void checkDeleteCourierTest() {
        courierData = CourierGenerator.getRandomCourier("TestLogon", "TestPass");
        courierApi.createCourier(courierData).log().all();
        courierId = courierApi.loginCourier(courierData)
                .log().all()
                .extract().path(CourierAPI.LOGIN_ID_FIELD);

        courierApi.deleteCourier(courierId).log().all()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .assertThat()
                .body(CourierAPI.OK_FIELD, is(true));
        courierId = CourierAPI.EMPTY_ID;
    }

    @Test
    public void cannotDeleteCourierWithWrongIdTest() {
        courierApi.deleteCourier(CourierAPI.DUMMY_ID).log().all()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .assertThat()
                .body(CourierAPI.MESSAGE_FIELD, is(CourierAPI.ID_DOESNT_EXISTS));
    }

    @Test
    public void cannotDeleteCourierWithoutIdTest() {
        courierApi.deleteCourier().log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body(CourierAPI.MESSAGE_FIELD, is(CourierAPI.INSUFFICIENT_DELETE_DATA));
    }

}
