package rePashion.server.domain.product.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import rePashion.server.global.common.config.JpaQueryFactoryConfig;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({ProductFilterRepository.class, JpaQueryFactoryConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductFilterRepositoryTest {

    @Test
    public void foo(){}
}