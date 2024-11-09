package com.spring.service;

import com.spring.dto.Request.MarketPlaceRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.entities.MarketPlace;

import java.util.List;

public interface MarketPlaceService {
    CommonResponse createMarketPlace(MarketPlaceRequest marketPlaceRequest);
    List<MarketPlace> getAllMarketPlaces();
    MarketPlace getMarketPlace(Integer id);
    CommonResponse updateMarketPlace(Integer id, MarketPlaceRequest marketPlaceRequest);
    CommonResponse deleteMarketPlace(Integer id);
}
