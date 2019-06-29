package com.ronglian.kangrui.saas.research.common.util;


import com.google.gson.*;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * json工具类
 */
public class JsonUtil {
 
    private static Gson gson=null;
    static{
        if(gson==null){
            gson=new Gson();
        }
    }
    private JsonUtil(){}
    /**
     * 将对象转换成json格式
     * @param ts
     * @return
     */
    public static String objectToJson(Object ts){
        String jsonStr=null;
        if(gson!=null){
            jsonStr=gson.toJson(ts);
        }
        return jsonStr;
    }
    /**
     * 将json格式转换成list对象
     * @param jsonStr
     * @return
     */
    public static List<?> jsonToList(String jsonStr){
        List<?> objList=null;
        if(gson!=null){
            Type type=new TypeToken<List<?>>(){}.getType();
            objList=gson.fromJson(jsonStr, type);
        }
        return objList;
    }
    /**
     * 转成list
     * 泛型在编译期类型被擦除导致报错
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> List<T> GsonToList(String gsonString, Class<T> cls) {
        List<T> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<T>>() {
            }.getType());
        }
        return list;
    }


    /**
     * 转成list
     * 解决泛型问题
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls) {
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

    /**
     * 将json格式转换成map对象
     * @param jsonStr
     * @return
     */
    public static Map<?,?> jsonToMap(String jsonStr){
        Map<?,?> objMap=null;
        if(gson!=null){
            Type type=new TypeToken<Map<?,?>>(){}.getType();
            objMap=gson.fromJson(jsonStr, type);
        }
        return objMap;
    }
    /**
     * 将json转换成bean对象
     * @param jsonStr
     * @return 
     * @return
     */
    public static <T>  T jsonToBean(String jsonStr,Class<T> cl){
        T obj=null;
        if(gson!=null){
            obj=gson.fromJson(jsonStr, cl);
        }
        return obj;
    }
    /**
     * 根据
     * @param jsonStr
     * @param key
     * @return
     */
    public static Object  getJsonValue(String jsonStr,String key){
        Object rulsObj=null;
        Map<?,?> rulsMap=jsonToMap(jsonStr);
        if(rulsMap!=null&&rulsMap.size()>0){
            rulsObj=rulsMap.get(key);
        }
        return rulsObj;
    }
     
}