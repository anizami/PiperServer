/**
 * Created by Asra Nizami on 4/3/14.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PiperParser {

    private static String PIPER_URL = "http://webapps.macalester.edu/dailypiper/dailypiper-portal.cfm?expanded=true";

    //TODO: Catch exceptions such as connection timeout, other errors, etc...
    public static List<PiperEvent> grabAndParse(){
        //TODO: Handle case when Jsoup.connect throws exception and doc is null
        Document doc = null;
        try {
            String html = Jsoup.connect(PIPER_URL).get().toString();
            doc = Jsoup.parse(html);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Element> fullElements = doc.select("div.story");
        List<PiperEvent> piperEvents = new LinkedList<PiperEvent>();

        for (Element element : fullElements){
            PiperEvent piperEvent = new PiperEvent();
            String title = element.select("h4").text();
            String body = element.children().not("h4").text();
            piperEvent.setTitle(title);
            piperEvent.setBody(body);

            String[] data = FoodEventUtils.extractData(body);
            piperEvent.setDescription(data[0]);
            piperEvent.setLocation(data[1]);
            piperEvent.setTime(data[2]);
            if (piperEvent.getDescription() != null && FoodEventUtils.isToday(title, body)  ){
                piperEvents.add(piperEvent);
            }
        }
        return piperEvents;
    }
}
