package com.kmasood.model;

import java.util.HashSet;

public class PageLinks {

  private HashSet<String> internalLinks;
  private HashSet<String> externalLinks;
  private HashSet<String> staticLinks;

  public PageLinks() {
    internalLinks = new HashSet<>();
    externalLinks = new HashSet<>();
    staticLinks = new HashSet<>();
  }

  public void addLink(String url) {

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
