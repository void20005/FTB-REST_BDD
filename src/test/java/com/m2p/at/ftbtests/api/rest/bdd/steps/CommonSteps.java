package com.m2p.at.ftbtests.api.rest.bdd.steps;

import com.m2p.at.ftbtests.api.rest.bdd.config.AppConfig;
import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.config.LogConfig;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigCache;
import org.apache.http.HttpHeaders;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.basic;
import static io.restassured.RestAssured.given;

/**
 * A step-definition container to contain common steps like "check health" etc
 */
public class CommonSteps {

    private final AppConfig appConfig = ConfigCache.getOrCreate(AppConfig.class);

    @Given("FTB is up and running and the tests are configured")
    public void checkHealthAndSetup() {
        doBasicSuitePreconditionSetup();
    }

    private void doBasicSuitePreconditionSetup() {
        RestAssured.config = RestAssured.config().logConfig(LogConfig
                .logConfig()
                .enablePrettyPrinting(true)
                .enableLoggingOfRequestAndResponseIfValidationFails()
                .blacklistHeader(HttpHeaders.AUTHORIZATION));

        RestAssured.authentication = basic(appConfig.admin(), appConfig.password());
        RestAssured.authentication = basic("john", "john123");
    }

}
