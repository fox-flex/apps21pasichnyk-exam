package json;

import java.util.HashMap;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class JsonObject extends Json {
    HashMap<String, Json> dict;

    public JsonObject(JsonPair... jsonPairs) {
        dict = new HashMap<>();
        for (JsonPair jsonPair : jsonPairs) {
            dict.put(jsonPair.key, jsonPair.value);
        }
    }

    @Override
    public String toJson() {
        StringBuilder res = new StringBuilder("{");
        String prefix = "";
        for (String key : dict.keySet()) {
            res.append(prefix);
            prefix = ",";
            res.append('\'')
                    .append(key)
                    .append("': ")
                    .append(dict.get(key).toJson());
        }
        res.append('}');
        return res.toString();
    }

    public void add(JsonPair jsonPair) {
        dict.put(jsonPair.key, jsonPair.value);
    }

    public Json find(String name) {
        return dict.get(name);
    }

    public JsonObject projection(String... names) {
        JsonObject res = new JsonObject();
        for (String name : names) {
            Json data = find(name);
            if (data != null) {
                res.add(new JsonPair(name, data));
            }
        }
        return res;
    }
}
