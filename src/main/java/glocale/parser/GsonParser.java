package glocale.parser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import glocale.part.Part;
import glocale.part.PartParser;

public class GsonParser {

    private static String solveKey(String root, String sub) {
        if (sub.equals(".")) {
            return root;
        }

        if (root.length() == 0) {
            return sub;
        }

        return root + "." + sub;
    }

    private static void parse(Map<String, Part[]> graph, String root, JsonObject curr) {
        for (Map.Entry<String, JsonElement> entry : curr.entrySet()) {
            String key = solveKey(root, entry.getKey());
            JsonElement value = entry.getValue();

            if (value.isJsonPrimitive()) {
                graph.put(key, PartParser.parse(value.getAsString()));
            } else if (value.isJsonObject()) {
                parse(graph, key, value.getAsJsonObject());
            } else {
                throw new IllegalArgumentException("Unsupported value: " + value + " is not a string or object.");
            }
        }
    }

    /**
     * Parses the provided JsonObject and returns a locale map.
     * 
     * @see {@link Glocale#use(Map<String, Part[]>)}
     */
    public static Map<String, Part[]> parse(JsonObject obj) {
        Map<String, Part[]> graph = new HashMap<>();
        parse(graph, "", obj);
        return Collections.unmodifiableMap(graph);
    }

}
