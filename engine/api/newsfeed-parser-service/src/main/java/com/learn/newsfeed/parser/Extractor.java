package com.learn.newsfeed.parser;
import com.learn.newsfeed.model.Document;
import net.sf.saxon.s9api.SaxonApiException;

import java.util.Map;

public interface Extractor {
  Document extract() throws SaxonApiException;
}
