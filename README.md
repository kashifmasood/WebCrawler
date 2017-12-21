# Simple WebCrawler

A simple Java web crawler application. Given a starting URL, the application visits all pages within the domain, 
but does not follow any external web sites.

As each URL is visited, the following information is captured and printed:

- Links to other pages under the same domain
- Links to external URLs
- Static links to images 

## Build / Run

To build the application:

```
cd <<project directory>>
```

```
./gradlew clean build
```

To run the application, pass in the URL of the website you want to start crawling from using the '-Purl' argument: 
<br>(make sure to pass in the full URL including http://)

```
./gradlew run -Purl='http://cnn.com'
```

## Third-party Dependencies

The application code utlizes [jsoup](https://jsoup.org) library to easily parse web page contents.


## Description
This application demostrates a simple approach to crawl websites.
 
Although the application currently is restricted to visit pages only under the given domain, it could be extended 
to visit external URL's provided that the code is enhanced to limit the number of websites to visit.
 
The appication utilizes the jsoup libraries to parse html contents for easy implementation. The solution could be
implemented with standard java libraries, at the expense of additional hundreds of lines of code and complexity. 

This is a simple command line java application which is meant for demonstration purposes only. A more scalable production 
ready version of such an application could include some the following features:
- Multi-threaded processing 
- Awareness of Robots.txt on each website to respect exclusion standard
- Service based design to allow for scalability and reuse in a broader architecture environment
