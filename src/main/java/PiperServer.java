/**
 * Created by Asra Nizami on 3/18/14.
 */

import static spark.Spark.*;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

import spark.Request;
import spark.Response;
import spark.Route;

public class PiperServer {

    private PiperParser piperParser = new PiperParser();

    public static void main(String[] args) {
        setPort(Integer.parseInt(System.getenv("PORT")));

        get(new Route("/getPiper") {
            @Override
            public Object handle(Request request, Response response) {
                PiperParser.parseThroughPiper();
                JSONObject json = new JSONObject();
                List<String> eventsList = PiperParser.getEventsList();
                Integer i = 0;
                for (String event : eventsList) {
                    String id = "" + i;
                    try {
                        json.put(id, event);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    i++;
                }
                return json;

            }
        });
    }


}
