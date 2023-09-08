package com.wx.common.web.handler;

import cn.hutool.json.JSONNull;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

/**
 * @author wwx
 * @version 1.0.0
 * @ClassName JsonNullSerizlizer.java
 * @Description 解决jacson无法序列化hutool的jsonnull对象的问题
 * @createTime 2022年11月25日 15:43:00
 */
@JsonComponent
public class JsonNullSerializer extends JsonSerializer<JSONNull> {
    @Override
    public void serialize(JSONNull jsonNull, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeNull();
    }
}
