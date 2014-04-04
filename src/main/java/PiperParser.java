/**
 * Created by Asra Nizami on 3/18/14.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class PiperParser {

    private static EventUtility s = new EventUtility();
    private static List<PiperEvent> finalEventsList = new ArrayList<PiperEvent>();


    public static void parseThroughPiper() {


        //Does this work???????

        List<String> eventList = new ArrayList<String>();
        String url = "http://webapps.macalester.edu/dailypiper/dailypiper-portal.cfm?expanded=true";
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fullPage = doc.toString();
        String[] possibleEventArray = s.stringSegment(fullPage);

        for (String possibleEvent : possibleEventArray) {
            boolean isStory = s.isStory(possibleEvent);
            if (isStory) {
                eventList.add(possibleEvent);
            }
        }
        for (String event : eventList){

//            System.out.println("-----------------NEW STORY!-----------------");
            String cleanedUpEvent = s.cleanup(event);
//            System.out.println(cleanedUpEvent);
            PiperEvent piperEvent = new PiperEvent();
            piperEvent.setTime("Unavailable");
            piperEvent.setLocation("Unavailable");
            piperEvent.setDescription(cleanedUpEvent);
            piperEvent.setTitle("Title");
            finalEventsList.add(piperEvent);
        }
    }

    public static List<PiperEvent> getEventsList() {
        return finalEventsList;
    }

}


