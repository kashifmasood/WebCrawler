package com.kmasood.crawler;

import com.kmasood.model.PageLinks;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Simple WebCrawler class to visit all the pages within the same domain from a given URL
 * Results of each page are saved in a PageList object which can be used to print/process results.
 *
 * @author kmasood
 *
 */
public class Crawler {
  public static String DOMAIN;

  private HashSet<String> visitedURLs;

  // This is where we will store data for each page we visit
  private List<PageLinks> pageLinksList;

  /**
   * Constructor
   * @param rootDomainURL - URL of the page to start crawl from
   */
  public Crawler(String rootDomainURL) {
    // Set the starting point domain
    DOMAIN = extractDomainName(rootDomainURL);
    visitedURLs = new HashSet<String>();
    pageLinksList = new ArrayList<>();
    parsePage(rootDomainURL);
  }

  /**
   * Helper function to extract the domain name from the given URL
   * @param url - url to extract domain name from
   * @return domain name
   */
  private String extractDomainName(String url) {
    String domainName = url;

    // Lets get passed the http:// or https:// first
    int slashslash = url.indexOf("//") + 2;
    int indexOfSlash = url.indexOf('/', slashslash);

    if (indexOfSlash > 0) {
      domainName = url.substring(0, url.indexOf('/', slashslash));
    }

    return domainName;
  }

  /**
   * As the crawler visits each page, it will call this method to store page data
   * @param pageLinks
   */
  private void recordPageData(PageLinks pageLinks) {
    pageLinksList.add(pageLinks);
//    System.out.println(pageLinks.toString());
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
    if (!visitedURLs.contains(pageURL)) {

      try {
        pageLinks.addLink(pageURL);
        visitedURLs.add(pageURL);

        Document pageDocument = Jsoup.connect(pageURL).get();

        // Parse static contents first
        Elements staticElements = pageDocument.select("img[src~=(?i)]"); // TODO: fix this

        for (Element staticElement : staticElements) {
          String link = staticElement.attr("src");

          pageLinks.addStaticElement(link);
        }

        // Now parse all links
        Elements pageElements = pageDocument.select("a[href]");

        for (Element element : pageElements) {
          String link = element.attr("abs:href");

          pageLinks.addLink(link);

          // Perform some minor checks before crawling...
          // Strip away any anchor tags or parameters at the end of a URL to avoid incorrect identification
          if (link.contains("#")) {
            link = link.substring(0, link.indexOf('#'));
          } else if (link.contains("?")) {
            link = link.substring(0, link.indexOf('?'));
          }

          //
          // Visit the link if its internal to the domain only
          //
          if (!pageLinks.isExternalLink(link)) {
            // Don't crawl the main root page again.
            // If we don't check for this, it will cause an infinite loop (in case of anchor tags)
            if (!pageLinks.getPageURL().equals(link)) {
              parsePage(link);
            }
          }
        }

        // Don't forget to save the page information
        this.recordPageData(pageLinks);

      } catch (Exception e) {
        System.err.println("Error visiting URL " + pageURL + " - " + e.toString());
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
      System.err.println("Please provide a fully formed URL as a parameter.\nExample: http://google.com");
    }
  }
}
