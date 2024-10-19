package com.m2p.at.ftbtests.api.rest.bdd.model.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(builderMethodName = "of")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FlightDto {
    private Long flightId;  // read-only
    private String flightNumber;
    private String departureAirportCode;
    private String destinationAirportCode;
    private List<Integer> departureDate;
    private List<Integer> arrivalDate;
    private String departureTime;
    private String arrivalTime;
    private String gate;
    private String status;
    private Double flightCharge;
    private Long aircraftId;
    private List<Long> passengerIds;
    private Long id;  // read-only

}
