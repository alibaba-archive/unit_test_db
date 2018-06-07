package com.aliyun.jtester.tools.datagen;

import java.util.ArrayList;
import java.util.List;

import com.aliyun.jtester.json.JSON;
import com.aliyun.jtester.module.ICore;
import com.aliyun.jtester.module.database.IDBInitial;
import com.aliyun.jtester.tools.commons.ArrayHelper;
import com.aliyun.jtester.tools.commons.ExceptionWrapper;

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class DataSet {
    protected List<ICore.DataMap> datas = new ArrayList<ICore.DataMap>();

    public DataSet() {
    }

    public void data(int count, ICore.DataMap datas) {
        List list = DataSet.parseMapList(count, datas);
        this.datas.addAll(list);
    }

    public void data(ICore.DataMap data) {
        this.datas.add(data);
    }

    public void data(String json) {
        ICore.DataMap data = JSON.toObject(json, ICore.DataMap.class);
        this.datas.add(data);
    }

    /**
     * 根据要插入数据的数量count和数据集合datas，分解出count条待插入的数据集
     * 
     * @param count
     * @param datas
     * @return
     */
    public static List<ICore.DataMap> parseMapList(int count, ICore.DataMap datas) {
        List<ICore.DataMap> list = new ArrayList<ICore.DataMap>();
        for (int index = 0; index < count; index++) {
            ICore.DataMap data = new ICore.DataMap();
            for (String key : datas.keySet()) {
                Object dataGenerator = datas.get(key);
                Object value = getObjectFromDataGenerator(data, dataGenerator, index);
                data.put(key, value);
            }
            list.add(data);
        }
        return list;
    }

    private static Object getObjectFromDataGenerator(ICore.DataMap dataMap, Object dataGenerator, int index) {
        if (ArrayHelper.isCollOrArray(dataGenerator)) {
            Object[] oa = ArrayHelper.toArray(dataGenerator);
            int count = oa.length;
            Object value = index < count ? oa[index] : oa[count - 1];
            return value;
        } else if (dataGenerator instanceof AbastractDataGenerator) {
            AbastractDataGenerator generator = (AbastractDataGenerator) dataGenerator;
            generator.setDataMap(dataMap);
            return generator.generate(index);
        } else {
            return dataGenerator;
        }
    }

    /**
     * 插入列表中的数据集<br>
     * 插入完毕后列表不做清空，方便重用
     * 
     * @param table
     */
    public void insert(String table) {
        for (ICore.DataMap map : this.datas) {
            try {
                IDBInitial.newInsertOp().insert(table, map);
            } catch (Exception e) {
                throw ExceptionWrapper.getUndeclaredThrowableExceptionCaused(e);
            }
        }
    }
}
