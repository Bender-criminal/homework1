package ru.digitalhabbits.homework1.service;

import com.google.gson.Gson;
import org.apache.http.client.utils.URIBuilder;

import javax.annotation.Nonnull;
import java.net.URI;
import java.net.URISyntaxException;

public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);
        // TODO: NotImplemented
        String plainText = "{\"batchcomplete\":\"\",\"query\":{\"pages\":{\"673381\":{\"pageid\":673381,\"ns\":0,\"title\":\"New York\",\"extract\":\"New York most commonly refers to:\\n\\nNew York City, the most populous city in the United States, located in the state of New York\\nNew York (state), a state in the Northeastern United StatesNew York may also refer to:\\n\\n\\n== Film and television ==\\nNew York (1916 film), a lost American silent comedy drama by George Fitzmaurice\\nNew York (1927 film), an American silent drama by Luther Reed\\nNew York (2009 film), a Bollywood film by Kabir Khan\\nNew York: A Documentary Film, a film by Ric Burns\\n\\\"New York\\\" (Glee), an episode of Glee\\n\\n\\n== Literature ==\\nNew York (Anthony Burgess book), a 1976 work of travel and observation\\nNew York (Morand book), a 1930 travel book by Paul Morand\\nNew York (novel), a 2009 historical novel by Edward Rutherfurd\\nNew York (magazine), a bi-weekly magazine founded in 1968\\n\\n\\n== Music ==\\nNew York EP, a 2012 EP by Angel Haze\\n\\\"New York\\\" (Angel Haze song)\\nNew York (album), a 1989 album by Lou Reed\\n\\\"New York\\\" (Eskimo Joe song) (2007)\\n\\\"New York\\\" (Ja Rule song) (2004)\\n\\\"New York\\\" (Paloma Faith song) (2009)\\n\\\"New York\\\" (St. Vincent song) (2017)\\n\\\"New York\\\" (Snow Patrol song) (2011)\\n\\\"New York\\\" (U2 song) (2000)\\nNew York, a 2006 album by Antti Tuisku\\n\\\"New York\\\", a 1977 song by the Sex Pistols from Never Mind the Bollocks, Here's the Sex Pistols\\n\\n\\n== Places ==\\n\\n\\n=== United Kingdom ===\\nNew York, Lincolnshire\\nNew York, North Yorkshire\\nNew York, Tyne and Wear\\n\\n\\n=== United States ===\\n\\n\\n==== New York state ====\\nNew York metropolitan area, the region encompassing New York City and its suburbs\\nNew York County, covering the same area as the New York City borough of Manhattan\\nNew York, the U.S. Postal Service address designating Manhattan\\nProvince of New York, a British colony preceding the state of New York\\n\\n\\n==== Other states ====\\nNew York, Florida, an unincorporated community in Santa Rosa County\\nNew York, Iowa, an unincorporated community in Wayne County\\nNew York, Kentucky, an unincorporated community in Ballard County\\nNew York, Missouri, a ghost town in Scott County\\nNew York, Texas, an unincorporated community in Henderson County\\nNew York Mountain, a mountain in Colorado\\nNew York Mountains, a mountain range in California\\n\\n\\n== Ships ==\\nMany ships have been named after the city or state of New York. See:\\n\\nList of ships named New York \\nList of ships named City of New York\\nList of ships named New York City\\n\\n\\n== Sports ==\\n\\n\\n=== American football ===\\nNew York Giants, members of the East Division of the National Football Conference of the NFL (1925)\\nNew York Jets, members of the East Division of the American Football Conference of the NFL (1960)\\nNew York (World Series of Football), a professional football team for the World Series of Football (1902\\u20131903)\\n\\n\\n=== Baseball ===\\nNew York Mets, members of the East Division of the National League of MLB (1962)\\nNew York Yankees, members of the East Division of the American League of MLB (1903)\\n\\n\\n=== Hockey ===\\nNew York Islanders, members of the Metropolitan Division of the Eastern Conference of the NHL (1972)\\nNew York Rangers, members of the Metropolitan Division of the Eastern Conference of the NHL (1926)\\n\\n\\n=== Soccer ===\\nNew York City FC, a professional soccer team based in New York City that competes in the Eastern Conference of MLS (2015)\\nNew York Red Bulls, a professional soccer team that competes in the Eastern Conference of MLS (1996)\\nNew York Stadium in South Yorkshire, home ground of Rotherham United F.C.\\n\\n\\n=== Other sports ===\\nNew York GAA, a county board of the Gaelic Athletic Association outside Ireland, responsible for Gaelic games in the New York metropolitan area\\nNew York Knicks, a professional basketball team, part of the Atlantic Division of the Eastern Conference in the NBA\\n\\n\\n== Other uses ==\\nNew York (pinball), a 1976 pinball machine by Gottlieb\\nNew York (typeface), a 1983 Macintosh font\\nNew York Harbor, a waterfront in New York City\\nBrooklyn Navy Yard, referred to as New York in naval histories\\nTiffany Pollard or New York (born 1982), star of the reality TV show I Love New York\\n\\n\\n== See also ==\\nNew York City (disambiguation)\\nNew York Cosmos (disambiguation)\\nNew York, New York (disambiguation)\\nNova Iorque, Brazilian municipality in the state of Maranh\\u00e3o\\nNowy Jork, former name of \\u0141agiewniki, W\\u0142oc\\u0142awek County, Poland\\nNY (disambiguation)\\nSS New York, a list of ships\\nSS New York City, a list of ships\\nUSS New York, a list of United States Navy ships and submarines\\nAll pages with titles beginning with New York\\nAll pages with titles containing New York\"}}}}";
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

}
