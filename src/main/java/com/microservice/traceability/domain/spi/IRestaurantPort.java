package com.microservice.traceability.domain.spi;

public interface IRestaurantPort {

    void validateOwnerOfRestaurant(Long ownerId, Long restaurantId);

}
