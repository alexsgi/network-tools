package network.news;

public class News {

    private String sourceId, sourceName, author, title, description, url, urlToImage, publishedAt, content;

    public News(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content) {
        this.sourceId = sourceId;
        this.sourceName = sourceName;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }

    public String getSourceId() {
        return sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

}