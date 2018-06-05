package com.aliyun.kongming.unittest.fit;

import java.io.File;
import java.net.URL;

import com.aliyun.kongming.unittest.fit.util.ClazzUtil;
import com.aliyun.kongming.unittest.fit.util.StringHelper;

public class WikiFile {
    /**
     * fit文件的后缀
     */
    private final static String FIT_SURFIX   = ".wiki";
    public File                 wikiFile;
    public String               clazzPath;
    private String              htmlFilePath = null;

    /**
     * 判断文件文件是否是fit文件
     * 
     * @param filename
     * @return
     */
    public static boolean isFitFile(String filename) {
        if (StringHelper.isBlankOrNull(filename)) {
            return false;
        }
        filename = filename.trim();
        if (filename.endsWith(FIT_SURFIX) == false) {
            return false;
        }
        return filename.length() > FIT_SURFIX.length();
    }

    /**
     * 返回wiki文件的名称
     * 
     * @return
     */
    public String wikiName() {
        String name = wikiFile.getName();
        int last = name.lastIndexOf(FIT_SURFIX);
        return name.substring(0, last);
    }

    /**
     * 创建wiki测试结果输出路径
     * 
     * @param dbfitDir
     * @return
     */
    public File mkTestedDir(String dbfitDir) {
        int last = clazzPath.lastIndexOf(FIT_SURFIX);
        String base = System.getProperty("user.dir");
        this.htmlFilePath = String.format("%s/%s/%s.html", base, dbfitDir, clazzPath.subSequence(0, last));

        File html = new File(this.htmlFilePath);
        if (html.getParentFile().exists() == false) {
            html.getParentFile().mkdirs();
        }
        return html;
    }

    /**
     * 查找wiki文件
     * 
     * @param claz
     * @param url
     * @return
     * @throws Exception
     */
    public static WikiFile findWikiFile(final Class<?> claz, final String url) throws Exception {
        WikiFile wikiFile = new WikiFile();

        wikiFile.clazzPath = url;
        URL file = ClassLoader.getSystemResource(wikiFile.clazzPath);
        if (file == null && claz != null) {
            wikiFile.clazzPath = ClazzUtil.getPathFromPath(claz) + File.separatorChar + url;
            file = ClassLoader.getSystemResource(wikiFile.clazzPath);
        }
        if (file == null) {
            throw new RuntimeException(String.format("can't find wiki in classpath:%s", wikiFile.clazzPath));
        } else {
            wikiFile.wikiFile = new File(file.toURI());
        }
        return wikiFile;
    }

    public String getHtmlFilePath() {
        return htmlFilePath;
    }
}
