import api.OrdersAPI;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(value = Parameterized.class)
public class CreateOrderTest extends OrderBaseTest {

    public CreateOrderTest(String[] colour) {
        orderData = getFilledOrderData(colour);
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{}}
        };
    }


    @Test
    public void canCreateOrderTest() {
        orderTrack = ordersApi.createOrder(orderData)
                .log().all()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .assertThat()
                .body(OrdersAPI.ORDER_TRACK_FIELD, notNullValue())
                .extract().path(OrdersAPI.ORDER_TRACK_FIELD);
    }


}
