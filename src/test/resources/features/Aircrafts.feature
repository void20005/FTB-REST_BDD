Feature: Testing FTB REST API AIRCRAFTS resource
  Clients should be able to READ/CREATE/UPDATE/DELETE an aircraft record.

  Scenario: Get specific aircraft data by its ID
    Given FTB is up and running and the tests are configured
    When client gets details of Aircraft id=1010
    Then aircraft data to be manufacturer='Boeing' and model='777-8' and number of seats=100

  Scenario: Create an aircraft with number of seats null
    Given FTB is up and running and the tests are configured
    When client tries to create an Aircraft having manufacturer='Boeing' and model='777-8'
    Then aircraft data to be manufacturer='Boeing' and model='777-8'

  Scenario: Check if number of seats value of Aircraft is null by its ID
    Given FTB is up and running and the tests are configured
    When client gets details of Aircraft id=1010
    Then aircraft number of seats is null

  Scenario: Get specific aircraft data by its ID
    Given FTB is up and running and the tests are configured
    When client gets details of Aircraft id=1010
    Then aircraft data to be manufacturer='Boeing' and model='777-8' and number of seats=100

  Scenario: Create an aircraft with number of seats null
    Given FTB is up and running and the tests are configured
    When client tries to create an Aircraft having manufacturer='Boeing' and model='777-8'
    Then aircraft data to be manufacturer='Boeing' and model='777-8'

  Scenario: Check if number of seats value of Aircraft is null by its ID
    Given FTB is up and running and the tests are configured
    When client gets details of Aircraft id=1010
    Then aircraft number of seats is null

  Scenario: Check if number of seats value of Aircraft is 1
    Given FTB is up and running and the tests are configured
    When client gets details of Aircraft id=1408
    Then aircraft data to be manufacturer='Boeing' and model='777-8' and number of seats=1

  Scenario: create an aircraft with the maximum string length of the manufacturer field
    Given FTB is up and running and the tests are configured
    When client tries to create an Aircraft having manufacturer='11Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta.' and model='777-8' and number of seats=100
    Then aircraft data to be manufacturer='11Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer nec odio. Praesent libero. Sed cursus ante dapibus diam. Sed nisi. Nulla quis sem at nibh elementum imperdiet. Duis sagittis ipsum. Praesent mauris. Fusce nec tellus sed augue semper porta.' and model='777-8' and number of seats=100


  Scenario Outline: Create an aircrafts with examples
    Given FTB is up and running and the tests are configured
    When client tries to create an Aircraft having manufacturer="<manufacturer>" and model="<model>" and number of seats=<number of seats>
    Then aircraft data to be manufacturer="<manufacturer>" and model="<model>" and number of seats=<number of seats>

    Examples:
      |manufacturer      |model       |number of seats|
      |Boeing            |888 e        |11             |
      |Airbus            |310 e        |12             |
      |bombarier         |213  e       |13             |




