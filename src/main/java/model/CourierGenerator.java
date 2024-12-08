package model;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;


public class CourierGenerator {
    @Step("Generate user")
    public static CourierData getRandomCourier(){
        return new CourierData(RandomStringUtils.randomAlphabetic(8),
                RandomStringUtils.randomAlphabetic(8),
                RandomStringUtils.randomAlphabetic(8));
    }

    @Step("Generate user whit prefixes")
    public static CourierData getRandomCourier(String login, String password, String firstName){
        return new CourierData(login + RandomStringUtils.randomAlphabetic(4),
                password + RandomStringUtils.randomAlphabetic(4),
                firstName+ RandomStringUtils.randomAlphabetic(4));
    }

    @Step("Generate user whit prefixes")
    public static CourierData getRandomCourier(String login, String password){
        String newLogin = (login == null) ? null: login + RandomStringUtils.randomAlphabetic(4);
        String newPass = (password == null) ? null: password + RandomStringUtils.randomAlphabetic(4);

        return new CourierData(newLogin, newPass);
    }



}
