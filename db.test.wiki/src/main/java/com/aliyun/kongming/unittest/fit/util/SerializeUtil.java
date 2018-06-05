package com.aliyun.kongming.unittest.fit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.aliyun.kongming.unittest.fit.exception.JTesterException;
import com.aliyun.kongming.unittest.fit.util.xstream.ExXStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

//import com.thoughtworks.xstream.XStream;

/**
 * POJO对象序列化和反序列化工具类
 * 
 * @author darui.wudr
 */
public class SerializeUtil {
    /**
     * 将pojo序列化后存储在dat类型的文件中
     * 
     * @param <T>
     * @param o 需要序列化的对象
     * @param filename 存储文件的路径名称
     */
    public static <T> void toDat(T o, String filename) {
        SerializeUtil.mkdirs(filename);

        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
            out.writeObject(o);
            out.close();
        } catch (Exception e) {
            throw new JTesterException(e);
        }
    }

    /**
     * 利用XStream将pojo保存为xml格式的文件
     * 
     * @param <T>
     * @param o 需要序列化的对象
     * @param filename 存储文件的路径名称
     */
    public static <T> void toXML(T o, String filename) {
        try {
            ExXStream xs = new ExXStream(new DomDriver());
            // XStream xs = new XStream();
            FileOutputStream fos = new FileOutputStream(filename);
            xs.toXML(o, fos);
            fos.close();
        } catch (Exception e) {
            throw new JTesterException(e);
        }
    }

    /**
     * 从dat文件中将pojo反序列化出来
     * 
     * @param <T>
     * @param claz 反序列化出来的pojo class类型
     * @param filename pojo序列化信息文件,如果以"classpath:"开头表示文件存储在classpth的package路径下，
     *            否则表示文件的绝对路径
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromDat(Class<T> claz, String filename) {
        try {
            InputStream inputStream = SerializeUtil.isFileExisted(filename);
            ObjectInputStream in = new ObjectInputStream(inputStream);
            Object obj = in.readObject();
            in.close();
            return (T) obj;
        } catch (Exception e) {
            throw new JTesterException(e);
        }
    }

    /**
     * 从dat文件中反序列对象
     * 
     * @param <T>
     * @param returnClazz 反序列化出来的pojo class类型
     * @param pathClazz dat文件所在的目录下的class，用来方便寻找dat文件
     * @param filename dat文件名称
     * @return
     */
    public static <T> T fromDat(Class<T> returnClazz, Class<?> pathClazz, String filename) {
        String path = ClazzUtil.getPathFromPath(pathClazz);
        return fromDat(returnClazz, path + File.separatorChar + filename);
    }

    /**
     * 利用xstream将pojo从xml文件中反序列化出来
     * 
     * @param <T>
     * @param claz 反序列化出来的pojo class类型
     * @param filename pojo序列化信息文件,如果以"classpath:"开头表示文件存储在classpth的package路径下
     *            <br>
     *            以"file:" 开头 表示文件的绝对路径
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T fromXML(Class<T> claz, String filename) {
        try {
            InputStream fis = SerializeUtil.isFileExisted(filename);
            if (fis == null) {
                throw new JTesterException(String.format("file '%s' doesn't exist", filename));
            }
            ExXStream xs = new ExXStream(new DomDriver());
            // XStream xs = new XStream();
            Object o = xs.fromXML(fis);
            return (T) o;
        } catch (FileNotFoundException e) {
            throw new JTesterException(e);
        }
    }

    /**
     * 利用xstream将pojo从xml文件中反序列化出来
     * 
     * @param <T>
     * @param returnClazz 反序列化出来的pojo class类型
     * @param pathClazz xml文件所在的目录下的class，用来方便寻找dat文件
     * @param filename 序列号文件xml的名称
     * @return
     */
    public static <T> T fromXML(Class<T> returnClazz, Class<?> pathClazz, String filename) {
        String path = ClazzUtil.getPathFromPath(pathClazz);
        return fromXML(returnClazz, path + File.separatorChar + filename);
    }

    private static InputStream isFileExisted(String filename) throws FileNotFoundException {
        if (filename.startsWith("file:")) {
            File file = new File(filename.replace("file:", ""));
            if (!file.exists()) {
                throw new JTesterException("object serializable file doesn't exist");
            }
            return new FileInputStream(file);
        } else {
            String file = filename.replaceFirst("classpath:", "");

            InputStream stream = ClassLoader.getSystemResourceAsStream(file);
            if (stream == null) {
                throw new JTesterException("object serializable file doesn't exist");
            }
            return stream;
        }
    }

    private static void mkdirs(String filename) {
        filename = filename.replace('/', File.separatorChar);
        filename = filename.replace('\\', File.separatorChar);
        File fo = new File(filename);
        // 文件不存在,就创建该文件,先创建文件的目录
        if (!fo.exists()) {
            String path = filename.substring(0, filename.lastIndexOf(File.separatorChar));
            File pFile = new File(path);
            pFile.mkdirs();
        }
    }
}
