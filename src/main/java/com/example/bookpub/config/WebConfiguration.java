package com.example.bookpub.config;

import com.example.bookpub.formatter.BookFormatter;
import com.example.bookpub.repository.BookRepository;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.filters.RemoteIpFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.io.File;
import java.time.Duration;

@Configuration
@PropertySource("classpath:/tomcat.https.properties")
@EnableConfigurationProperties(WebConfiguration.TomcatSslConnectorProperties.class)
public class WebConfiguration implements WebMvcConfigurer {

    private Log logger = LogFactory.getLog(WebConfiguration.class);

    @Autowired
    private BookRepository bookRepository;

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new LocaleChangeInterceptor();
    }

//    @Bean
//    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
//        return new ByteArrayHttpMessageConverter();
//    }

    @Bean
    public ServletWebServerFactory servletContainer
            (TomcatSslConnectorProperties properties) {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(createSslConnector(properties));
        return tomcat;
    }

    private Connector createSslConnector(TomcatSslConnectorProperties properties) {
        Connector connector = new Connector();
        properties.configureConnector(connector);
        return connector;
    }

    @Bean
    public MyServletWebServerFactoryCustomizer myServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
        serverProperties.getServlet().getSession().setTimeout(Duration.ofMinutes(1));
        return new MyServletWebServerFactoryCustomizer(serverProperties);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        logger.info("addFormatter: BookFormatter");
        registry.addFormatter(new BookFormatter(bookRepository));
    }

//    @Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
////        converters.clear();
//        converters.add(byteArrayHttpMessageConverter());
//        converters.add(mappingJackson2HttpMessageConverter());
//    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

        configurer.setUseSuffixPatternMatch(false);
        configurer.setUseTrailingSlashMatch(true);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/internal/**").addResourceLocations("classpath:/");

    }

    @ConfigurationProperties(prefix = "custom.tomcat.https")
    public static class TomcatSslConnectorProperties {

        private Integer port;
        private Boolean ssl = true;
        private Boolean secure = true;
        private String scheme = "https";
        private File keystore;
        private String keystorePassword;


        public void configureConnector(Connector connector) {
            if (port != null) {
                connector.setPort(port);
            }
            if (secure != null) {
                connector.setSecure(secure);
            }
            if (scheme != null) {
                connector.setScheme(scheme);
            }
            if (ssl != null) {
                connector.setProperty("SSLEnabled", ssl.toString());
            }
            if (keystore != null && keystore.exists()) {
                connector.setProperty("keystoreFile",keystore.getAbsolutePath());
            }
            if (keystorePassword != null) {
                connector.setProperty("keystorePassword", keystorePassword);
            }
        }

        public Integer getPort() {
            return port;
        }

        public void setPort(Integer port) {
            this.port = port;
        }

        public Boolean getSsl() {
            return ssl;
        }

        public void setSsl(Boolean ssl) {
            this.ssl = ssl;
        }

        public Boolean getSecure() {
            return secure;
        }

        public void setSecure(Boolean secure) {
            this.secure = secure;
        }

        public String getScheme() {
            return scheme;
        }

        public void setScheme(String scheme) {
            this.scheme = scheme;
        }

        public File getKeystore() {
            return keystore;
        }

        public void setKeystore(File keystore) {
            this.keystore = keystore;
        }

        public String getKeystorePassword() {
            return keystorePassword;
        }

        public void setKeystorePassword(String keystorePassword) {
            this.keystorePassword = keystorePassword;
        }
    }


}
