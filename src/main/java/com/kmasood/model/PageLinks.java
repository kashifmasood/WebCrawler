package com.kmasood.model;

import java.util.HashSet;

/**
 * Model object to store statistics for each page visited.
 * As links are added to this object, the output string is created for easy printing
 *
 * @author kmasood
 *
 */
public class PageLinks {

  private String rootDomain;
  private HashSet<String> internalLinks;
  private HashSet<String> externalLinks;
  private HashSet<String> staticLinks;

  private String output; // Stores the output for printing

  private static final String ROOT_LABEL =     "-> ";
  private static final String INTERNAL_LABEL = "\n     internal link: ";
  private static final String EXTERNAL_LABEL = "\n     external link: ";
  private static final String IMAGE_LABEL =    "\n     image: ";

  public PageLinks(String rootDomainURL) {
    rootDomain = rootDomainURL;
    internalLinks = new HashSet<>();
    externalLinks = new HashSet<>();
    staticLinks = new HashSet<>();

    output = ROOT_LABEL + rootDomain;
  }

  /**
   * Adds a link to appropriate bucket
   * @param url - URL link in a page to record
   */
  public void addLink(String url) {
    if (url.equals(rootDomain)) {
      return;
    }

    // Check if we already have this
    if (!alreadyVisited(url) ) {
      if (!isExternalLink(url)) {
        internalLinks.add(url);
        output += INTERNAL_LABEL + url;
      } else {
        externalLinks.add(url);
        output += EXTERNAL_LABEL + url;
      }
    }
  }

  public void addStaticElement(String url) {
    staticLinks.add(url);
    output += IMAGE_LABEL + url;
  }

  /**
   * Helper method for determining if the url has already been visited or not
   * @param url - URL of the page to check
   * @return True if link has been visited
   */
  public boolean alreadyVisited(String url) {
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

  public String getRootDomain() {
    return rootDomain;
  }

  public boolean isExternalLink(String url) {
    return (!url.startsWith(rootDomain));
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

  @Override
  public String toString() {
    return output;
  }
}
