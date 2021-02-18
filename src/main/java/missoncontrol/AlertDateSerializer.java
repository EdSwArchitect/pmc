package missoncontrol;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Serializer for the date object when serializing JSON
 */
public class AlertDateSerializer implements JsonSerializer<Date> {
    /** Telemetry timestamp format */
    private static String DATE_FORMAT = "yyyyMMdd HH:mm:ss.SSS'Z'";
    /** formatter */
    private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
        System.out.format("Object is of class: %s\n", src.getClass().getName());
        return new JsonPrimitive(sdf.format(src));
    }
}
