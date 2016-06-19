package fefe.com.accountbook.functions;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import fefe.com.accountbook.item.Product;

/**
 * Created by fefe on 16/06/19.
 */
public class ProductAdapter implements JsonSerializer<Product> {

    @Override
    public JsonElement serialize(Product src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.name);
        jsonObject.addProperty("cost", src.cost);
        jsonObject.addProperty("genre", src.genre);
        jsonObject.addProperty("date", src.date);
        jsonObject.addProperty("year", src.month.year);
        jsonObject.addProperty("month", src.month.month);

        return jsonObject;
    }
}