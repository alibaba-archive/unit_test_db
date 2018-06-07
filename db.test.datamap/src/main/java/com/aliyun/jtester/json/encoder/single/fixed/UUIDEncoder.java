package com.aliyun.jtester.json.encoder.single.fixed;

import java.io.Writer;
import java.util.UUID;

import com.aliyun.jtester.json.encoder.single.FixedTypeEncoder;
import com.aliyun.jtester.json.encoder.single.FixedTypeEncoder;

public class UUIDEncoder extends FixedTypeEncoder<UUID> {
	public static UUIDEncoder instance = new UUIDEncoder();

	private UUIDEncoder() {
		super(UUID.class);
	}

	@Override
	protected void encodeSingleValue(UUID target, Writer writer) throws Exception {
		writer.append(quote_Char).append(target.toString()).append(quote_Char);
	}
}
