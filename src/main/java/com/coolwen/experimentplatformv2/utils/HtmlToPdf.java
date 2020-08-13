package com.coolwen.experimentplatformv2.utils;

import com.lowagie.text.pdf.BaseFont;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @author 朱治汶
 * @version 1.0
 * @date 2020/7/24 17:35
 **/
public class HtmlToPdf {

    protected static final Logger logger = LoggerFactory.getLogger(HtmlToPdf.class);

    public static void topdf(String content, String path) throws Exception {
        ITextRenderer renderer = new ITextRenderer();
        ITextFontResolver fontResolver = renderer.getFontResolver();
        //设置字体，否则不支持中文,在html中使用字体，html{ font-family: SimSun;}
        fontResolver.addFont("static/fonts/simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        renderer.setDocumentFromString(content);
        renderer.layout();
        renderer.createPDF(new FileOutputStream(new File(path)));
    }
}