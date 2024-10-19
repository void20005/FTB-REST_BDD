A set of notes, references, how-tos and quick instructions for the BDD test automation solution for FTB REST API.

# Getting Started
## Set up your work-station
1. Install [Java v>=11](https://www.oracle.com/java/technologies/downloads/#java11).
2. Install [Maven](https://maven.apache.org/install.html)
3. [Optional] Install [Allure CLI](https://docs.qameta.io/allure-report/#_commandline)

## Set up the project.
1. Clone the project's repository:
````
git clone TODO-NEED-THE-REPO-LINK !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
````
2. Alter local test execution settings
- modify ``src/main/resources/app.properties`` file - provide valid values for the base urls etc

## Project overview
The test automation solution is a Java+Cucumber application which uses Maven as a builder and JUnit as Unit-test framework.
RestAssured is used as special HTTP client tool.
Cucumber is used to support BDD-style tests.
As a result of test execution a plain-text log file and Allure-results are generated.

## Project structure
1. `src/test/java` - actual actions (test steps) and utility code is placed there.
2. `src/test/resource/feature` - Gherkin-based scenarios (feature files) are placed there.
3. `src/main/resources` - contains configuration files including test execution settings(`app.properties`). 
4. `src/main/java` - contains all the code to support the tests, see below.
    - `...config` package contains application properties provider(s)
    - `...model` package contains classes which model DB entities and API request/response's data.

# How-to ...
## ... run the tests locally using IDE.
1. Specify ``lcl`` Spring profile using ``spring.profiles.active`` flag. To do that - go to Edit configurations -> select run configuration -> add the flag to VM Options:
````
 -Dspring.profiles.active=lcl
````
2. Save and run.
3. Note, you could edit the IDE's TestNG-execution template so `-Dspring.profiles.active=lcl` would be added automatically.

## ... run all tests using Maven.
1. CD into the project's root directory. I.e. the project's ``pom.xml`` shall be there.
2. Run the test using Maven's ``clean test`` goals. Note, you do not really need anything else in case of a local run and assuming you do have ``lcl`` profile configured since ``lcl`` is the default one.
````
mvn clean test
````

## ... generate Allure report
As soon as you have your test run completed:
````
mvn allure:serve
````
Note, the tests generate a plain-text log file as well, see ``ftb_bdd.log``


# Technologies
- Java as a programming language. Rationale: one of the most popular languages for test automation, many open-source  tools and libraries are available, easy to find an expert. https://www.java.com/
- [Maven as a building tool. ](https://maven.apache.org/what-is-maven.html)
- [JUnit as a fundamental unit-test framework for the functional tests.](https://testng.org/doc/)
- [RestAssured as a REST API client tool.](https://rest-assured.io/)
- [Owner as a property-access library.](https://eliasnogueira.com/easily-manage-properties-files-in-java-with-owner/)
- [Cucumber as a BDD framework.](https://cucumber.io)
- [Allure as a reporting tool.](https://docs.qameta.io/allure-report/)
- [ModelMapper for Entity-to-DTO transformation.](http://modelmapper.org/getting-started/)
- [Lombok to make Java nicer, more laconic and more readable.](https://projectlombok.org/)
-
