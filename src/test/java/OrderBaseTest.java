import api.CourierAPI;
import api.OrdersAPI;
import model.OrderData;
import org.junit.After;
import org.junit.Before;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderBaseTest {
    protected OrderData orderData;
    protected OrdersAPI ordersApi = new OrdersAPI();
    protected CourierAPI courierApi = new CourierAPI();
    protected int orderTrack;
    protected int courierId;

    @Before
    public void setUp() {
        orderTrack = OrdersAPI.EMPTY_TRACK;
        courierId = CourierAPI.EMPTY_ID;
    }

    @After
    public void cleanUp() {
        if (orderTrack != OrdersAPI.EMPTY_TRACK) {
            int orderId = ordersApi.getOrderIdByTrack(orderTrack);
            ordersApi.cancelOrder(orderId).log().all();
        }
        if(courierId != CourierAPI.EMPTY_ID){
            courierApi.deleteCourier(courierId);
        }
    }

    protected OrderData getFilledOrderData() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime rentDate = LocalDateTime.now().plusDays(OrdersAPI.RENT_DATA_OFFSET);

        return new OrderData("TestFirstName", "TestLastName", "TestAddress",
                "4", "+7 800 355 35 35", "5",
                dtf.format(rentDate), "Test comment field", new String[]{"GREY"});
    }

    protected OrderData getFilledOrderData(String[] colour) {
        OrderData orderData = getFilledOrderData();
        orderData.setColor(colour);
        return orderData;
    }
}
