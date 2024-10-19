package com.m2p.at.ftbtests.api.rest.bdd.model.api;

import lombok.*;

@Data
@Builder(builderMethodName = "of")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateFlightDto {
    private String flightNumber;
    private String departureAirportCode;
    private String destinationAirportCode;
    private String departureDate;
    private String arrivalDate;
    private String departureTime;
    private String arrivalTime;
    private String gate;
    private String status;
    private Double flightCharge;
    private Long aircraftId;
}

