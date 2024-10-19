package com.m2p.at.ftbtests.api.rest.bdd.steps;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.m2p.at.ftbtests.api.rest.bdd.model.api.ErrorResponse;
import com.m2p.at.ftbtests.api.rest.bdd.model.api.SuccessResponse;
import io.cucumber.datatable.DataTable;
import java.util.Map;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.m2p.at.ftbtests.api.rest.bdd.model.api.FlightDto;
import com.m2p.at.ftbtests.api.rest.bdd.model.api.CreateFlightDto;
import com.m2p.at.ftbtests.api.rest.bdd.utils.ApiCalls;
import com.m2p.at.ftbtests.api.rest.bdd.utils.ExchangeStorage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import static org.assertj.core.api.Assertions.assertThat;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;


/**
 * A step-definition class to contain FTB REST API FLIGHTS resource related steps.
 */
@Slf4j
public class FlightSteps {

    private final static String PATH = "flights";
    private final static String PATH_DETAILS = PATH + "/{id}";
    public static final int SC_CREATED = 201;
    public static final int SC_BAD_REQUEST = 400;
    public static final int SC_NOT_FOUND = 404;
    private final ApiCalls apiCalls;
    private final ExchangeStorage<FlightDto> storage;
    public FlightSteps() {
        this.apiCalls = new ApiCalls();
        this.storage = new ExchangeStorage<>();
    }

    @When("client gets details of Flight id={long}")
    public void getById(long id) {
        var response = apiCalls.doGet(SC_OK, FlightDto.class, PATH_DETAILS, String.valueOf(id));
        storage.setLastApiCallSingleItemResponse(response);
    }


    @Then("the API should return valid flight details:")
    public void verifyFlightDetails(DataTable dataTable) {

        List<List<String>> rows = dataTable.asLists(String.class);
        List<String> expectedFlightData = rows.get(1);
        System.out.println("Expected data: " + expectedFlightData);

        var lastResponse = storage.getLastApiCallSingleItemResponse();
        System.out.println("Recieved data: " + lastResponse);

        assertThat(lastResponse.getFlightNumber()).isEqualTo(expectedFlightData.get(0)); // flightNumber
        assertThat(lastResponse.getDepartureAirportCode()).isEqualTo(expectedFlightData.get(1)); // departureAirportCode
        assertThat(lastResponse.getDestinationAirportCode()).isEqualTo(expectedFlightData.get(2)); // destinationAirportCode
        assertThat(lastResponse.getDepartureDate()).isEqualTo(parseDate(expectedFlightData.get(3))); // departureDate
        assertThat(lastResponse.getArrivalDate()).isEqualTo(parseDate(expectedFlightData.get(4))); // arrivalDate
        assertThat(lastResponse.getDepartureTime()).isEqualTo(expectedFlightData.get(5)); // departureTime
        assertThat(lastResponse.getArrivalTime()).isEqualTo(expectedFlightData.get(6)); // arrivalTime
        assertThat(lastResponse.getGate()).isEqualTo(expectedFlightData.get(7)); // gate
        assertThat(lastResponse.getStatus()).isEqualTo(expectedFlightData.get(8)); // status
        assertThat(lastResponse.getFlightCharge()).isEqualTo(Double.valueOf(expectedFlightData.get(9))); // flightCharge

    }

    private List<Integer> parseDate(String dateStr) {
        return Arrays.stream(dateStr.split("-"))
                .map(Integer::valueOf)
                .collect(Collectors.toList());
    }



