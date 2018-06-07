package com.aliyun.jtester.json.encoder.single.jms;

import java.io.Writer;

import javax.management.ObjectName;

import com.aliyun.jtester.json.encoder.single.fixed.StringEncoder;
import com.aliyun.jtester.json.encoder.single.SpecTypeEncoder;
import com.aliyun.jtester.json.encoder.single.fixed.StringEncoder;

public class ObjectNameEncoder<T extends ObjectName> extends SpecTypeEncoder<T> {

	protected ObjectNameEncoder() {
		super(ObjectName.class);
	}

	@Override
	protected void encodeSingleValue(T target, Writer writer) throws Exception {
		String value = target.toString();
		writer.append(quote_Char);
		StringEncoder.writeEscapeString(value, writer);
		writer.append(quote_Char);
	}

	@Override
	protected void encodeOtherProperty(T target, Writer writer) throws Exception {
		// TODO Auto-generated method stub

	}
}
