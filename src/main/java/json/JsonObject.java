package json;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Created by Andrii_Rodionov on 1/3/2017.
 */
public class JsonObject extends Json {
    HashMap<String, Json> dict;

    public JsonObject(JsonPair... jsonPairs) {
        dict = Arrays.stream(jsonPairs)
                .collect(Collectors.toMap(
                        x -> x.key,
                        x -> x.value,
                        (prev, next) -> next,
                        HashMap::new)
                );
    }

    @Override
    public String toJson() {
        return '{' +
                dict.entrySet()
                        .stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue().toJson())
                        .collect(Collectors.joining(", ")) +
                '}';
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
