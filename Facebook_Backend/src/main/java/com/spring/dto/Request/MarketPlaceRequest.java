package com.spring.dto.Request;

import lombok.Data;

@Data
public class MarketPlaceRequest {
    private Integer itemId;
    private Integer quantity;
    private Integer locationId;
    private Integer price;
}
