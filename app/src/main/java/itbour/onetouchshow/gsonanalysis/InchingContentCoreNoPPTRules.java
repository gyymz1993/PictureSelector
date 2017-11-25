package itbour.onetouchshow.gsonanalysis;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import itbour.onetouchshow.bean.inching.InchingCoreNoPPTV10Bean;

/**
 * Created by zjl_d on 2017/11/21.
 */

public class InchingContentCoreNoPPTRules implements JsonDeserializer<InchingCoreNoPPTV10Bean> {
    @Override
    public InchingCoreNoPPTV10Bean deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        return null;
    }
}
