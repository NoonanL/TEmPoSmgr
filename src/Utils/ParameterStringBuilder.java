package Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParameterStringBuilder {

    /*
    This class is a utility function to build a formatted output string that will be accepted by the API.
    It uses a Stringbuilder working off a hashmap of key-value pairs and then returns the formatted String.
     */
    @SuppressWarnings("Duplicates")
    public static String getParamsString(Map<String, String> params)
            throws UnsupportedEncodingException {

        //define non-encoded body items as chars to try and avoid charset issues.
        char oBracket = '{';
        char eBracket = '}';
        char quote = '"';

        StringBuilder result = new StringBuilder();
            result.append(oBracket);
        //for all the key-value strings passed via map
        for (Map.Entry<String, String> entry : params.entrySet()) {
            result.append(quote);
            //encode key to charset and add it to result string
            result.append(entry.getKey());
            result.append(quote);
            result.append(':');
            result.append(quote);
            //encode value to charset and add it to result string
            result.append(entry.getValue());
            result.append(quote);
            result.append(',');

        }result.append(eBracket);
        String resultString = result.toString();
        //System.out.println(resultString);
        return resultString;

    }

}
