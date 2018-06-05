package com.aliyun.kongming.unittest.fit.dbfit.util;

import com.aliyun.kongming.unittest.fit.util.ParseArg;

import fit.TypeAdapter;

public class DbTypeAdapter extends TypeAdapter {

    public Object parse(final String s) throws Exception {
        String text = ParseArg.parseCellValue(s);
        if (text.toLowerCase().equals("<null>")) {
            return null;
        }
        if (this.type.equals(String.class) && Options.isFixedLengthStringParsing() && text.startsWith("'")
                && text.endsWith("'")) {
            return text.substring(1, text.length() - 1);
        }
        TypeAdapter ta = TypeAdapter.adapterFor(this.type);
        if (ta.getClass().equals(TypeAdapter.class)) {
            return super.parse(text);
        } else {
            return ta.parse(text);
        }
    }
}
