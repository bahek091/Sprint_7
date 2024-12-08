package model;

import lombok.Data;

import java.util.List;

@Data
public class ListOfOrdersData {
    private List<OrderData> orders;
}
