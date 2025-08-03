package com.learn.newsfeed.parser;

import com.learn.newsfeed.model.Article;
import com.learn.newsfeed.model.DocMeta;
import com.learn.newsfeed.model.Document;
import com.learn.newsfeed.util.XPathConstants;
import net.sf.saxon.s9api.*;

import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SaxonExtractor implements Extractor {

    private final XPathCompiler xpathCompiler;
    private final XdmNode document;

    public SaxonExtractor(SaxonExtractorBuilder builder) {
        this.xpathCompiler = builder.processor.newXPathCompiler();
        this.document = builder.document;
    }

    public static class SaxonExtractorBuilder {
        private Processor processor;
        private XdmNode document;

        public SaxonExtractorBuilder(){
            this.processor = new Processor(false);
        }

        public SaxonExtractorBuilder processor(Processor processor){
            this.processor = processor;
            return this;
        }

        public SaxonExtractorBuilder fromFile(File xmlFile) throws SaxonApiException {
            DocumentBuilder builder = processor.newDocumentBuilder();
            this.document = builder.build(new StreamSource(xmlFile));
            return this;
        }

        public SaxonExtractorBuilder fromStreamSource(StreamSource source) throws SaxonApiException {
            DocumentBuilder builder = processor.newDocumentBuilder();
            this.document = builder.build(source);
            return this;
        }

        public SaxonExtractorBuilder fromStringSource(String xmlContent) throws SaxonApiException {
            DocumentBuilder builder = processor.newDocumentBuilder();
            this.document = builder.build(new StreamSource(new StringReader(xmlContent)));
            return this;
        }

        public SaxonExtractorBuilder fromBytes(byte[] xmlBytes) throws SaxonApiException {
            DocumentBuilder builder = processor.newDocumentBuilder();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(xmlBytes);
            this.document = builder.build(new StreamSource(inputStream));
            return this;
        }

        public SaxonExtractor build(){
            if (document == null) {
                throw new IllegalStateException("Document must be provided before building SaxonExtractor");
            }
            return new SaxonExtractor(this);
        }
    }

    @Override
    public Document extract() throws SaxonApiException {
        DocMeta docMeta = extractDocmeta();
        List<Article> articles = extractArticles();

        return new Document(docMeta,articles);
    }

    private DocMeta extractDocmeta() throws SaxonApiException {
        String docId = extractField(XPathConstants.DOC_ID);
        String publicationDate = extractField(XPathConstants.PUBLICATION_DATE);
        String copyright = extractField(XPathConstants.COPYRIGHT);
        String sourceTitle = extractField(XPathConstants.SOURCE_TITLE);

        return new DocMeta("",docId,publicationDate,copyright,sourceTitle);
    }

    private List<Article> extractArticles() throws SaxonApiException {
        List<Article> articleList = new ArrayList<>();
        XPathSelector articleSelector = xpathCompiler.compile(XPathConstants.ARTICLE_ROOT).load();
        articleSelector.setContextItem(document);
        XdmValue articles = articleSelector.evaluate();

        for (XdmItem item : articles) {
            XdmNode articleNode = (XdmNode) item;

            String id = extractField(XPathConstants.ARTICLE_ID, articleNode);
            String headline = extractField(XPathConstants.ARTICLE_HEADLINE, articleNode);
            String articleLink = extractField( XPathConstants.ARTICLE_LINK, articleNode);
            String summary = extractField(XPathConstants.ARTICLE_SUMMARY, articleNode);
            String publicationDate = extractField(XPathConstants.ARTICLE_PUBLICATION_DATE, articleNode);
            List<String> authors =extractListField(XPathConstants.ARTICLE_AUTHORS, articleNode);
            List<String> categories =extractListField(XPathConstants.ARTICLE_CATEGORIES, articleNode);
            articleList.add(new Article(id,headline,articleLink,summary,publicationDate,authors,categories));
        }

        return articleList;
    }



    private String extractField(String xpath) throws SaxonApiException {
        XPathSelector selector = xpathCompiler.compile(xpath).load();
        selector.setContextItem(document);
        XdmValue value = selector.evaluate();
        return value.size() > 0 ? value.itemAt(0).getStringValue() : null;
    }

    private String extractField(String xpath, XdmNode context) throws SaxonApiException {
        XPathSelector selector = xpathCompiler.compile(xpath).load();
        selector.setContextItem(context);
        XdmValue value = selector.evaluate();
        return value.size() > 0 ? value.itemAt(0).getStringValue() : null;
    }

    private List<String> extractListField(String xpath, XdmNode context) throws SaxonApiException {
        XPathSelector selector = xpathCompiler.compile(xpath).load();
        selector.setContextItem(context);
        XdmValue values = selector.evaluate();

        List<String> result = new ArrayList<>();
        for (XdmItem item : values) {
            result.add(item.getStringValue());
        }
        return result;
    }

}
