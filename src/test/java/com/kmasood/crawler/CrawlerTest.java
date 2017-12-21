package com.kmasood.crawler;

import com.kmasood.model.PageLinks;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CrawlerTest {

  /**
   * This test is to ensure that we are parsing the contents of a page correctly by identifying the correct number
   * of external and static links. No internal links are added to the SamplePage1 since we will test that in a
   * later test case for crawling depth of a page
   */
  @Test
  public void testSinglePage1() {
    Crawler crawler = new Crawler("https://kashifmasood.github.io/WebCrawler/html/SamplePage1");

    List<PageLinks> list = crawler.getPageLinksList();

    assertEquals("# Of Pages", 1, list.size());

    PageLinks pageLinks = list.get(0);
    assertNotNull("No page data found", pageLinks);
    assertEquals("External Links", 2, pageLinks.getExternalLinks().size());
    assertEquals("Internal Links", 0, pageLinks.getInternalLinks().size());
    assertEquals("Static Links", 3, pageLinks.getStaticLinks().size());
  }

  /**
   * This test is to ensure that we are parsing the contents of a page correctly by identifying the correct number
   * of external and static links. No internal links are added to the SamplePage1 since we will test that in a
   * later test case for crawling depth of a page
   */
  @Test
  public void testSinglePage2() {
    Crawler crawler = new Crawler("https://kashifmasood.github.io/WebCrawler/html/SamplePage2");

    List<PageLinks> list = crawler.getPageLinksList();

    assertEquals("# Of Pages", 1, list.size());

    PageLinks pageLinks = list.get(0);
    assertNotNull("No page data found", pageLinks);
    assertEquals("External Links",3, pageLinks.getExternalLinks().size());
    assertEquals("Internal Links",3, pageLinks.getInternalLinks().size());
    assertEquals("Static Links",2, pageLinks.getStaticLinks().size());
  }

  /**
   * This is where we test whether the code correctly crawls to all the internal links.
   * Each page we visit should create a corrosponding PageLinks object with its data so
   * the number of PageLinks will confirm the depth of pages we visited
   */
  @Test
  public void testPageDepth() {
    Crawler crawler = new Crawler("https://kashifmasood.github.io/WebCrawler/html/");

    List<PageLinks> list = crawler.getPageLinksList();

    assertEquals("# Of Pages", 2, list.size());
  }
}