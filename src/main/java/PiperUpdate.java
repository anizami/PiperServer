import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Created by anizami on 4/23/14.
 */
import org.hibernate.Query;
import spark.*;

import java.awt.*;
import java.util.Calendar;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;
import java.util.HashMap;
import java.util.Map;

public class PiperUpdate {

    private static SessionFactory createSessionFactory() {
        //configure() uses the mappings and properties specified in an application resource named hibernate.cfg.xml
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

        // Let's add code to delete all items from the database first
//        Transaction tx = session.beginTransaction();
        String stringquery = "DELETE FROM PiperEvent";
        Query query = session.createQuery(stringquery);
        query.executeUpdate();
        Calendar c = Calendar.getInstance();
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        System.out.println("The day of the week is " + dayOfWeek);
        if (dayOfWeek == 0 || dayOfWeek == 7) {
            List<PiperEvent> eventsList = JSoupParse.grabAndParse();
            for (PiperEvent event: eventsList) {
                Transaction tx = session.beginTransaction();
                try{
                    session.save(event);
                    tx.commit();
                }
                catch (Exception e) {
                    tx.rollback();

                }
            }
        }
//        tx.commit();
      }
}
