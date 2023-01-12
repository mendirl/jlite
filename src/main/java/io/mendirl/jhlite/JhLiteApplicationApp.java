package io.mendirl.jhlite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import io.mendirl.jhlite.common.domain.Generated;

@SpringBootApplication
@Generated(reason = "Not testing logs")
public class JhLiteApplicationApp {

  private static final Logger log = LoggerFactory.getLogger(JhLiteApplicationApp.class);

  public static void main(String[] args) {
    Environment env = SpringApplication.run(JhLiteApplicationApp.class, args).getEnvironment();

    if (log.isInfoEnabled()) {
      log.info(ApplicationStartupTraces.of(env));
    }
  }
}
