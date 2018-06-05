package org.jtester.module.database;

import org.jtester.module.database.dbop.IDBOperator;
import org.jtester.module.database.dbop.IInsertOp;
import org.jtester.tools.commons.Reflector;

/**
 * Created by chao.qianc on 2016/5/31.
 */
public class IDBInitial {
    public static IDBOperator initDBOperator() {
        try {
            Class claz = Class.forName("org.jtester.module.database.dbop.DBOperator");
            Object o = Reflector.instance.newInstance(claz);
            return (IDBOperator) o;
        } catch (Throwable e) {
            return null;
        }
    }


    static Class dbInsertOpClazz = null;

    public static IInsertOp newInsertOp() {
        try {
            if (dbInsertOpClazz == null) {
                dbInsertOpClazz = Class.forName("org.jtester.module.database.dbop.InsertOp");
            }
            Object o = dbInsertOpClazz.newInstance();
            return (IInsertOp) o;
        } catch (Throwable e) {
            return null;
        }
    }
}
