package com.kmasood.crawler;

import com.kmasood.model.PageLinks;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Crawler {

  private PageLinks pageLinks;

  public Crawler(String rootDomainURL) {
    pageLinks = new PageLinks(rootDomainURL);
    parsePage(rootDomainURL);
  }

  public PageLinks getPageLinks() {
    return pageLinks;
  }

  public void parsePage(String pageURL) {
    // Make sure we haven't visited this page already
    if (!pageLinks.alreadyVisited(pageURL)) {

      try {
        pageLinks.addLink(pageURL);
        System.out.println(pageURL);

        Document pageDocument = Jsoup.connect(pageURL).get();

        // Parse static contents first
        Elements staticElements = pageDocument.select("img[src~=(?i)]");

        for (Element staticElement : staticElements) {
          String link = staticElement.attr("src");

          pageLinks.addStaticElement(link);
          System.out.println(link);
        }

        // Now parse all links
        Elements pageElements = pageDocument.select("a[href]");

        for (Element element : pageElements) {
          String link = element.attr("abs:href");

          pageLinks.addLink(link);
          System.out.println(link);

          //
          // Visit the link if its internal to the domain
          //
          if (!pageLinks.isExternalLink(link)) {
            parsePage(link);
          }
        }

      } catch (IOException e) {
        System.err.println("Error parsing page: " + pageURL + " - " + e.getMessage());
      }
    }
  }

  public static void main(String[] args) {
    if (args.length > 0) {
      new Crawler(args[0]);
    } else {
      System.err.println("Please provide a valid website URL as a parameter.\nExample: Crawler.main http://google.com");
    }
  }
}
