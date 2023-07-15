package org.specialiststeak.scraper.endpoint;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static org.specialiststeak.scraper.utils.GetHTMLUtil.getHTML;

public final class AnimePlanetLinkFinder {
    private static final String ANIME_PLANET_SEARCH_URL_ANIME = "https://www.anime-planet.com/anime/all?name=";
    private static final String ANIME_PLANET_SEARCH_URL_MANGA = "https://www.anime-planet.com/manga/all?name=";

    private static final String RESULTS_CODE_ANIME = "<ul class=\"cardDeck cardGrid\" data-type=\"anime\">";
    private static final String RESULTS_CODE_MANGA = "<ul class=\"cardDeck cardGrid\" data-type=\"manga\">";

    private static final String MANGA_BASE_URL = "https://www.anime-planet.com/manga";
    private static final String ANIME_BASE_URL = "https://www.anime-planet.com/anime";

    public static void main(String[] args) throws Exception {
        System.out.println(searchAnime("naruto", false));
        System.out.println(searchManga("drug eating genius mage", false));
        System.out.println(getFirstGoogleSearchResultLinkAnime("chainsaw man"));
        System.out.println(getFirstGoogleSearchResultLinkManga("chainsaw man"));
    }

    public static String searchManga(String query, boolean googleFallback) throws Exception {
        query = cleanQuery(query);
        String url = ANIME_PLANET_SEARCH_URL_MANGA + query;
        String HTML = getHTML(url);
        if(HTML.contains("If you like this manga, you might like...")){
            int resultIndex = HTML.indexOf("<h1 itemprop=\"name\">");
            HTML = HTML.substring(resultIndex + 20);
            int endIndex = HTML.indexOf("</h1>");
            String name = HTML.substring(0, endIndex);
            name = name.replaceAll(" ", "-");
            name = name.toLowerCase();
            url = MANGA_BASE_URL + "/" + name;
            return url;
        }
        int resultIndex = HTML.indexOf(RESULTS_CODE_MANGA);
        if (resultIndex == -1 && googleFallback) {
            return getFirstGoogleSearchResultLinkManga(query);
        }
        HTML = HTML.substring(resultIndex);
        int linkIndex = HTML.indexOf("href=\"");
        HTML = HTML.substring(linkIndex + 12);
        int linkEndIndex = HTML.indexOf("\"");
        String link = MANGA_BASE_URL + HTML.substring(0, linkEndIndex);
        return link;
    }

    public static String searchAnime(String query, boolean googleFallback) throws Exception {
        query = cleanQuery(query);
        String url = ANIME_PLANET_SEARCH_URL_ANIME + query;
        String HTML = getHTML(url);
        if(HTML.contains("If you like this anime, you might like...")){
            int resultIndex = HTML.indexOf("<h1 itemprop=\"name\">");
            HTML = HTML.substring(resultIndex + 20);
            int endIndex = HTML.indexOf("</h1>");
            String name = HTML.substring(0, endIndex);
            name = name.replaceAll(" ", "-");
            name = name.toLowerCase();
            url = ANIME_BASE_URL + "/" + name;
            return url;
        }
        int resultIndex = HTML.indexOf(RESULTS_CODE_ANIME);
        if (resultIndex == -1 && googleFallback) {
            return getFirstGoogleSearchResultLinkAnime(query);
        }
        HTML = HTML.substring(resultIndex);
        int linkIndex = HTML.indexOf("href=\"");
        HTML = HTML.substring(linkIndex + 12);
        int linkEndIndex = HTML.indexOf("\"");
        String link = ANIME_BASE_URL + HTML.substring(0, linkEndIndex);
        return link;
    }

    private static String cleanQuery(String query) {
        while(query.contains("  ")) {
            query = query.replace("  ", " ");
        }
        query = query.trim();
        query = query.replaceAll(" ", "%20");
        return query;
    }

    public static String getFirstGoogleSearchResultLinkAnime(String query) throws Exception {
        Document connection = Jsoup.connect("https://www.google.com/search?q=site:anime-planet.com+" + query
                .replaceAll("  ", " ")
                .replaceAll(" ", "-")
                .trim() + "+anime").get();
        Elements groups = connection.getElementsByClass("g");
        if (groups.size() <= 0) {
            return null;
        }
        Element firstGroup = groups.first();
        String href = firstGroup.getElementsByTag("a").first().attr("href");
        return href;
    }

    public static String getFirstGoogleSearchResultLinkManga(String query) throws Exception {
        Document connection = Jsoup.connect("https://www.google.com/search?q=site:anime-planet.com+" + query
                .replaceAll("  ", " ")
                .replaceAll(" ", "-")
                .trim() + "+manga").get();
        Elements groups = connection.getElementsByClass("g");
        if (groups.size() <= 0) {
            return null;
        }
        Element firstGroup = groups.first();
        String href = firstGroup.getElementsByTag("a").first().attr("href");
        return href;
    }
}
