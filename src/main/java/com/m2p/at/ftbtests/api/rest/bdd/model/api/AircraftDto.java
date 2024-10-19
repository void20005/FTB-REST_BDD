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
public class AircraftDto {
    //@JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long aircraftId;
    private long id;
    private String manufacturer;
    private String model;
    private Integer numberOfSeats;

    @Builder.Default
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Long> flightIds = new ArrayList<>();

/*    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Override
    public long getId() {
        return aircraftId;
    }

    @Override
    public void setId(long id) {
        this.aircraftId = id;
    }*/
}
