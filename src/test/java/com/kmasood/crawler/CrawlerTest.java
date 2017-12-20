package com.kmasood.crawler;

import com.kmasood.model.PageLinks;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CrawlerTest {

  private Crawler crawler;

  @Before
  public void setUp() throws Exception {
    crawler = new Crawler();
  }

  @Test
  public void testWebsite1() {
    crawler.parsePage("https://kashifmasood.github.io/WebCrawler/html/SamplePage1.html");

    PageLinks pagelinks  = crawler.getPageLinks();

    assertEquals("External Links", 5, pagelinks.getExternalLinks().size());
    assertEquals("Internal Links", 0, pagelinks.getInternalLinks().size());
    assertEquals("Static Links", 3, pagelinks.getStaticLinks().size());
  }

  @Test
  public void testWebsite2() {
    crawler.parsePage("https://kashifmasood.github.io/WebCrawler/html/SamplePage2.html");

    PageLinks pagelinks  = crawler.getPageLinks();

    assertEquals("External Links",2, pagelinks.getExternalLinks().size());
    assertEquals("Internal Links",1, pagelinks.getInternalLinks().size());
    assertEquals("Static Links",2, pagelinks.getStaticLinks().size());
  }
}