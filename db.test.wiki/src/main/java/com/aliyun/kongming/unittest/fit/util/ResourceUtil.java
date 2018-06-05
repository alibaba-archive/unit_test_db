package com.aliyun.kongming.unittest.fit.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;

public class ResourceUtil {
    /*
     * To convert the InputStream to String we use the BufferedReader.readLine()
     * method. We iterate until the BufferedReader return null which means
     * there's no more data to read. Each line will appended to a StringBuilder
     * and returned as String.
     */
    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = null;
        String line = null;
        try {
            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(reader);
            close(is);
        }
    }

    /**
     * 过滤file文件中的注释代码
     * 
     * @param is
     * @return
     */
    public static String convertStreamToSQL(InputStream is) {
        BufferedReader reader = null;
        String line = null;
        try {
            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                if (!line.startsWith("#") && !line.startsWith("--")) {
                    buffer.append(line + " ");
                }
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(reader);
            close(is);
        }
    }

    public static String convertStreamToString(InputStream is, String encoding) {
        BufferedReader reader = null;
        String line = null;
        try {
            StringBuilder buffer = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(is, encoding));
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            return buffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(reader);
            close(is);
        }
    }

    public static void close(InputStream is) {
        if (is == null) {
            return;
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(Reader reader) {
        if (reader == null) {
            return;
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void close(Writer writer) {
        if (writer == null) {
            return;
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 从文本文件中读取内容
     * 
     * @param filePath
     * @return
     */
    public static String readStringFromFile(String filePath) {
        try {
            InputStream stream = new FileInputStream(new File(filePath));
            return convertStreamToString(stream);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 从文本文件中读取内容
     * 
     * @param file
     * @return
     */
    public static String readStringFromFile(File file) {
        try {
            String encoding = ResourceUtil.getFileEncodingCharset(file);
            InputStream stream = new FileInputStream(file);
            return convertStreamToString(stream, encoding);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 写字符串到文件中
     * 
     * @param file
     * @param content
     */
    public static void writeStringToFile(File file, String content) {
        FileWriter writer = null;
        try {
            File parent = file.getParentFile();
            // 创建目录
            if (parent.exists() == false) {
                parent.mkdirs();
            }
            // 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
            writer = new FileWriter(file, false);
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 把classpath中的文件内容复制到指定的外部文件中
     * 
     * @param classPathFile
     * @param outputFile
     */
    public static void copyFile(String classPathFile, File outputFile) {
        FileWriter writer = null;
        InputStream is = null;
        try {
            writer = new FileWriter(outputFile);
            is = ClassLoader.getSystemResourceAsStream(classPathFile);
            String content = convertStreamToString(is);
            writer.write(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            close(writer);
        }
    }

    /**
     * 从classpath中读取文件内容
     * 
     * @param clazPathFile
     * @return
     */
    public static String readFileFromClassPath(String clazPathFile) {
        try {
            InputStream is = ClassLoader.getSystemResourceAsStream(clazPathFile);
            String content = convertStreamToString(is);
            return content;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream findClassPathStream(Class<?> claz, String url) {
        InputStream is = ClassLoader.getSystemResourceAsStream(url);
        if (is != null) {
            return is;
        } else if (claz == null) {
            throw new RuntimeException("can't find class path resource in " + url);
        }
        String newUrl = ClazzUtil.getPathFromPath(claz) + File.separatorChar + url;
        is = ClassLoader.getSystemResourceAsStream(newUrl);
        if (is != null) {
            return is;
        } else {
            throw new RuntimeException("can't find class path resource in " + url + " or in " + newUrl);
        }
    }

    /**
     * 查找文件
     * 
     * @param claz
     * @param url
     * @return
     * @throws Exception
     */
    public static File findWikiFile(final Class<?> claz, final String url) throws Exception {
        if (url.startsWith("file:")) {
            return new File(url.replace("file:", ""));
        } else {
            URL file = ClassLoader.getSystemResource(url);
            String newUrl = url;
            if (file == null && claz != null) {
                newUrl = ClazzUtil.getPathFromPath(claz) + File.separatorChar + url;
                file = ClassLoader.getSystemResource(newUrl);
            }
            if (file == null) {
                throw new RuntimeException(String.format("can't find resource in classpath:%s", newUrl));
            } else {
                return new File(file.toURI());
            }
        }
    }

    /**
     * 文件缺省的编码格式
     * 
     * @return
     */
    public static String fileEncoding() {
        return System.getProperty("file.encoding", "utf-8");
    }

    /**
     * 获得的文件的编码格式
     * 
     * @param file
     * @return
     */
    public static String getFileEncodingCharset(File file) {
        return "utf-8";
    }

    /**
     * 根据文件名称返回文件输入流
     * 
     * @param file
     * @return
     */
    public static InputStream getInputStreamFromFile(final String file) {
        String _file = file;
        if (file == null) {
            throw new RuntimeException("execute file name can't be null!");
        }
        if (file.contains("file:")) {
            _file = file.replace("file:", "");
            try {
                return new FileInputStream(new File(_file));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            if (file.contains("classpath:")) {
                _file = file.replace("classpath:", "");
            }
            return ClassLoader.getSystemResourceAsStream(_file);
        }
    }
}
