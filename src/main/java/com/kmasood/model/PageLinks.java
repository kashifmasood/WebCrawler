package com.kmasood.model;

import com.kmasood.crawler.Crawler;

import java.util.HashSet;

/**
 * Model object to store statistics for each page visited.
 * As links are added to this object, the output string is created for easy printing
 *
 * @author kmasood
 *
 */
public class PageLinks {

  private String pageUrl;
  private HashSet<String> internalLinks;
  private HashSet<String> externalLinks;
  private HashSet<String> staticLinks;

  private static final String ROOT_LABEL =     "-> ";
  private static final String INTERNAL_LABEL = "     internal link: ";
  private static final String EXTERNAL_LABEL = "     external link: ";
  private static final String IMAGE_LABEL =    "     image: ";

  public PageLinks(String url) {
    pageUrl = url;
    internalLinks = new HashSet<>();
    externalLinks = new HashSet<>();
    staticLinks = new HashSet<>();

    System.out.println(ROOT_LABEL + pageUrl);
  }

  /**
   * Adds a link to appropriate bucket
   * @param url - URL link in a page to record
   */
  public void addLink(String url) {
    if (url.equals(pageUrl)) {
      return;
    }

    // Check if we already have this
    if (!alreadyRecorded(url) ) {
      if (!isExternalLink(url)) {
        internalLinks.add(url);
        System.out.println(INTERNAL_LABEL + url);
      } else {
        externalLinks.add(url);
        System.out.println(EXTERNAL_LABEL + url);
      }
    }
  }

  public void addStaticElement(String url) {
    staticLinks.add(url);
    System.out.println(IMAGE_LABEL + url);
  }

  /**
   * Helper method for determining if the url has already been seen and we have it recorded
   * @param url - URL of the page to check
   * @return True if link has been recorded
   */
  public boolean alreadyRecorded(String url) {
    boolean foundIt = false;

    if (internalLinks.contains(url)) {
      foundIt = true;
    } else if (externalLinks.contains(url)) {
      foundIt = true;
    } else if (staticLinks.contains(url)) {
      foundIt = true;
    }

    return foundIt;
  }

  public String getPageURL() {
    return pageUrl;
  }

  public boolean isExternalLink(String url) {

    boolean t = !url.startsWith(Crawler.DOMAIN);
     return (!url.startsWith(Crawler.DOMAIN));
  }

  public HashSet<String> getInternalLinks() {
    return internalLinks;
  }

  public HashSet<String> getExternalLinks() {
    return externalLinks;
  }

  public HashSet<String> getStaticLinks() {
    return staticLinks;
  }
}
