package com.spring.service.impl;

import com.spring.dto.Request.MarketPlaceRequest;
import com.spring.dto.response.CommonResponse;
import com.spring.entities.MarketPlace;
import com.spring.repository.MarketPlaceRepository;
import com.spring.service.MarketPlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MarketPlaceServiceImpl implements MarketPlaceService {

    private final MarketPlaceRepository marketPlaceRepository;

    private MarketPlace findById(Integer id) {
        return marketPlaceRepository.findById(id).orElseThrow(
                () -> new RuntimeException(CommonResponse.entityNotFound().toString()));
    }

    public CommonResponse createMarketPlace(MarketPlaceRequest marketPlaceRequest) {
        MarketPlace marketPlace = new MarketPlace();
        marketPlace.setItemId(marketPlaceRequest.getItemId());
        marketPlace.setQuantity(marketPlaceRequest.getQuantity());
        marketPlace.setLocationId(marketPlaceRequest.getLocationId());
        marketPlace.setPrice(marketPlaceRequest.getPrice());
        marketPlaceRepository.save(marketPlace);
        return CommonResponse.success();
    }

    public List<MarketPlace> getAllMarketPlaces() {
        return marketPlaceRepository.findAll();
    }

    public MarketPlace getMarketPlace(Integer id) {
        return this.findById(id);
    }

    public CommonResponse updateMarketPlace(Integer id, MarketPlaceRequest marketPlaceRequest) {
        MarketPlace marketPlace = this.findById(id);
        marketPlace.setItemId(marketPlaceRequest.getItemId());
        marketPlace.setQuantity(marketPlaceRequest.getQuantity());
        marketPlace.setLocationId(marketPlaceRequest.getLocationId());
        marketPlace.setPrice(marketPlaceRequest.getPrice());
        marketPlaceRepository.save(marketPlace);
        return CommonResponse.success();
    }

    public CommonResponse deleteMarketPlace(Integer id) {
        MarketPlace marketPlace = this.findById(id);
        marketPlaceRepository.delete(marketPlace);
        return CommonResponse.success();
    }
}
