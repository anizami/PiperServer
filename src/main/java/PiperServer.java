/**
 * Created by Asra Nizami on 3/18/14.
 */

import static spark.Spark.*;
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

public class PiperServer {
    /*
    Credits to http://www.taywils.me/2013/11/05/javasparkframeworktutorial.html for a Java with Spark tutorial
    https://github.com/pcantrell/jokes-spark for a Hibernate tutorial and to
    https://gist.github.com/Fitzsimmons/2490382 for deploying server on Heroku tutorial
     */

    public static void main(String[] args) {
        setPort(Integer.parseInt(System.getenv("PORT")));
        final SessionFactory sessionFactory = createSessionFactory();

        before(new Filter() {
            @Override
            public void handle(Request req, Response res) {
                res.type("application/json");
            }
        });

        get(new Route("/getPiper") {
            @Override
            public Object handle(Request request, Response response) {
                Session session = sessionFactory.openSession();
                String jsonString = new Gson().toJson(
                        session.createQuery("from PiperEvent").list());
                session.close();
                return jsonString;
            }
        });

        post(new Route("/addEvent") {
            @Override
            public Object handle(Request request, Response response) {
                Session session = sessionFactory.openSession();
                Transaction tx = session.beginTransaction();
                try {
                    PiperEvent event = new PiperEvent();
                    event.setTime(request.queryParams("time"));
                    event.setTitle(request.queryParams("title"));
                    event.setLocation(request.queryParams("location"));
                    event.setDescription(request.queryParams("description"));
                    session.save(event);
                    tx.commit();
                    Map<String,Object> resBody = new HashMap<String, Object>();
                    resBody.put("success", true);
                    resBody.put("PiperEvent", event);
                    String jsonObject = new Gson().toJson(resBody);
                    session.close();
                    return jsonObject;
                }
                catch (Exception e) {
                    tx.rollback();

                    // HTTP codes in the 5xx range mean that something went wrong on the server,
                    // and it's not necessarily the client's fault.
                    response.status(500);

                    Map<String,Object> resBody = new HashMap<String, Object>();
                    resBody.put("success", false);
                    resBody.put("error", e.getLocalizedMessage());
                    String jsonObject = new Gson().toJson(resBody);
                    session.close();
                    return jsonObject;
                }
            }
        }
      );
    }

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration().configure();
        return configuration.buildSessionFactory(
                new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build());
    }
}
