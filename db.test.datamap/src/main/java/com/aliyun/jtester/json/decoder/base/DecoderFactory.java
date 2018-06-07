package com.aliyun.jtester.json.decoder.base;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.jtester.json.decoder.ArrayDecoder;
import com.aliyun.jtester.json.decoder.CollectionDecoder;
import com.aliyun.jtester.json.decoder.IDecoder;
import com.aliyun.jtester.json.decoder.MapDecoder;
import com.aliyun.jtester.json.decoder.PoJoDecoder;
import com.aliyun.jtester.json.decoder.single.AtomicBooleanDecoder;
import com.aliyun.jtester.json.decoder.single.AtomicIntegerDecoder;
import com.aliyun.jtester.json.decoder.single.AtomicLongDecoder;
import com.aliyun.jtester.json.decoder.single.BigDecimalDecoder;
import com.aliyun.jtester.json.decoder.single.BigIntegerDecoder;
import com.aliyun.jtester.json.decoder.single.BooleanDecoder;
import com.aliyun.jtester.json.decoder.single.ByteDecoder;
import com.aliyun.jtester.json.decoder.single.CharDecoder;
import com.aliyun.jtester.json.decoder.single.CharsetDecoder;
import com.aliyun.jtester.json.decoder.single.ClazzDecoder;
import com.aliyun.jtester.json.decoder.single.DoubleDecoder;
import com.aliyun.jtester.json.decoder.single.FileDecoder;
import com.aliyun.jtester.json.decoder.single.FloatDecoder;
import com.aliyun.jtester.json.decoder.single.InetAddressDecoder;
import com.aliyun.jtester.json.decoder.single.IntegerDecoder;
import com.aliyun.jtester.json.decoder.single.LocaleDecoder;
import com.aliyun.jtester.json.decoder.single.LongDecoder;
import com.aliyun.jtester.json.decoder.single.PatternDecoder;
import com.aliyun.jtester.json.decoder.single.ShortDecoder;
import com.aliyun.jtester.json.decoder.single.SimpleDateFormatDecoder;
import com.aliyun.jtester.json.decoder.single.SocketAddressDecoder;
import com.aliyun.jtester.json.decoder.single.StringDecoder;
import com.aliyun.jtester.json.decoder.single.TimeZoneDecoder;
import com.aliyun.jtester.json.decoder.single.URIDecoder;
import com.aliyun.jtester.json.decoder.single.URLDecoder;
import com.aliyun.jtester.json.decoder.single.UUIDDecoder;
import com.aliyun.jtester.json.decoder.single.spec.AppendableDecoder;
import com.aliyun.jtester.json.decoder.single.spec.DateDecoder;
import com.aliyun.jtester.json.decoder.single.spec.EnumDecoder;

@SuppressWarnings("serial")
public class DecoderFactory {

    public static List<IDecoder> decoders = new ArrayList<IDecoder>() {
        {
            // 简单对象
            this.add(StringDecoder.toSTRING);
            this.add(BooleanDecoder.toBOOLEAN);
            this.add(ByteDecoder.toBYTE);
            this.add(CharDecoder.toCHARACTER);
            this.add(ClazzDecoder.toCLASS);
            this.add(DoubleDecoder.toDOUBLE);
            this.add(FloatDecoder.toFLOAT);
            this.add(IntegerDecoder.toINTEGER);
            this.add(LongDecoder.toLONG);
            this.add(ShortDecoder.toSHORT);
            this.add(LocaleDecoder.toLOCAL);
            this.add(PatternDecoder.toPATTERN);
            this.add(URLDecoder.toURL);
            this.add(URIDecoder.toURI);
            this.add(UUIDDecoder.toUUID);
            this.add(TimeZoneDecoder.toTimeZone);
            this.add(InetAddressDecoder.toINETADDRESS);
            this.add(BigDecimalDecoder.toBIGDECIMAL);
            this.add(BigIntegerDecoder.toBIGINTEGER);
            this.add(CharsetDecoder.toCHARSET);
            this.add(SocketAddressDecoder.toSOCKETADDRESS);
            this.add(FileDecoder.toFILE);
            this.add(SimpleDateFormatDecoder.toSIMPLEDATEFORMAT);
            this.add(AtomicBooleanDecoder.toATOMICBOOLEAN);
            this.add(AtomicIntegerDecoder.toATOMICINTEGER);
            this.add(AtomicLongDecoder.toATOMICLONG);
            //
            this.add(EnumDecoder.toENUM);
            this.add(DateDecoder.toDATE);
            this.add(AppendableDecoder.toAPPENDABLE);
            // array, collection, map, pojo
            this.add(ArrayDecoder.toARRAY);
            this.add(CollectionDecoder.toCOLLECTION);
            this.add(MapDecoder.toMAP);
            this.add(PoJoDecoder.toPOJO);

        }
    };

    public static IDecoder getDecoder(Type type) {
        for (IDecoder decoder : decoders) {
            if (decoder.accept(type)) {
                return decoder;
            }
        }
        return MapDecoder.toMAP;
    }

}
