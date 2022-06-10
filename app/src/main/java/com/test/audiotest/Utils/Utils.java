package com.test.audiotest.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Utils {
    private static Gson gson;

    private Utils() {
    }

    /**
     * Add specific parsing to gson
     *
     * @return new instance of {@link Gson}
     */
    public static Gson getGsonParser() {
        if (gson == null) {
            GsonBuilder gsonBuilder = new GsonBuilder();
//            gsonBuilder.registerTypeAdapter(Filters.class, new DeserializerFilters());
            gson = gsonBuilder.create();
        }
        return gson;
    }
}
