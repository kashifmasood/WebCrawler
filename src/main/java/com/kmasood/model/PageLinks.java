package com.kmasood.model;

import java.util.HashSet;

public class PageLinks {

  private String rootDomain;
  private HashSet<String> internalLinks;
  private HashSet<String> externalLinks;
  private HashSet<String> staticLinks;

  public PageLinks(String rootDomainURL) {
    rootDomain = rootDomainURL;
    internalLinks = new HashSet<>();
    externalLinks = new HashSet<>();
    staticLinks = new HashSet<>();
  }

  public void addLink(String url) {
    if (!isExternalLink(url)) {
      internalLinks.add(url);
    } else {
      externalLinks.add(url);
    }
  }

  public void addStaticElement(String url) {
    staticLinks.add(url);
  }

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

  public boolean isExternalLink(String url) {
    return (url.startsWith(rootDomain) ? false : true);
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
