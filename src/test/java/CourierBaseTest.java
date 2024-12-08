import api.CourierAPI;
import model.CourierData;
import org.junit.After;
import org.junit.Before;

public class CourierBaseTest {
    protected CourierAPI courierApi = new CourierAPI();;
    protected int courierId;
    protected CourierData courierData;

    @Before
    public void setUp() {
        courierId = CourierAPI.EMPTY_ID;
    }

    @After
    public void cleanData() {
        if (courierId != CourierAPI.EMPTY_ID) {
            courierApi.deleteCourier(courierId);
        } else {
            if (courierData.courierIsValid()) {
                courierId = courierApi.loginCourier(courierData).extract().path(CourierAPI.LOGIN_ID_FIELD);
                courierApi.deleteCourier(courierId).log().all();
            }
        }
    }

}
