package dbad.labs.dbad_lab1.presentation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dbad.labs.dbad_lab1.presentation.di.AppModule;
import org.hibernate.SessionFactory;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());

        CLI app = injector.getInstance(CLI.class);

        try {
            app.start();
        } finally {
            SessionFactory sessionFactory = injector.getInstance(SessionFactory.class);
            if (sessionFactory != null && !sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        }
    }
}