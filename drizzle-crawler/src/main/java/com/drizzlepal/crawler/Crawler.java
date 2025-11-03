package com.drizzlepal.crawler;

public interface Crawler<T> {

    T crawl() throws Exception;

}
