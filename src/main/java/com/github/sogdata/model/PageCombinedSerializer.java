package com.github.sogdata.model;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class PageCombinedSerializer<T> extends JsonSerializer<IPage<T>> {

	@Autowired
	private PageAttribute attribute;

	@Override
	public void serialize(IPage<T> page, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		jsonGenerator.writeStartObject();
		jsonGenerator.writeArrayFieldStart(attribute.getContent());
		for (Object item : page.getRecords())
			jsonGenerator.writeObject(item);
		jsonGenerator.writeEndArray();
		jsonGenerator.writeNumberField(attribute.getTotalElements(), page.getTotal());
		jsonGenerator.writeNumberField(attribute.getTotalPages(), page.getPages());
		jsonGenerator.writeNumberField(attribute.getPage(), page.getCurrent());
		jsonGenerator.writeNumberField(attribute.getSize(), page.getSize());
		jsonGenerator.writeEndObject();
	}
}