package com.example.custom_listview;

public class News {

    public News(String path, String title, String excerpt, String sourceUrl, String webUrl, String originalUrl, String featuredContent, String highlight, String publishedDateTime) {
        this.path = path;
        this.title = title;
        this.excerpt = excerpt;
        this.sourceUrl = sourceUrl;
        this.webUrl = webUrl;
        this.originalUrl = originalUrl;
        this.featuredContent = featuredContent;
        this.highlight = highlight;
        this.publishedDateTime = publishedDateTime;
    }

    String path;
    String title;
    String excerpt;
    String sourceUrl;
    String webUrl;
    String originalUrl;
    String featuredContent;
    String highlight;
    String publishedDateTime;
}
