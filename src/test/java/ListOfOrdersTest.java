import api.ListOfOrdersAPI;
import api.OrdersAPI;
import model.ListOfOrdersData;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class ListOfOrdersTest {
    ListOfOrdersAPI listOfOrdersAPI = new ListOfOrdersAPI();

    @Test
    public void checkListOfOrdersTest() {
        listOfOrdersAPI.getListOf10AvailableOrders()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .body(notNullValue());


    }

    @Test
    public void checkListOfOrdersIsReturnedTest() {
        ListOfOrdersData ordersList = listOfOrdersAPI.getListOf10AvailableOrders()
                .log().all()
                .statusCode(HttpStatus.SC_OK)
                .assertThat()
                .body(OrdersAPI.ORDERS_FIELD, notNullValue())
                .extract().as(ListOfOrdersData.class);
        Assert.assertFalse(ordersList.getOrders().isEmpty());

    }
}
