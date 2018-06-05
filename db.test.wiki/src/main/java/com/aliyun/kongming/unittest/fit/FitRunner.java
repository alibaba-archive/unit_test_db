package com.aliyun.kongming.unittest.fit;

import java.io.File;
import java.util.Map;

import com.aliyun.kongming.unittest.fit.dbfit.DbFitException;
import com.aliyun.kongming.unittest.fit.dbfit.DbFitWikiPage;
import com.aliyun.kongming.unittest.fit.dbfit.util.SymbolUtil;
import com.aliyun.kongming.unittest.fit.exception.FitRunException;
import com.aliyun.kongming.unittest.fit.util.ConfigUtil;
import com.aliyun.kongming.unittest.fit.util.ResourceUtil;
import com.aliyun.kongming.unittest.fit.util.StringHelper;

import fit.Counts;
import fitlibrary.batch.trinidad.InMemoryTestImpl;
import fitlibrary.batch.trinidad.TestDescriptor;
import fitlibrary.batch.trinidad.TestEngine;
import fitlibrary.batch.trinidad.TestResult;
import fitlibrary.runner.SpreadsheetRunner;
import fitlibrary.suite.BatchFitLibrary;

public class FitRunner {
    protected final TestEngine testRunner;
    protected final String     fitDir;
    protected String           rootPath = null;

    protected FitRunner() {
        this(ConfigUtil.dbfitDir());
    }

    protected FitRunner(String fitDir) {
        this.testRunner = new JTesterFitLibraryTestEngine();
        this.fitDir = fitDir;
        this.prepareFiles();
    }

    /**
     * 运行fitnesse wiki文件
     * 
     * @param claz
     * @param url
     */
    protected void runFitTest(Class<?> claz, final String url) throws Exception {
        if (StringHelper.isBlankOrNull(url)) {
            return;
        } else if (WikiFile.isFitFile(url) == false) {
            throw new RuntimeException(String.format("url:%s is not a valid fit file name", url));
        }
        WikiFile wiki = WikiFile.findWikiFile(claz, url);

        this.runFitnesseWiki(wiki.wikiName(), wiki);
    }

    void runFitExcel(Class<?> claz, final String url) {
        SpreadsheetRunner runner = new SpreadsheetRunner();
        try {
            runner.run(new File(url), new File("report.html"), new BatchFitLibrary());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 运行dbfit格式的wiki文件
     * 
     * @param name
     * @param wikiFile
     * @throws Exception
     */
    private void runFitnesseWiki(final String name, WikiFile wikiFile) {
        String wiki = ResourceUtil.readStringFromFile(wikiFile.wikiFile);
        wiki = decoratedWiki(wiki);
        String html = this.addCssFile(DbFitWikiPage.getHtml(wiki));
        TestDescriptor test = new InMemoryTestImpl(name, html);
        TestResult testResult = testRunner.runTest(test);
        // writeTestResult(testResult, fitDir, wikiFile);

        File htmlFile = wikiFile.mkTestedDir(fitDir);
        System.err.println(testResult.getName() + " right:" + testResult.getCounts().right + " wrong:"
                + testResult.getCounts().wrong + " exeptions: " + testResult.getCounts().exceptions);
        String resultHtml = testResult.getContent();
        ResourceUtil.writeStringToFile(htmlFile, resultHtml);
        try {
            this.isSuccess(testResult, name, wikiFile);
        } catch (FitRunException e) {
            ErrorRecorder.addError(wikiFile, resultHtml, e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 对于dbfit，为了方便在头上把DatabaseFixture给省略了，运行时需要加上<br>
     * 这个函数就是给子类重载用的
     * 
     * @param wiki
     * @return
     */
    protected String decoratedWiki(String wiki) {
        return wiki;
    }

    private final static String ERR_MESSAGE = "Run Wiki Page[%s.wiki],right=%d; wrong=%d;exceptions=%d.\nclass path url[%s].\nResult['file://%s']";

    private void isSuccess(TestResult tr, String name, WikiFile wikiFile) throws FitRunException {
        Counts count = tr.getCounts();
        int right = count.right;
        int wrong = count.wrong;
        int exception = count.exceptions;

        if (wrong + exception != 0) {
            throw new FitRunException(String.format(ERR_MESSAGE, name, right, wrong, exception, wikiFile.clazzPath,
                    wikiFile.getHtmlFilePath()));
        }
    }

    private final static String CSS_FILE = "<link rel='stylesheet' media='all' type='text/css' href='file:///%s/fitnesse.css' />";

    private String addCssFile(String html) {
        StringBuffer buffer = new StringBuffer("<html>");
        buffer.append("\n");
        buffer.append(String.format(CSS_FILE, this.rootPath));
        buffer.append("\n");
        buffer.append(html);
        buffer.append("\n");
        buffer.append("</html>");
        return buffer.toString();
    }

    public static final String Fitnesse_Css_Resource_Path = "com/aliyun/kongming/unittest/fit/fitnesse_base.css";// "files/css/fitnesse_base.css";

    void prepareFiles() {
        File folder = new File(fitDir + File.separatorChar + "files");
        if (!folder.exists()) {
            folder.mkdirs();
        }
        this.rootPath = folder.getAbsolutePath();
        File css = new File(fitDir + File.separatorChar + "files/fitnesse.css");
        if (!css.exists()) {
            ResourceUtil.copyFile(Fitnesse_Css_Resource_Path, css);
        }
    }

    String getFitDir() {
        return fitDir;
    }

    private static FitRunner fitRunner = new FitRunner();

    /**
     * 运行fitnesse测试wiki文件
     * 
     * @param claz
     * @param url
     */
    public static void runFit(Class<?> claz, String url, String... urls) {
        try {
            fitRunner.runFitTest(claz, url);
            for (String wiki : urls) {
                fitRunner.runFitTest(claz, wiki);
            }
        } catch (Exception e) {
            throw new DbFitException(e);
        }
    }

    public static void runFit(Class<?> claz, boolean cleanSymbols, String url, String... urls) {
        runFit(claz, url, urls);
        if (cleanSymbols) {
            SymbolUtil.clearSymbols();
        }
    }

    /**
     * 运行fitnesse测试wiki文件
     * 
     * @param claz
     * @param symbols wiki变量
     * @param url
     * @param urls
     */
    public static void runFit(Class<?> claz, Map<String, String> symbols, String url, String... urls) {
        SymbolUtil.setSymbol(symbols);
        runFit(claz, url, urls);
    }

    public static void runFit(Class<?> claz, Map<String, String> symbols, boolean cleanSymbols, String url,
                              String... urls) {
        SymbolUtil.setSymbol(symbols);
        runFit(claz, url, urls);
        if (cleanSymbols) {
            SymbolUtil.clearSymbols();
        }
    }
}
