package com.eximbay.okr.config;

import com.eximbay.okr.aop.*;
import ma.glasnost.orika.*;
import ma.glasnost.orika.impl.*;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableAspectJAutoProxy
public class ApplicationConfig implements WebMvcConfigurer {

    @Bean
    AopLogger logger(){
        return new AopLogger();
    }

    @Bean
    MapperFacade mapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .mapNulls(false)
                .useBuiltinConverters(false).build();
        return mapperFactory.getMapperFacade();
    }
}
