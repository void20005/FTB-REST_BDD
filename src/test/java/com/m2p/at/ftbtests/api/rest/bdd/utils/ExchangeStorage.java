package com.m2p.at.ftbtests.api.rest.bdd.utils;

import com.m2p.at.ftbtests.api.rest.bdd.model.api.ErrorResponse;
import com.m2p.at.ftbtests.api.rest.bdd.model.api.SuccessResponse;
import lombok.Data;

/**
 * A container to keep various data (e.g. API call responses) between scenario steps.
 * This is the way to exchange data between steps e.g. get response, store in here, get the response in the next step.
 */
@Data
public class ExchangeStorage<R> {

    private R lastApiCallSingleItemResponse;
    private int lastApiCallResponseStatus;
    private ErrorResponse lastApiCallErrorResponse;
    private SuccessResponse lastApiCallSuccessResponse;

    public void setLastApiCallSuccessResponse(SuccessResponse successResponse) {
        this.lastApiCallSuccessResponse = successResponse;
    }

    public SuccessResponse getLastApiCallSuccessResponse() {
        return lastApiCallSuccessResponse;
    }


    public void setLastApiCallErrorResponse(ErrorResponse errorResponse) {
        this.lastApiCallErrorResponse = errorResponse;
    }

    public void setLastApiCallResponseStatus(int status) {
        this.lastApiCallResponseStatus = status;
    }

    public int getLastApiCallResponseStatus() {
        return lastApiCallResponseStatus;
    }

    public ErrorResponse getLastApiCallErrorResponse() {
        return lastApiCallErrorResponse;
    }
}
