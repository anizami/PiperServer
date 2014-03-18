/**
 * Created by Asra Nizami on 3/18/14.
 */
public class EventUtility {
    public String[] stringSegment(String string) {

        String[] subStrings = string.split("<div");

        return subStrings;
    }

    public boolean isStory(String block) {

        if (!block.contains("class=\"")) {
            System.out.println("no tag! ");
            return false;

        } else {
            int tagLoc = block.indexOf("class=\"");
            if ((block.substring(tagLoc + 7, tagLoc + tagLoc + 12).equals("story\""))) {
                return true;
            }
        }
        return false;
    }

    public String cleanup(String story) {
        String goodText ="";
        story = ("<" + story);
        int len = story.length();
        int numOpen = 0;
        int numClose = 0;
        boolean copy = true;

        for (int i = 0; i < len; i++) {
            String character = story.substring(i, i + 1);
            if (character.equals("<")) {
                numOpen++;
                copy = false;
            } else if (character.equals(">")) {
                numClose++;
                copy = true;
            }
            if ( copy && !character.equals(">")){
                goodText += character;
            }

        }

        goodText = goodText.replace("&nbsp;", " ");

        String[] replaceWithSpace = { "&nbsp;", "&raquo;"};
        String[] replaceWithQuotes = { "&quot;" };
        String[] replaceWithEAccent = { "&eacute;" };
        String[] replaceWithAmp = { "&amp;" };
        String[] replaceWithOAccent = { "&oacute;" };
        String[] replaceWithUUmlaut = { "&uuml;" };



        for (int i = 0; i < replaceWithSpace.length; i++) {
            String uglyString = replaceWithSpace[i];
            goodText = goodText.replace(uglyString, " ");

        }
        for (int i = 0; i < replaceWithQuotes.length; i++) {
            String uglyString = replaceWithQuotes[i];
            goodText = goodText.replace(uglyString, "\"");

        }
        for (int i = 0; i < replaceWithEAccent.length; i++) {
            String uglyString = replaceWithEAccent[i];
            goodText = goodText.replace(uglyString, "é");
        }
        for (int i = 0; i < replaceWithOAccent.length; i++) {
            String uglyString = replaceWithOAccent[i];
            goodText = goodText.replace(uglyString, "ó");
        }
        for (int i = 0; i < replaceWithUUmlaut.length; i++) {
            String uglyString = replaceWithUUmlaut[i];
            goodText = goodText.replace(uglyString, "ü");
        }
        for (int i = 0; i < replaceWithAmp.length; i++) {
            String uglyString = replaceWithAmp[i];
            goodText = goodText.replace(uglyString, "&");

        }

        //goodText = goodText.replace("\n", "RETURN");
        goodText = goodText.replaceAll(" * ", " ");
        //goodText = goodText+"POOOOOOOP";


        return goodText;

    }


}
