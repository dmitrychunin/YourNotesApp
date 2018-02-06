package ru.performanceLab.yourNote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import ru.performanceLab.yourNote.scope.SessionBean;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Scope(scopeName = "customSession")
    public SessionBean sessionBean(String userName) {
        return new SessionBean(userName);
    }

    @Bean
    public NoteController noteController() {
        return new NoteController() {
            @Override
            protected SessionBean getSession(String userName) {
                return sessionBean(userName);
            }
        };
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
