package hy.ea.util.elkc;

/**
 * mz
 */


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.MorpherRegistry;
import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;

public class JsonUtil {
    static {
        MorpherRegistry mr = JSONUtils.getMorpherRegistry();
        DateMorpher dm = new DateMorpher(new String[]{"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd", "HH:mm:ss", "yyyyMMdd", "yyyyMMddHHmmss", "HHmmsss"});
        mr.registerMorpher(dm);
    }

    public JsonUtil() {
    }

    public static Object getObject4JsonString(String jsonString, Class pojoCalss) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[]{"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"}));
        Object pojo = JSONObject.toBean(jsonObject, pojoCalss);
        return pojo;
    }

    public static Map getMap4Json(String jsonString) {
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        return getMap4Json(jsonObject);
    }

    public static Map getMap4Json(JSONObject jsonObject) {
        if (jsonObject == null) {
            return null;
        } else {
            Iterator keyIter = jsonObject.keys();
            HashMap valueMap = new HashMap();

            while (keyIter.hasNext()) {
                String key = (String) keyIter.next();
                Object value = jsonObject.get(key);
                valueMap.put(key, value);
            }

            return valueMap;
        }
    }

    public static Object[] getObjectArray4Json(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        return jsonArray.toArray();
    }

    public static List getList4Json(String jsonString, Class pojoClass) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        ArrayList list = new ArrayList();

        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object pojoValue = JSONObject.toBean(jsonObject, pojoClass);
            list.add(pojoValue);
        }

        return list;
    }

    public static List getList4Json(String jsonString, Class pojoClass, JsonConfig jsonConfig) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        ArrayList list = new ArrayList();

        for (int i = 0; i < jsonArray.size(); ++i) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Object pojoValue = JSONObject.toBean(jsonObject, pojoClass, jsonConfig);
            list.add(pojoValue);
        }

        return list;
    }

    public static String[] getStringArray4Json(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        String[] stringArray = new String[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); ++i) {
            stringArray[i] = jsonArray.getString(i);
        }

        return stringArray;
    }

    public static Long[] getLongArray4Json(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Long[] longArray = new Long[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); ++i) {
            longArray[i] = Long.valueOf(jsonArray.getLong(i));
        }

        return longArray;
    }

    public static Integer[] getIntegerArray4Json(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Integer[] integerArray = new Integer[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); ++i) {
            integerArray[i] = Integer.valueOf(jsonArray.getInt(i));
        }

        return integerArray;
    }

    public static Double[] getDoubleArray4Json(String jsonString) {
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Double[] doubleArray = new Double[jsonArray.size()];

        for (int i = 0; i < jsonArray.size(); ++i) {
            doubleArray[i] = Double.valueOf(jsonArray.getDouble(i));
        }

        return doubleArray;
    }

    public static String getJsonString4JavaPOJO(Object javaObj) {
        JSONObject json = JSONObject.fromObject(javaObj);
        return json.toString();
    }

    public static String getJsonString4JavaList(List list, String dataFormat) {
        JsonConfig jsonConfig = configJson(dataFormat);
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        String array = jsonArray.toString().replaceAll("null", "\"\"");
        return array;
    }

    public static String getJsonString4JavaList(List list, String[] excludes, String datePattern) {
        JsonConfig jsonConfig = configJson(excludes, datePattern);
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        String array = jsonArray.toString().replaceAll("null", "\"\"");
        return array;
    }

    public static JSONArray getJson4JavaList(List list, String dataFormat) {
        JsonConfig jsonConfig = configJson(dataFormat);
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        return jsonArray;
    }

    public static JSONArray getJson4JavaList(List list, String[] excludes, String datePattern) {
        JsonConfig jsonConfig = configJson(excludes, datePattern);
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        return jsonArray;
    }

    public static String getJsonString4JavaPOJO(Object javaObj, String dataFormat) {
        JsonConfig jsonConfig = configJson(dataFormat);
        JSONObject json = JSONObject.fromObject(javaObj, jsonConfig);
        return json.toString();
    }

    public static JSONObject getJson4JavaPOJO(Object javaObj, String dataFormat) {
        JsonConfig jsonConfig = configJson(dataFormat);
        JSONObject json = JSONObject.fromObject(javaObj, jsonConfig);
        return json;
    }

    public static JsonConfig configJson(String datePattern) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(new String[]{""});
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(datePattern));
        return jsonConfig;
    }

    public static JsonConfig configJson(String[] excludes, String datePattern) {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setExcludes(excludes);
        jsonConfig.setIgnoreDefaultExcludes(true);
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(datePattern));
        return jsonConfig;
    }
}
