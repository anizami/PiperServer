/**
 * Created by Asra Nizami on 3/18/14.
 */

import static spark.Spark.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.hibernate.Query;
import spark.*;

import java.awt.*;
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



public class PiperServer {

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
                return new Gson().toJson(
                        session.createQuery("from PiperEvent").list());

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
                    return new Gson().toJson(resBody);
                }
                catch (Exception e) {
                    tx.rollback();

                    // HTTP codes in the 5xx range mean that something went wrong on the server,
                    // and it's not necessarily the client's fault.
                    response.status(500);

                    Map<String,Object> resBody = new HashMap<String, Object>();
                    resBody.put("success", false);
                    resBody.put("error", e.getLocalizedMessage());
                    return new Gson().toJson(resBody);

                }
            }
        }
      );
    }

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
}
