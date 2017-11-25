package itbour.onetouchshow.gsonanalysis;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import itbour.onetouchshow.AppConst;
import itbour.onetouchshow.bean.inching.DocContentV10Bean;
import itbour.onetouchshow.bean.inching.InchingCoreNoPPTV10Bean;
import itbour.onetouchshow.bean.inching.InchingDocStringNoPPTV10Bean;

/**
 * Created by zjl_d on 2017/11/21.
 */

public class InchingContentRules implements JsonDeserializer<DocContentV10Bean> {
    @Override
    public DocContentV10Bean deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException {
        DocContentV10Bean docContentV10Bean = new DocContentV10Bean();
        JsonObject asJsonObject = json.getAsJsonObject();
        JsonElement tmplIdJson = asJsonObject.get("tmplId");
        JsonElement docIdJson = asJsonObject.get("docId");
        JsonElement opTypeJson = asJsonObject.get("opType");

        String docStringJson = (String) context.deserialize(asJsonObject.get("docString"), String.class);

        setNoDocStringParams(context, docContentV10Bean, tmplIdJson, docIdJson, opTypeJson);

        setDocStringParams(context, docContentV10Bean, opTypeJson, docStringJson);

        return docContentV10Bean;
    }

    /**
     * 设置docString
     *
     * @param context
     * @param docContentV10Bean
     * @param opTypeJson
     * @param docStringJson
     */
    private void setDocStringParams(JsonDeserializationContext context, DocContentV10Bean docContentV10Bean, JsonElement opTypeJson, String docStringJson) {
        int opType = (int) context.deserialize(opTypeJson, Integer.class);
        if (opType == AppConst.PPT_ORTYPE) {

        } else {

            InchingDocStringNoPPTV10Bean inchingCoreNoPPTV10Bean =
                    new GsonBuilder().registerTypeAdapter(InchingCoreNoPPTV10Bean.class, new InchingContentCoreNoPPTRules()).create()
                    .fromJson(docStringJson, InchingDocStringNoPPTV10Bean.class);
            docContentV10Bean.setNoPptCoreData(inchingCoreNoPPTV10Bean);
        }
    }

    /**
     * 设置除了docString的参数
     *
     * @param context
     * @param docContentV10Bean
     * @param tmplIdJson
     * @param docIdJson
     * @param opTypeJson
     */
    private void setNoDocStringParams(JsonDeserializationContext context, DocContentV10Bean docContentV10Bean,
                                      JsonElement tmplIdJson, JsonElement docIdJson, JsonElement opTypeJson) {
        //除去docStrin外其它的数据
        int tmplId = (int) context.deserialize(tmplIdJson, Integer.class);
        int docId = (int) context.deserialize(docIdJson, Integer.class);
        int opType = (int) context.deserialize(opTypeJson, Integer.class);


        docContentV10Bean.setTmplId(tmplId);
        docContentV10Bean.setDocId(docId);
        docContentV10Bean.setOpType(opType);
    }
}
