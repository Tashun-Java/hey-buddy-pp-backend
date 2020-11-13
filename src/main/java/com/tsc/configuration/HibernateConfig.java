package com.tsc.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Objects;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScans(value = {
        @ComponentScan("com.tsc.dao"),
        @ComponentScan("com.tsc.service")})
public class HibernateConfig {
    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean getSessionFactoryBean() {
        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        Properties props = new Properties();
        // Setting JDBC properties
//        props.put(DIALECT, Objects.requireNonNull(env.getProperty("spring.database-platform")));
//        props.put(DRIVER, Objects.requireNonNull(env.getProperty("spring.driver")));
        props.put(URL, Objects.requireNonNull(env.getProperty("spring.datasource.url")));
        props.put(USER, Objects.requireNonNull(env.getProperty("spring.datasource.username")));
        props.put(PASS, Objects.requireNonNull(env.getProperty("spring.datasource.password")));

        // Setting Hibernate properties
        props.put(SHOW_SQL, Objects.requireNonNull(env.getProperty("hibernate.show_sql")));
        props.put(HBM2DDL_AUTO, Objects.requireNonNull(env.getProperty("hibernate.hbm2ddl.auto")));

        // Setting C3P0 properties
        props.put(C3P0_MIN_SIZE, Objects.requireNonNull(env.getProperty("hibernate.c3p0.min_size")));
        props.put(C3P0_MAX_SIZE, Objects.requireNonNull(env.getProperty("hibernate.c3p0.max_size")));
        props.put(C3P0_ACQUIRE_INCREMENT,
                Objects.requireNonNull(env.getProperty("hibernate.c3p0.acquire_increment")));
        props.put(C3P0_TIMEOUT, Objects.requireNonNull(env.getProperty("hibernate.c3p0.timeout")));
        props.put(C3P0_MAX_STATEMENTS, Objects.requireNonNull(env.getProperty("hibernate.c3p0.max_statements")));

        factoryBean.setHibernateProperties(props);
        factoryBean.setPackagesToScan("net.codegen.model");

        return factoryBean;
    }

    @Bean
    public HibernateTransactionManager getTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(getSessionFactoryBean().getObject());
        return transactionManager;
    }


}
