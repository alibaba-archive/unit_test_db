package com.aliyun.kongming.unittest.fit;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import com.aliyun.kongming.unittest.fit.util.ResourceUtil;
import com.aliyun.kongming.unittest.fit.util.StringHelper;

/**
 * fit文件运行错误汇总
 * 
 * @author darui.wudr
 */
public class ErrorRecorder {
    final static String FIT_WRONG_MESSAGE_HTML = System.getProperty("user.dir") + "/target/dbfit/fitnesse.wrong.html";

    /**
     * 重新创建一个新的fitnesse运行错误汇总文件
     * 
     * @throws Exception
     */
    public static void createNewErrorFile() {
        try {
            File file = new File(FIT_WRONG_MESSAGE_HTML);
            File parent = file.getParentFile();
            if (parent.exists() == false) {
                parent.mkdirs();
            }
            Writer writer = new FileWriter(file, false);
            writer.write("<html><style>");
            writer.write(ResourceUtil
                    .readFileFromClassPath(com.aliyun.kongming.unittest.fit.FitRunner.Fitnesse_Css_Resource_Path));
            writer.write("</style><body>");
            writer.close();
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }
    }

    private final static String NO_FAILURE    = "<div class='pass'>no failure</div>";
    private static boolean      HAS_ADD_ERROR = false;

    /**
     * 记录fitnesse的运行错误
     * 
     * @param wikiname
     * @param file
     * @param html
     * @param e
     */
    public static void addError(WikiFile wiki, String html, Exception e) {
        HAS_ADD_ERROR = true;
        StringBuilder buffer = new StringBuilder();
        String exception = StringHelper.exceptionTrace(e, StringHelper.DEFAULT_EXCEPTION_FILTER);

        buffer.append("<br/><br/><div>============" + wiki.clazzPath + "==============</div></br>");
        buffer.append("<table  border='1' cellspacing='0'>");
        buffer.append("<tr><td class='error'><div class='fit_stacktrace'>" + exception + "</div></td></tr>");
        buffer.append("</table><br/>");

        int start = html.indexOf("<table");
        int end = html.length() - 8;
        String content = html.substring(start, end).trim();
        buffer.append(content);
        try {
            File htmlFile = new File(FIT_WRONG_MESSAGE_HTML);
            if (htmlFile.exists() == false) {
                createNewErrorFile();
            }
            Writer writer = new FileWriter(htmlFile, true);
            writer.write(buffer.toString());
            writer.close();
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }
    }

    public static void endAddError() {
        try {
            File file = new File(FIT_WRONG_MESSAGE_HTML);
            Writer writer = new FileWriter(file, true);
            if (HAS_ADD_ERROR == false) {
                writer.write(NO_FAILURE);
            }
            writer.write("</body></html>");
            writer.close();
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }
    }
}
