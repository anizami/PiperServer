/**
 * Created by anizami on 4/23/14.
 */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FoodEventUtils {

    private static String lookBehind = "(?<=(^| ))";
    private static String lookAhead = "(?=($| |\\.|\\!|\\?|\\,|\\;|\\:))";
    private static String foodRegex = lookBehind + "([Bb]reakfast|[Rr]oot [Bb]eer|[Ff]ood and [Ff]un|[Ll]unch|[Dd]inner|[Ss]nack[s]|[Bb]agels|[Rr]efreshments|[Pp]ie|[Cc](ake|offee|hocolate|andy)|[Ii]ce cream]|([Cc]affeinated )?[Bb]everages|[Vv]eggies|[Aa]pples|[Vv]egetables|[Tt]ea|[Dd]rinks|[Pp]izza]|[Ff]ruit)" + lookAhead;
    private static String placeRegex = lookBehind + "([Oo]lin[ -][Rr]ice|[Cc]ampus[ -][Cc]enter|[Cc]arnegie|[Oo]ld [Mm]ain|[Hh]all [Oo]f [Ff]ame [Rr]oom|[Hh]armon [Rr]oom|[Ff]rench [Mm]eadow [Bb]akery|(Russian|Japan|French|German|Spanish|Chinese) [Hh]ouse|[Mm]arkim [Hh]all|[Ss]tudent [Gg]allery|Koch [Ll]ounge|[Ww]eyerhaeuser ([Mm]emorial)? [Cc]hapel|[Kk]agin|GSRC|[Kk]irk|[Dd]upre|[Tt]urck|[Dd]oty|GDD|G.D.D.|[M]innehaha [Ff]alls|[Bb]igelow|[Ww]eyerhaeuser ([Bb]oardroom)?|[Ss]cience [Mm]useum [Oo]f [Mm]innesota|[Dd]eWitt [Ww]allace [Ll]ibrary)" + lookAhead;
    private static String timeRegex = lookBehind + "([0-9]{1,2}(:[0-9][0-9])?(( a.m.)|( p.m.))?|(noon))(( to )|( ?- ?))([0-9]{1,2}(:[0-9][0-9])?(( a.m.)|( p.m.))?|(noon))|([0-9]{1,2}:[0-9]{2,2}( a.m.| p.m.)?)|([0-9]{1,2}( a.m.| p.m.)|noon)" + lookAhead;
    private static String todayRegex = lookBehind + "[Tt]oday" + lookAhead;

    public static String[] extractData(String input){

        // 0:description 1:place 2:time 3: body
        String[] output = new String[4];

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

    public static boolean isToday (String title, String body){
        String allText = title + " " + body;
        Pattern TODAY_PATTERN = Pattern.compile(todayRegex);
        Matcher o = TODAY_PATTERN.matcher(allText);
        return o.find();
    }
}
