package com.toeic.learn.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(1)
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) {
        Long vocabularyCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM vocabulary", Long.class);

        if (vocabularyCount == null || vocabularyCount == 0) {
            log.info("Database is empty, initializing with data.sql...");
            jdbcTemplate.execute("RUNSCRIPT FROM 'classpath:data.sql'");
            log.info("Loading additional vocabulary data from data-extra.sql...");
            jdbcTemplate.execute("RUNSCRIPT FROM 'classpath:data-extra.sql'");
            log.info("Data initialization completed.");
        } else {
            log.info("Database already contains {} vocabularies, skipping data.sql and data-extra.sql", vocabularyCount);
        }
    }
}
