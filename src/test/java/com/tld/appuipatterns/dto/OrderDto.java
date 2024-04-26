package com.tld.appuipatterns.dto;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class OrderDto {

    private String status;
    private Integer courierId;
    private String customerName;
    private String customerPhone;
    private String comment;
    private Integer id;

    public void setStatus(String status) {
        this.status = status;
    }

    public OrderDto(String status, String customerName, String customerPhone) {
        this.status = status;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.comment = RandomStringUtils.randomAlphanumeric(2);
        this.id = Integer.valueOf( RandomStringUtils.randomNumeric(1,3) );
    }

    // static method to use factory
    public static OrderDto createRandomOrder() {
        return new OrderDto("OPEN", "randomName", "54321678");
    }

    public static OrderDto createRandomDeliveredOrder() {
        return new OrderDto("DELIVERED", "randomName", "54321678");
    }
}
