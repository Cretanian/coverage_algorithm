import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;

public class ParseJsonFile {

    static void parseRelativePotitionObject(Object obj, ArrayList<Integer> X_values, ArrayList<Integer> Y_values)
    {

        JSONObject relativePosition = (JSONObject) obj;
        relativePosition = (JSONObject) relativePosition.get("relativePosition");
        System.out.println(relativePosition);

        JSONArray coordinates = (JSONArray) ((JSONObject) relativePosition.get("value")).get("coordinates");

        for(int i = 0; i < coordinates.size(); ++i )
        {
            String str = coordinates.get(i).toString();
            str = str.substring(1, str.length() - 1);
            String[] tokens = str.split(",");
            X_values.add(Integer.parseInt(tokens[0]));
            Y_values.add(Integer.parseInt(tokens[1]));
        }

    }
}
