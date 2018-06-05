package com.aliyun.kongming.unittest.fit.dbfit.util;

import com.aliyun.kongming.unittest.fit.util.ParseArg;

import fit.Binding;
import fit.Fixture;
import fit.Parse;

public class SymbolAccessQueryBinding extends Binding.QueryBinding {
    public void doCell(Fixture fixture, Parse cell) {
        String content = cell.text();
        try {
            if (content.startsWith(">>")) {
                Object value = this.adapter.get();
                SymbolUtil.setSymbol(content.substring(2).trim(), value);
                cell.addToBody(Fixture.gray("= " + String.valueOf(value)));
                return;
            }
            Object actual = this.adapter.get();
            String value = ParseArg.parseCellValue(cell);
            if (content.startsWith("fail[") || content.endsWith("]")) {
                String expectedVal = value.substring(5, value.length() - 1);
                Object expected = adapter.parse(expectedVal);
                if (adapter.equals(actual, expected)) {
                    fixture.wrong(cell, String.valueOf(value));
                } else {
                    fixture.right(cell);
                }
            } else {
                Object expected = this.adapter.parse(value);
                if (adapter.equals(actual, expected)) {
                    fixture.right(cell);
                } else {
                    fixture.wrong(cell, String.valueOf(value));
                }
            }
        } catch (Exception t) {
            fixture.exception(cell, t);
            return;
        }
    }
}
