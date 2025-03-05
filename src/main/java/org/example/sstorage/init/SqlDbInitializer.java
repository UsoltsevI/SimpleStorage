package org.example.sstorage.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

/**
 * This class is responsible for loading tables into the database.
 * Also, the definition of functions and procedures on the database side.
 *
 * @author UsoltsevI
 */
@Component
public class SqlDbInitializer implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOGGER = LoggerFactory.getLogger(SqlDbInitializer.class);
    private final JdbcTemplate jdbcTemplate;
    private final Resource schemaScript;

    /**
     * Upload jdbcTemplate and sql scripts.
     *
     * @param jdbcTemplate    Spring JdbcTemplate for communicating with the database
     * @param schemaScript    Resource with SQL script or creating tables
     */
    @Autowired
    public SqlDbInitializer(JdbcTemplate jdbcTemplate
            , @Value("classpath:schema.sql") Resource schemaScript) {
        this.jdbcTemplate = jdbcTemplate;
        this.schemaScript = schemaScript;
    }

    /**
     * Upload scripts to the database.
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        populateDatabase();
    }

    /**
     * Upload tables to the database.
     */
    private void populateDatabase() {
        DatabasePopulator databasePopulator = new ResourceDatabasePopulator(schemaScript);
        try {
            databasePopulator.populate(jdbcTemplate.getDataSource().getConnection());
            LOGGER.info("Database schema initialized from schema.sql");
        } catch (Exception e) {
            LOGGER.error("Failed to initialize database schema", e);
        }
    }
}
