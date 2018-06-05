package com.aliyun.kongming.unittest.fit.dbfit;

import fitnesse.wiki.WikiPage;
import fitnesse.wikitext.WidgetBuilder;
import fitnesse.wikitext.widgets.AliasLinkWidget;
import fitnesse.wikitext.widgets.AnchorDeclarationWidget;
import fitnesse.wikitext.widgets.AnchorMarkerWidget;
import fitnesse.wikitext.widgets.BoldWidget;
import fitnesse.wikitext.widgets.CenterWidget;
import fitnesse.wikitext.widgets.CommentWidget;
import fitnesse.wikitext.widgets.EmailWidget;
import fitnesse.wikitext.widgets.EvaluatorWidget;
import fitnesse.wikitext.widgets.HashWidget;
import fitnesse.wikitext.widgets.HeaderWidget;
import fitnesse.wikitext.widgets.HruleWidget;
import fitnesse.wikitext.widgets.ImageWidget;
import fitnesse.wikitext.widgets.ItalicWidget;
import fitnesse.wikitext.widgets.LinkWidget;
import fitnesse.wikitext.widgets.ListWidget;
import fitnesse.wikitext.widgets.LiteralWidget;
import fitnesse.wikitext.widgets.NoteWidget;
import fitnesse.wikitext.widgets.ParentWidget;
import fitnesse.wikitext.widgets.PlainTextTableWidget;
import fitnesse.wikitext.widgets.PreformattedWidget;
import fitnesse.wikitext.widgets.StandardTableWidget;
import fitnesse.wikitext.widgets.StrikeWidget;
import fitnesse.wikitext.widgets.StyleWidget;
import fitnesse.wikitext.widgets.VariableDefinitionWidget;
import fitnesse.wikitext.widgets.VariableWidget;
import fitnesse.wikitext.widgets.WidgetRoot;

public class DbFitWikiPage {
    /**
     * 根据wiki内容构造html
     * 
     * @param content
     * @return
     * @throws Exception
     */
    public static String getHtml(String content) {
        try {
            ParentWidget root = new WidgetRoot(content, (WikiPage) null, myHtmlWidgetBuilder);
            return root.render();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static WidgetBuilder myHtmlWidgetBuilder = new WidgetBuilder() {
        {

            this.addWidgetClass(CommentWidget.class);
            this.addWidgetClass(LiteralWidget.class);
            // this.addWidgetClass(WikiWordWidget.class);
            this.addWidgetClass(BoldWidget.class);
            this.addWidgetClass(ItalicWidget.class);
            this.addWidgetClass(PreformattedWidget.class);
            this.addWidgetClass(HruleWidget.class);
            this.addWidgetClass(HeaderWidget.class);
            this.addWidgetClass(CenterWidget.class);
            this.addWidgetClass(NoteWidget.class);
            this.addWidgetClass(StyleWidget.ParenFormat.class);
            this.addWidgetClass(StyleWidget.BraceFormat.class);
            this.addWidgetClass(StyleWidget.BracketFormat.class);
            // this.addWidgetClass(TableWidget.class);
            this.addWidgetClass(StandardTableWidget.class);
            this.addWidgetClass(PlainTextTableWidget.class);
            this.addWidgetClass(ListWidget.class);
            // this.addWidgetClass(ClasspathWidget.class);
            this.addWidgetClass(ImageWidget.class);
            this.addWidgetClass(LinkWidget.class);
            // this.addWidgetClass(TOCWidget.class);
            this.addWidgetClass(AliasLinkWidget.class);
            // this.addWidgetClass(VirtualWikiWidget.class);
            this.addWidgetClass(StrikeWidget.class);
            // this.addWidgetClass(LastModifiedWidget.class);
            // this.addWidgetClass(TodayWidget.class);
            // this.addWidgetClass(XRefWidget.class);
            // this.addWidgetClass(MetaWidget.class);
            this.addWidgetClass(EmailWidget.class);
            this.addWidgetClass(AnchorDeclarationWidget.class);
            this.addWidgetClass(AnchorMarkerWidget.class);
            // this.addWidgetClass(CollapsableWidget.class);
            // this.addWidgetClass(IncludeWidget.class);
            this.addWidgetClass(VariableDefinitionWidget.class);
            this.addWidgetClass(EvaluatorWidget.class);
            this.addWidgetClass(VariableWidget.class);
            this.addWidgetClass(HashWidget.class);
        }
    };
}
