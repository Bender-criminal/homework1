package ru.digitalhabbits.homework1.service;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.slf4j.LoggerFactory.getLogger;

public class WikipediaClient {
    private static final Logger logger = getLogger(WikipediaClient.class);

    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);

        String plainText = getPlainText(uri);
        return getArticle(plainText);
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }

    private String getArticle(String plainText){
        String text = "";

        Gson gson = new Gson();
        WikiArticle article = gson.fromJson(plainText, WikiArticle.class);

        text = article.query.pages.entrySet().iterator().next().getValue().extract;

        return text.replaceAll("\\\\n", "\n").toLowerCase();
    }

    @Nonnull
    private String getPlainText(@Nonnull URI uri){

        HttpGet request = new HttpGet(uri);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // return it as a String
                return EntityUtils.toString(entity);
            }

        } catch (ClientProtocolException e) {
            logger.error("Получение инфо из Wiki: ошибка протокола HTTP", e);
            return "";
        } catch (IOException e) {
            logger.error("Получение инфо из Wiki: ошибка ввода-вывода", e);
            return "";
        }

        return "";
    }

}
