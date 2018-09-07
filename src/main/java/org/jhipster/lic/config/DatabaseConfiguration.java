package org.jhipster.lic.config;

import org.jhipster.lic.domain.CloudStore;
import org.jhipster.lic.security.DataBlocDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("org.jhipster.lic.repository")
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
@EnableTransactionManagement
public class DatabaseConfiguration {

    private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

    private final Environment env;

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties defaultDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSource defaultDataSource() {
        return defaultDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customerEntityManagerFactory(
        EntityManagerFactoryBuilder builder) {
        return builder
            .dataSource(defaultDataSource())
            .packages(CloudStore.class)
            .persistenceUnit("default")
            .build();
    }

//    @Primary
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setDatabase(Database.SQL_SERVER);
//        vendorAdapter.setGenerateDdl(true);
//        vendorAdapter.setShowSql(true);
//
//        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//        factory.setJpaVendorAdapter(vendorAdapter);
//        factory.setPackagesToScan(getClass().getPackage().getName());
//        factory.setDataSource(dataSource());
//        factory.setJpaProperties(jpaProperties());
//
//        return factory;
//    }

    @Primary
    @Bean(name = "transactionManager")
    public JpaTransactionManager db2TransactionManager(@Qualifier("entityManagerFactory") final EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    public DatabaseConfiguration(Environment env) {
        this.env = env;
    }

//    /**
//     * Open the TCP port for the H2 database, so it is available remotely.
//     *
//     * @return the H2 database TCP server
//     * @throws SQLException if the server failed to start
//     */
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    @Primary
//    @ConfigurationProperties("spring.datasource")
//    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
//    public Server h2TCPServer() throws SQLException {
//        return Server.createTcpServer("-tcp","-tcpAllowOthers");
//    }

//    /**
//     * Open the TCP port for the H2 database, so it is available remotely.
//     *
//     * @return the H2 database TCP server
//     * @throws SQLException if the server failed to start
//     */
//    @Bean(initMethod = "start", destroyMethod = "stop")
//    @Profile(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)
//    public Object h2TCPServer() throws SQLException {
//        log.debug("Starting H2 database");
//        return H2ConfigurationHelper.createServer();
//    }

}
