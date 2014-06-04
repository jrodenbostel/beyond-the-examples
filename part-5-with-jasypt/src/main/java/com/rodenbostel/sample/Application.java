package com.rodenbostel.sample;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.properties.EncryptableProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${spring.datasource.driverClassName}")
    private String databaseDriverClassName;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String databaseUsername;

    private String databasePassword;

    @Bean
    public DataSource datasource() throws IOException {
        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName(databaseDriverClassName);
        ds.setUrl(datasourceUrl);
        ds.setUsername(databaseUsername);
        ds.setPassword(getSecurePassword());

        return ds;
    }

//    http://www.jasypt.org/spring31.html
//    http://www.jasypt.org/springsecurity.html
//    http://www.jasypt.org/encrypting-configuration.html
//    http://sourceforge.net/projects/jasypt/files/jasypt/jasypt%201.9.2/
//    Justins-MacBook-Pro:bin justin$ ./encrypt.sh input="password" password=testtest
//
//    ----ENVIRONMENT-----------------
//
//    Runtime: Oracle Corporation Java HotSpot(TM) 64-Bit Server VM 24.51-b03
//
//
//
//    ----ARGUMENTS-------------------
//
//    input: password
//    password: testtest
//
//
//
//    ----OUTPUT----------------------
//
//    xpPrNtXz+SQmTYB0WQrc+2T8ZTubofox
    private String getSecurePassword() throws IOException {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(System.getProperty("tadirect.jasypt.key"));
        Properties props = new EncryptableProperties(encryptor);
        props.load(this.getClass().getClassLoader().getResourceAsStream("application.properties"));
        return props.getProperty("datasource.password");
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.US);
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

}