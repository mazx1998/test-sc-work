package com.app.config;

import com.app.model.converters.UserToJwtUserConverter;
import com.app.model.converters.registerUserDtoToUserConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Максим Зеленский
 */

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new registerUserDtoToUserConverter());
        registry.addConverter(new UserToJwtUserConverter());
    }
}
