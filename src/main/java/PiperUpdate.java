import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by anizami on 4/23/14.
 */
import org.hibernate.Query;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PiperUpdate {

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration().configure();
//        if(System.getenv("DATABASE_URL") != null)
//            configuration.setProperty("hibernate.connection.url", System.getenv("DATABASE_URL"));
        return configuration.buildSessionFactory(
                new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build());
    }

    public static void main(String[] args) {
        final SessionFactory sessionFactory = createSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        String stringQuery = "DELETE FROM PiperEvent";
        Query query = session.createQuery(stringQuery);
        query.executeUpdate();
        tx.commit();
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek != 1 && dayOfWeek != 7) {
            List<PiperEvent> eventsList = PiperParser.grabAndParse();
            tx = session.beginTransaction();
            for (PiperEvent event: eventsList) {
                session.save(event);
            }
            try {
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
            }
        }
        session.close();
    }
}