    @When("the client sends a POST request to create a flight, expected status = {int}, data:")
    public void createFlightWithExpectedStatus(int expectedStatusCode, io.cucumber.datatable.DataTable flightParamsTable) throws JsonProcessingException {
        // Convert the DataTable to a list of maps, one map per row
        List<Map<String, String>> flightParams = flightParamsTable.asMaps(String.class, String.class);

        for (Map<String, String> flightParam : flightParams) {
            CreateFlightDto flightRequest = new CreateFlightDto();
            flightRequest.setFlightNumber(trimQuotes(flightParam.get("flightNumber")));
            flightRequest.setDepartureAirportCode(trimQuotes(flightParam.get("departureAirportCode")));
            flightRequest.setDestinationAirportCode(trimQuotes(flightParam.get("destinationAirportCode")));
            flightRequest.setDepartureDate(trimQuotes(flightParam.get("departureDate")));
            flightRequest.setArrivalDate(trimQuotes(flightParam.get("arrivalDate")));
            flightRequest.setDepartureTime(trimQuotes(flightParam.get("departureTime")));
            flightRequest.setArrivalTime(trimQuotes(flightParam.get("arrivalTime")));
            flightRequest.setGate(trimQuotes(flightParam.get("gate")));
            flightRequest.setStatus(trimQuotes(flightParam.get("status")));
            flightRequest.setFlightCharge(Double.valueOf(flightParam.get("flightCharge")));
            flightRequest.setAircraftId(Long.valueOf(flightParam.get("aircraftId")));

            // Make the API call and handle both 201 (success) and 400 (error)
            if (expectedStatusCode == SC_CREATED) {
                var response = apiCalls.doPost(SC_CREATED, FlightDto.class, flightRequest, "flights");
                storage.setLastApiCallSingleItemResponse(response);
            } else if (expectedStatusCode == SC_BAD_REQUEST) {
                var response = apiCalls.doPost(SC_BAD_REQUEST, ErrorResponse.class, flightRequest, "flights");
                storage.setLastApiCallErrorResponse(response);
            } else {
                throw new IllegalArgumentException("Unexpected status code: " + expectedStatusCode);
            }
        }
    }

    private String trimQuotes(String input) {
        if (input != null && input.startsWith("\"") && input.endsWith("\"")) {
            return input.substring(1, input.length() - 1);
        }
        return input;
    }


    @Then("the API should return expectedStatusCode = {string} with expectedErrorMessage = {string}")
    public void verifyErrorMessage(String expectedStatusCode, String expectedErrorMessage) {
        ErrorResponse actualErrorResponse = storage.getLastApiCallErrorResponse();
        if (actualErrorResponse == null) {
            System.out.println("Error response is null");
            throw new AssertionError("Error response was expected but is null");
        }
        System.out.println("Received status code: " + actualErrorResponse.getStatus());
        System.out.println("Received error message: " + actualErrorResponse.getMessage());
        assertThat(actualErrorResponse.getStatus())
                .as("Expected status code to be returned")
                .isEqualTo(expectedStatusCode);
        assertThat(actualErrorResponse.getMessage())
                .as("Expected error message to be returned")
                .contains(expectedErrorMessage);
    }


    @Then("the API should return expectedMessage = {string}")
    public void verifyAnswerMessage(String expectedStatusCode, String expectedErrorMessage) {
        ErrorResponse actualErrorResponse = storage.getLastApiCallErrorResponse();
        if (actualErrorResponse == null) {
            System.out.println("Error response is null");
            throw new AssertionError("Error response was expected but is null");
        }
        System.out.println("Received status code: " + actualErrorResponse.getStatus());
        System.out.println("Received error message: " + actualErrorResponse.getMessage());
        assertThat(actualErrorResponse.getStatus())
                .as("Expected status code to be returned")
                .isEqualTo(expectedStatusCode);
        assertThat(actualErrorResponse.getMessage())
                .as("Expected error message to be returned")
                .contains(expectedErrorMessage);
    }
    @When("the client sends a DELETE request to delete flight with flightId = {int}")
    @When("When the client sends a DELETE request to delete flight with flightId = {int}")
    public void deleteFlightById(int flightId) {
        try {
            // Accept both 200 OK and 404 Not Found
            SuccessResponse successResponse = apiCalls.doDelete(new int[]{SC_OK, SC_NOT_FOUND}, SuccessResponse.class, "flights/" + flightId);
            storage.setLastApiCallSuccessResponse(successResponse);
        } catch (Exception e) {
            log.error("Error occurred while deleting flight with ID: " + flightId, e);
        }
    }


    @Then("the API should return code = {int} with message = {string} for deleted flight")
    public void verifySuccessMessage(int expectedCode, String expectedMessage) {
        SuccessResponse successResponse = storage.getLastApiCallSuccessResponse();

        System.out.println("Received code: " + successResponse.getCode());
        System.out.println("Received message: " + successResponse.getMessage());

        assertThat(successResponse.getCode())
                .as("Expected status code to be returned")
                .isEqualTo(expectedCode);

        assertThat(successResponse.getMessage())
                .as("Expected message to be returned")
                .isEqualTo(expectedMessage);
    }

}
