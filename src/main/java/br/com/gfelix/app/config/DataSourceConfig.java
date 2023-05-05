package br.com.gfelix.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Primary
    @Bean
    @Qualifier("mySqlDatasource")
    @ConfigurationProperties(prefix = "app.database")
    public DataSource springDataSource() {
        return DataSourceBuilder.create().build();
    }
}
