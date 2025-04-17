package space.config;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import space.entity.Space;
import space.entity.SpaceType;
import space.entity.User;
import space.service.auth.EncryptionService;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@ComponentScan(basePackages = "space")
public class JpaConfig {
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/cowork");
        dataSource.setUsername("postgres");
        dataSource.setPassword("yaoi");
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("space.entity");
        factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
        jpaProperties.put("hibernate.show_sql", "true");
        jpaProperties.put("hibernate.format_sql", "true");

        factoryBean.setJpaProperties(jpaProperties);

        return factoryBean;
    }

    @Bean
    public EntityManagerFactory emf() {
        EntityManagerFactory emf = entityManagerFactoryBean().getNativeEntityManagerFactory();
        runMigrations(emf.createEntityManager());
        return emf;
    }

    private void runMigrations(EntityManager em) {
        em.getTransaction().begin();
        em.persist(new User(0,
                "Admin1",
                "admin@gmail.com",
                EncryptionService.encode("12345"),
                User.Role.ADMIN_ROLE));
        User user = new User(
                0,
                "John Wick",
                "johnwick@gmail.com",
                EncryptionService.encode("12345"),
                User.Role.USER_ROLE);
        em.persist(user);
        SpaceType type1 = new SpaceType(0,
                "Private Room");
        SpaceType type2 = new SpaceType(0,
                "Conference");
        SpaceType type3 = new SpaceType(0,
                "Shared Workplace");
        SpaceType type4 = new SpaceType(0,
                "Cubicles");
        SpaceType type5 = new SpaceType(0,
                "Chill Zone");

        em.persist(type1);
        em.persist(type2);
        em.persist(type3);
        em.persist(type4);
        em.persist(type5);

        Space space1 = new Space(0,
                "California Vibes",
                1000,
                type5);
        Space space2 = new Space(0,
                "Apple Inc",
                50000,
                type2);
        Space space3 = new Space(0,
                "Silicon Valley",
                15000,
                type4);
        Space space4 = new Space(0,
                "Typical Workplace",
                20050,
                type5);
        Space space5 = new Space(0,
                "Highway to Paradise",
                800,
                type1);

        em.persist(space1);
        em.persist(space2);
        em.persist(space3);
        em.persist(space4);
        em.persist(space5);
        em.flush();
        em.getTransaction().commit();
        em.close();
    }
}
