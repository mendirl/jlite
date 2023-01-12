package io.mendirl.jhlite.cucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;
import io.mendirl.jhlite.ComponentTest;

@ComponentTest
@RunWith(Cucumber.class)
@CucumberOptions(
  glue = { "io.mendirl.jhlite", "tech.jhipster.lite.module.infrastructure.primary" },
  plugin = {
    "pretty", "json:target/cucumber/cucumber.json", "html:target/cucumber/cucumber.htm", "junit:target/cucumber/TEST-cucumber.xml",
  },
  features = "src/test/features"
)
public class CucumberTest {}
