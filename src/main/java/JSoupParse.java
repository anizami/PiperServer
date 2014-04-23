/**
 * Created by Asra Nizami on 4/3/14.
 */

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JSoupParse {

    private static String PIPER_URL = "http://webapps.macalester.edu/dailypiper/dailypiper-portal.cfm?expanded=true";
    private static List<PiperText> piperTexts;


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
        piperTexts = new LinkedList<PiperText>();
        List<Element> fullElements = doc.select("div.story");

        for (Element element : fullElements){

            PiperText piperText = new PiperText();

            String title = element.select("h4").text();
            String body = element.children().not("h4").text();

            //TODO: deal with piperTexts w null bodies or titles!
            piperText.setTitle(title);
            piperText.setBody(body);

            piperTexts.add(piperText);
        }

        List<PiperEvent> piperEvents = new LinkedList<PiperEvent>();


        for (PiperText piperText:piperTexts ){
            String title = piperText.getTitle();
            String body = piperText.getBody();
            String[] data = Utils.extractData(body);
            PiperEvent piperEvent = new PiperEvent();

            piperEvent.setTitle(title);
            piperEvent.setDescription(data[0]);
            piperEvent.setLocation(data[1]);
            piperEvent.setTime(data[2]);

            if (piperEvent.getDescription() != null){
                piperEvents.add(piperEvent);
            }

        }

        return piperEvents;

    }


}
