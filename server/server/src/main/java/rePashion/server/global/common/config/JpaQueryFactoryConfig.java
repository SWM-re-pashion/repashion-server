package rePashion.server.global.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
public class JpaQueryFactoryConfig {

    @PersistenceContext
    private EntityManager em;

    @Bean
    JPAQueryFactory jpaQueryFactory(){
        return new JPAQueryFactory(em);
    }
}
