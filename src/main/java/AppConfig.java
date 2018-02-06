import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import ru.performanceLab.yourNote.scope.SessionBeanFactoryPostProcessor;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories("ru.performanceLab.yourNote.repository")
public class AppConfig {

//    @Bean
//    public EntityManagerFactory entityManagerFactory() {
//        return new LocalContainerEntityManagerFactoryBean();
//    }
//    @Bean
//    public static BeanFactoryPostProcessor beanFactoryPostProcessor() {
//        return new SessionBeanFactoryPostProcessor();
//    }
}
