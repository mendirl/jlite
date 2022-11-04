package io.mendirl.jlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import io.mendirl.jlite.common.domain.Generated;

@SpringBootApplication
@Generated(reason = "Not testing logs")
public class JliteApplicationApp {

  private static final Logger log = LoggerFactory.getLogger(JliteApplicationApp.class);

  public static void main(String[] args) {
    Environment env = SpringApplication.run(JliteApplicationApp.class, args).getEnvironment();

    if (log.isInfoEnabled()) {
      log.info(ApplicationStartupTraces.of(env));
    }
  }
}
