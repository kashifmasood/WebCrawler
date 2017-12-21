package com.kmasood.crawler;

import com.kmasood.model.PageLinks;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple WebCrawler class to visit all the pages within the same domain from a given URL
 * Results of each page are saved in a PageList object which can be used to print/process results.
 *
 * @author kmasood
 *
 */
public class Crawler {

  // This is where we will store data for each page we visit
  private List<PageLinks> pageLinksList;

  /**
   * Constructor
   * @param rootDomainURL - URL of the page to start crawl from
   */
  public Crawler(String rootDomainURL) {
    pageLinksList = new ArrayList<>();
    parsePage(rootDomainURL);
  }

  /**
   * As the crawler visits each page, it will call this method to store page data
   * @param pageLinks
   */
  private void recordPageData(PageLinks pageLinks) {
    pageLinksList.add(pageLinks);
    System.out.println(pageLinks.toString());
  }

  public List<PageLinks> getPageLinksList() {
    return pageLinksList;
  }

  /**
   * This is the main method which parses each page and records any links in that page.
   * If it finds a link within the domain, it will call itself recursively and continue processing.
   * This mnethod uses the jsoup library to parse the HTML contents of the page
   *
   * @param pageURL - URL of the page to visit
   */
  private void parsePage(String pageURL) {
    PageLinks pageLinks = new PageLinks(pageURL);

    // Make sure we haven't visited this page already
    if (!pageLinks.alreadyVisited(pageURL)) {

      try {
        pageLinks.addLink(pageURL);

        Document pageDocument = Jsoup.connect(pageURL).get();

        // Parse static contents first
        Elements staticElements = pageDocument.select("img[src~=(?i)]");

        for (Element staticElement : staticElements) {
          String link = staticElement.attr("src");

          pageLinks.addStaticElement(link);
        }

        // Now parse all links
        Elements pageElements = pageDocument.select("a[href]");

        for (Element element : pageElements) {
          String link = element.attr("abs:href");

          pageLinks.addLink(link);

          //
          // Visit the link if its internal to the domain only
          //
          if (!pageLinks.isExternalLink(link)) {
            // Perform some minor checks before crawling...
            // Strip away any anchor tags at the end of a URL to avoid parsing the page more than once
            if (link.contains("#")) {
              link = link.substring(0, link.indexOf('#'));
            }

            // Don't crawl the main root page again.
            // If we don't check for this, it will cause an infinite loop (in case of anchor tags)
            if (!pageLinks.getRootDomain().equals(link)) {
              parsePage(link);
            }
          }
        }

        // Don't forget to save the page information
        this.recordPageData(pageLinks);

      } catch (IOException e) {
        System.err.println("Error parsing page: " + pageURL + " - " + e.getMessage());
      }
    }
  }

  /**
   * Main driver method
   * @param args - One argument only = URL to start crawling from
   */
  public static void main(String[] args) {
    if (args.length > 0) {
      new Crawler(args[0]);
    } else {
      System.err.println("Please provide a valid website URL as a parameter.\nExample: Crawler.main http://google.com");
    }
  }
}
