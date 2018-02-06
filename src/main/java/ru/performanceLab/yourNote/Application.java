package ru.performanceLab.yourNote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import ru.performanceLab.yourNote.repository.NoteRepository;
import ru.performanceLab.yourNote.scope.SessionBean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    @Autowired
    ApplicationContext ctx;

//    @Bean
//    public ViewResolver jspViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setViewClass(JstlView.class);
//        resolver.setPrefix("/WEB-INF/");
//        resolver.setSuffix(".jsp");
//        return resolver;
//    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    public UserRepository userRepository() {
//        return new UserRepository();
//    }
//
//    @Bean
//    public NoteRepository noteRepository() {
//        return new NoteRepository();
//    }

    @Bean
    @Scope(scopeName = "customSession")
    public SessionBean sessionBean() {
        return new SessionBean();
    }
    @Bean
    public NoteController noteController() {
        return new NoteController() {
            @Override
            protected SessionBean getSession() {
                return sessionBean();
            }
        };
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
