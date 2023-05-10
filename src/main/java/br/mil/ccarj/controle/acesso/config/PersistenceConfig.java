package br.mil.ccarj.controle.acesso.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfig {

    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    @Profile({ "dev", "debug", "test" })
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean
    @Profile({ "hom", "prod" })
    public DataSource jndiDataSource() {
        JndiDataSourceLookup dataSourceLookup = new JndiDataSourceLookup();
        return dataSourceLookup.getDataSource(primary().getJndiName());
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public JndiPropertyHolder primary() {
        return new JndiPropertyHolder();
    }

    private static class JndiPropertyHolder {

        private String jndiName;

        public String getJndiName() {
            return jndiName;
        }

        public void setJndiName(String jndiName) {
            this.jndiName = jndiName;
        }
    }
}