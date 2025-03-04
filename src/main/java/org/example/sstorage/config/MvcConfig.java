package org.example.sstorage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Class for adding static pages.
 *
 * @author UsoltsevI
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * Add static pages.
     *
     * @param registry registry to assist with the registration of simple automated controllers
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/login").setViewName("login");
    }
}