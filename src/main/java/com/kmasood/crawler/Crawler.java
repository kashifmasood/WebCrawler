package com.kmasood.crawler;

import com.kmasood.model.PageLinks;

public class Crawler {

  private PageLinks pageLinks;

  public Crawler() {
    pageLinks = new PageLinks();
  }

  public PageLinks getPageLinks() {
    return pageLinks;
  }

  public void parsePage(String pageURL) {
  }

  public static void main(String[] args) {
    if (args.length > 0) {
      new Crawler().parsePage(args[0]);
    }
  }
}
