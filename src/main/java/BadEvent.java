import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Asra Nizami on 4/3/14.
 */
public class BadEvent {
    private boolean hasFood;
    private String title = "";
    private String time = "";
    private String place = "";
    private String description = "";
    //TODO: Deal w words like 'teacher' erroneously returning TRUE because of 'tea' substring and shit like that.
    private transient String foodRegex = "[Bb]reakfast|[Ll]unch|[Dd]inner|[Ss]nack|[Rr]efreshments|[Pp]ie|[Cc](ake|offee|hocolate|andy)|[Ii]ce cream]|[Tt]ea|[Dd]rinks|[Pp]izza]";
    //TODO: Add accommodation for room numnbers like "HUM204" or "OLRI150"
    private String placeRegex = "[Oo]lin[ -][Rr]ice|[Cc]ampus[ -][Cc]enter|[Cc]arnegie|[Oo]ld [Mm]ain|[Mm]arkim [Hh]all|[Kk]agin|GSRC";
    //TODO: Add RegEx for times written w only 1 digit (e.g. '5 p.m.' or '1 o'clock')
    private String timeRegex = "[Nn]oon|[0-9]:[0-9][0-9]";

    public BadEvent (String title, String body){

        this.title = title;
        String sentenceEndRegex = ("(?<=[\\.\\!\\?]) (?=[A-Z0-9])");
        String sentences[] = body.split(sentenceEndRegex);
        Pattern FOOD_PATTERN = Pattern.compile(foodRegex);
        Pattern PLACE_PATTERN = Pattern.compile(placeRegex);
        Pattern TIME_PATTERN = Pattern.compile(timeRegex);

        for(String sentence : sentences){
            Matcher f = FOOD_PATTERN.matcher(sentence);
            Matcher p = PLACE_PATTERN.matcher(sentence);
            Matcher t = TIME_PATTERN.matcher(sentence);
            if (f.find()){
                this.hasFood = true;
                this.description = f.group(0);
            }if (p.find()){
                this.place = p.group(0);
            }if (t.find()){
                this.time = t.group(0);
            }
        }

    }

    public boolean getHasFood() { return hasFood; }
    public String getTitle(){
        return title;
    }
    public String getTime(){
        return time;
    }
    public String getPlace(){
        return place;
    }
    public String getDescription(){
        return description;
    }


}
