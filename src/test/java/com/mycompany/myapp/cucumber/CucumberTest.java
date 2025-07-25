package com.mycompany.myapp.cucumber;

import static io.cucumber.junit.platform.engine.Constants.FILTER_TAGS_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;
import static io.cucumber.junit.platform.engine.Constants.PLUGIN_PROPERTY_NAME;

import com.mycompany.myapp.ComponentTest;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectDirectories;
import org.junit.platform.suite.api.Suite;

@Suite(failIfNoTests = false)
@ComponentTest
@IncludeEngines("cucumber")
@SuppressWarnings("java:S2187")
@SelectDirectories("src/test/features")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "com.mycompany.myapp")
@ConfigurationParameter(
  key = PLUGIN_PROPERTY_NAME,
  value = "pretty, json:target/cucumber/cucumber.json, html:target/cucumber/cucumber.htm, junit:target/cucumber/TEST-cucumber.xml"
)
@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME, value = "not @disabled")
public class CucumberTest {}
