/**
 * Created by anizami on 4/23/14.
 */

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    static String[] extractData(String input){

        // 1:Food 2:place 3:time
        String[] output = new String[3];

        //TODO: Deal w words like 'teacher' erroneously returning TRUE because of 'tea' substring and shit like that.
        //TODO: singularity & plurality
        String foodRegex = "(?<=(^| ))([Bb]reakfast|[Ll]unch|[Dd]inner|[Ss]nack[s]|[Rr]efreshments|[Pp]ie|[Cc](ake|offee|hocolate|andy)|[Ii]ce cream]|[Tt]ea|[Dd]rinks|[Pp]izza])(?=($| |\\.|\\!|\\?))";
        //TODO: Add accommodation for room numbers like "HUM204" or "OLRI150"
        String placeRegex = "(?<=(^| ))([Oo]lin[ -][Rr]ice|[Cc]ampus[ -][Cc]enter|[Cc]arnegie|[Oo]ld [Mm]ain|[Mm]arkim [Hh]all|[Kk]agin|GSRC)(?=($| |\\.|\\!|\\?))";
        //TODO: Add RegEx for times written w only 1 digit (e.g. '5 p.m.' or '1 o'clock')
        //TODO: If there are multiple times listed in the event, find a way to pick one!
        String timeRegex = "(?<=(^| ))([Nn]oon|[0-9]:[0-9][0-9])(?=($| |\\.|\\!|\\?))";
        //TODO: RSVP?

        String sentenceEndRegex = ("(?<=[\\.\\!\\?]) (?=[A-Z0-9])");
        String sentences[] = input.split(sentenceEndRegex);
        Pattern FOOD_PATTERN = Pattern.compile(foodRegex);
        Pattern PLACE_PATTERN = Pattern.compile(placeRegex);
        Pattern TIME_PATTERN = Pattern.compile(timeRegex);

        for(String sentence : sentences){
            Matcher f = FOOD_PATTERN.matcher(sentence);
            Matcher p = PLACE_PATTERN.matcher(sentence);
            Matcher t = TIME_PATTERN.matcher(sentence);
            if (f.find()){
                output[0] = f.group(0);
            }if (p.find()){
                output[1] = p.group(0);
            }if (t.find()){
                output[2] = t.group(0);
            }
        }

        return output;

    }
}
