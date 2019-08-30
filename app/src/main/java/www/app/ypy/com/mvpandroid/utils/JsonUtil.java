package www.app.ypy.com.mvpandroid.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

/**
 * Json的工具类
 * <p>
 *
 * @author limeng
 * @version 2016年6月24日
 */
public class JsonUtil {
    private static final String TAG = JsonUtil.class.getName();

    private JsonUtil() {
        throw new IllegalAccessError("JsonUtil.class");
    }

    public static String parseString(Object t) {
        String value = null;
        if (t != null)
            value = JSON.toJSONString(t);
        return value;
    }

    public static <T> List<T> parseArrayObject(String data, Class<T> t) {
        try {
            List<T> parseArray = JSON.parseArray(data, t);
            return parseArray;
        } catch (Exception e) {


        }

        return null;
    }

    public static JSONObject parseJSONObject(Object t) {
        if (t == null)
            return null;
        String value = null;
        if (t instanceof String) {
            value = cast(t);
        } else
            value = parseString(t);

        JSONObject parseObject = null;
        try {
            if (!"".equals(value) && value != null)
                parseObject = JSON.parseObject(value);
        } catch (Exception e) {

        }

        return parseObject;
    }

    public static JSONArray parseArray(String data) {
        try {
            return JSON.parseArray(data);
        } catch (Exception e) {

        }
        return null;

    }

    public static <T> T cast(Object obj) {
        if (obj == null)
            return null;

        try {
            return (T) obj;
        } catch (Exception e) {

        }
        return null;
    }

}
