package com.microservice.traceability.infrastructure.out.mongo.adapter;

import com.microservice.traceability.domain.spi.IRestaurantPort;
import com.microservice.traceability.infrastructure.feign.clients.RestaurantClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantAdapter implements IRestaurantPort {

    private final RestaurantClient restaurantClient;

    @Override
    public void validateOwnerOfRestaurant(Long ownerId, Long restaurantId) {
        restaurantClient.isOwnerOfRestaurant(restaurantId, ownerId);
    }
}
