Feature: Flight API validation

  Scenario: CREATE a flight with VALID DATA
    Given FTB is up and running and the tests are configured
    When the client sends a POST request to create a flight, expected status = 201, data:
      |flightNumber|departureAirportCode|destinationAirportCode|departureDate|arrivalDate |departureTime|arrivalTime|gate |status   |flightCharge|aircraftId|
      | "FL123"    | "LED"              | "NNV"                | "2024-10-20"|"2024-10-20"| "08:00"     | "11:00"   |"A12"|"delayed"| 199.99     |    1     |
    Then the API should return valid flight details:
      |flightNumber|departureAirportCode|destinationAirportCode|departureDate|arrivalDate|departureTime|arrivalTime|gate|status |flightCharge|
      | FL123      | LED                | NNV                  |2024-10-20   |2024-10-20 | 08:00       | 11:00     | A12|delayed|199.99      |

  Scenario: CREATE a flight with INVALID DATA
    Given FTB is up and running and the tests are configured
    When the client sends a POST request to create a flight, expected status = 400, data:
      |flightNumber|departureAirportCode|destinationAirportCode|departureDate|arrivalDate |departureTime|arrivalTime|gate |status   |flightCharge|aircraftId|
      |   ""  | "LED"              | "NNV"                | "2024-10-20"|"2024-10-20"| "08:00"     | "11:00"   |"A12"|"delayed"| 199.99     |    1     |
    Then the API should return expectedStatusCode = "400 BAD_REQUEST - BAD, BAD REQUEST" with expectedErrorMessage = "Validation Failed"

  Scenario: CREATE a flight with already EXISTING FLIGHT NUMBER
    Given FTB is up and running and the tests are configured
    When the client sends a POST request to create a flight, expected status = 201, data:
      |flightNumber|departureAirportCode|destinationAirportCode|departureDate|arrivalDate |departureTime|arrivalTime|gate |status   |flightCharge|aircraftId|
      | "FL123"    | "LED"              | "NNV"                | "2024-10-20"|"2024-10-20"| "08:00"     | "11:00"   |"A12"|"delayed"| 199.99     |    1     |
    When the client sends a POST request to create a flight, expected status = 201, data:
      |flightNumber|departureAirportCode|destinationAirportCode|departureDate|arrivalDate |departureTime|arrivalTime|gate |status   |flightCharge|aircraftId|
      | "FL123"    | "LED"              | "NNV"                | "2024-10-20"|"2024-10-20"| "08:00"     | "11:00"   |"A12"|"delayed"| 199.99     |    1     |
    Then the API should return expectedStatusCode = "400 BAD_REQUEST - BAD, BAD REQUEST" with expectedErrorMessage = "Validation Failed"

  Scenario: GET specific flight data by its ID
    Given FTB is up and running and the tests are configured
    When client gets details of Flight id=14364
    Then the API should return valid flight details:
      |flightNumber|departureAirportCode|destinationAirportCode|departureDate|arrivalDate|departureTime|arrivalTime|gate|status |flightCharge|
      | FL123      | LED                | NNV                  |2024-10-20   |2024-10-20 | 08:00       | 11:00     | A12|delayed|199.99      |

  Scenario: DELETE specific flight data by its ID
    Given FTB is up and running and the tests are configured
    When the client sends a DELETE request to delete flight with flightId = 14362
    Then the API should return code = 200 with message = "Deleted Flight by Id=14362" for deleted flight
    And When the client sends a DELETE request to delete flight with flightId = 14362
    Then the API should return expectedStatusCode = "404 NOT_FOUND" with expectedErrorMessage = "Entity not found."