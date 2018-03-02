package com.example.bookpub.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.core.Ordered;

import java.time.Duration;

public class MyServletWebServerFactoryCustomizer implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>, Ordered {

    private final ServerProperties serverProperties;

    public MyServletWebServerFactoryCustomizer(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        serverProperties.getServlet().getSession().setTimeout(Duration.ofMinutes(1));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
