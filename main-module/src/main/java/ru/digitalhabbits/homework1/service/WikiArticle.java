package ru.digitalhabbits.homework1.service;
import java.util.Map;

public class WikiArticle {
    public String batchcomplete;
    public Query query;


    public class Query{
        public Map<String, Page> pages;
    }

    public class Page{
        public int pageid;
        public int ns;
        public String title;
        public String extract;
    }
}
