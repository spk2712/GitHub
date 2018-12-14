package com.tcs.ifact.helper;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class MyNullKeySerializer extends StdSerializer<Object> {
    public MyNullKeySerializer() {
        this(null);
    }
 
    public MyNullKeySerializer(Class<Object> t) {
        super(t);
    }
     

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		gen.writeFieldName("");
		
	}
}