package com.eximbay.okr.config;

import com.eximbay.okr.constant.AppConst;
import com.eximbay.okr.listener.AuditorAwareImpl;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.datatables.repository.DataTablesRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableAspectJAutoProxy
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableJpaRepositories(repositoryFactoryBeanClass = DataTablesRepositoryFactoryBean.class, basePackages = "com.eximbay.okr.repository")
@EnableCaching
public class ApplicationConfig implements WebMvcConfigurer {

    @Value("${application.file.imagesPath}")
    String imagePath;

    @Bean
    MapperFacade mapper() {
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder()
                .mapNulls(false)
                .useBuiltinConverters(false).build();
        return mapperFactory.getMapperFacade();
    }

    @Bean
    public AuditorAwareImpl auditorAware(){
        return new AuditorAwareImpl();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path imageLocation = Paths.get(imagePath);
        registry.addResourceHandler("/image/**")
                .addResourceLocations("file:///" + imageLocation.toAbsolutePath()+"/" + AppConst.IMAGE_SUB_PATH);

        registry.addResourceHandler("/avatar/**")
                .addResourceLocations("file:///" + imageLocation.toAbsolutePath()+"/" + AppConst.AVATAR_SUB_PATH);
    }
}
