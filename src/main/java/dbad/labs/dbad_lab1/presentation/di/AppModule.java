package dbad.labs.dbad_lab1.presentation.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import dbad.labs.dbad_lab1.data.utils.HibernateUtil;
import org.hibernate.SessionFactory;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    SessionFactory provideSessionFactory() {
        return HibernateUtil.sessionFactory;
    }
}