package io.mendirl.jhlite.technical.infrastructure.secondary.postgresql;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages =  { "io.mendirl.jhlite" }, enableDefaultTransactions = false)
public class DatabaseConfiguration {}
