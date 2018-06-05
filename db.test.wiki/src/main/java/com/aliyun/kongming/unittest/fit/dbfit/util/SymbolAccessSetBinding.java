package com.aliyun.kongming.unittest.fit.dbfit.util;

import com.aliyun.kongming.unittest.fit.util.ParseArg;

import fit.Binding;
import fit.Fixture;
import fit.Parse;

public class SymbolAccessSetBinding extends Binding.SetBinding {

    @Override
    public void doCell(Fixture fixture, Parse cell) throws Throwable {
        String text = ParseArg.parseCellValue(cell);
        if ("".equals(cell.text())) {
            fixture.handleBlankCell(cell, adapter);
        }
        adapter.set(adapter.parse(text));
    }
}
