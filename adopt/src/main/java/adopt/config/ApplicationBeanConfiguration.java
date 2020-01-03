package adopt.config;

import adopt.utils.factories.adoption.AdoptionFactory;
import adopt.utils.factories.adoption.AdoptionFactoryImpl;
import adopt.utils.mapper.ModelMapperCustomImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationBeanConfiguration {

    private static ModelMapperCustomImpl mapper;

    static {
        mapper = new ModelMapperCustomImpl();
    }
    @Bean
    public ModelMapperCustomImpl modelMapper() {
        return mapper;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AdoptionFactory adoptionFactory() {
        return new AdoptionFactoryImpl();
    }
}
