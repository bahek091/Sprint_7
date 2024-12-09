package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourierData {
    private String login;
    private String password;
    private String firstName;

    public CourierData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public boolean courierIsValid(){
        return (login!=null) && (password!=null);
    }
}
