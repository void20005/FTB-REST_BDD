package com.m2p.at.ftbtests.api.rest.bdd.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.m2p.at.ftbtests.api.rest.bdd.config.AppConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import org.aeonbits.owner.ConfigCache;

/**
 * Ugly but why not for a prototype.
 */
public class BeansFactory {

    public static ObjectMapper provideObjectMapper() {
        var om = new ObjectMapper();
        om.registerModule(new JavaTimeModule());
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        om.setDateFormat(new StdDateFormat().withColonInTimeZone(true));

        return om;
    }

    public static AppConfig provideAppConfig() {
        return ConfigCache.getOrCreate(AppConfig.class);
    }

    public static AllureRestAssured provideAllureRestAssured() {
        return new AllureRestAssured();
    }
}
