public class Header {
    private static int maxlength = 80;

    public static String center(String text){

        String forText = "[" + text + "]";
        String l = "";
        String r = "";

        for (int x = 0; x < (maxlength - forText.length()); x++){
            if (x % 2 == 0) l += "-";
            if (x % 2 == 1) r += "-";
        }

        return l + forText + r;
    }
}