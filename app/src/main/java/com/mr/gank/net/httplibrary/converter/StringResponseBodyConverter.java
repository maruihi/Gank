package com.mr.gank.net.httplibrary.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class StringResponseBodyConverter implements Converter<ResponseBody, String> {

    StringResponseBodyConverter() {
    }

    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }
    }
}
