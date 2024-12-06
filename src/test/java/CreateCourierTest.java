import api.CourierAPI;
import io.restassured.response.ValidatableResponse;
import model.CourierGenerator;
import model.CourierLoginData;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import model.CourierData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest {
    private CourierAPI courier;
    private int courierId = 0;
    private CourierData data;

    @Before
    public void setUp(){
        courier = new CourierAPI();
    }

    @After
    public void cleanData(){
        if(courierId > 0){
            courier.deleteCourier(courierId);
        } else {
            CourierLoginData loginData = courier.loginCourier(data).body().as(CourierLoginData.class);
            courier.deleteCourier(loginData.getId());
        }
    }

    @Test
    public void courierCanBeCreatedTest(){
        data = CourierGenerator.getRandomCourier("User", "Pass", "Name");

        //вызываем метод
        ValidatableResponse response = courier.createCourier(data);

        //проверка
        response.assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    public void courierWithoutNameCanBeCreatedTest(){
        data = CourierGenerator.getRandomCourier("User", "Pass");

        //вызываем метод
        ValidatableResponse response = courier.createCourier(data);

        //проверка
        response.assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));

    }

    @Test
    public void cantCreateSameCourierTest(){
        data = CourierGenerator.getRandomCourier("User", "Pass");

        //вызываем метод
        ValidatableResponse responseFirst = courier.createCourier(data);
        ValidatableResponse responseSecond = courier.createCourier(data);
        responseSecond.assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .body("message", is("Этот логин уже используется"));

    }

    @Test
    public void cantCreateCourierWithoutLoginTest(){

    }

    @Test
    public void cantCreateCourierWithoutPasswordTest(){

    }




}
