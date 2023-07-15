package org.specialiststeak.scraper.endpoint;

import org.specialiststeak.scraper.utils.FileUtils;
import org.specialiststeak.scraper.utils.GetHTMLUtil;

import java.io.IOException;
import java.util.*;

import static org.specialiststeak.scraper.utils.GsonUtils.writeHashMapToFile;

public final class AnimePlanetLinkScraperToJsonFile {
    public static void main(String[] args) throws InterruptedException, IOException {
        var x = scrapeMangaLink(1500);
        var y = scrapeAnimeLink(1500);
        HashMap<String, String> xx = new HashMap<>();
        HashMap<String, String> yy = new HashMap<>();
        for (String s : x) {
            String name = s.replace("https://www.anime-planet.com/anime/", "");
            name = name.replace("-", " ");
            name = name.replace("/", "");
            xx.put(name, s);
        }
        for (String s : y) {
            String name = s.replace("https://www.anime-planet.com/anime/", "");
            name = name.replace("-", " ");
            name = name.replace("/", "");
            yy.put(name, s);
        }
        writeHashMapToFile(xx, "src/main/resources/anime-planet-anime.json");
        writeHashMapToFile(yy, "src/main/resources/anime-planet-manga.json");
        FileUtils.readAndWrite(
                "src/main/resources/anime-planet-anime.json",
                "src/main/resources/anime-planet-anime-cleaned.json",
                "all?page\\u003d"
        );
        FileUtils.readAndWrite("src/main/resources/anime-planet-manga.json",
                "src/main/resources/anime-planet-manga-cleaned.json",
                "all?page\\u003d"
        );
    }

    private static List<String> scrapeMangaLink(int sleepTime) throws InterruptedException {
        int START_PAGE = 1;
        final int PAGES = 2371;
        Set endpoints = new HashSet<>();
        String manga_url = "https://www.anime-planet.com/manga/";
        String anime_planet_url = "https://anime-planet.com/manga/all?page=";
        for (; START_PAGE <= PAGES; START_PAGE++) {
            long begin = System.currentTimeMillis(), end, totalSum = 0, iterations = 0;
            System.out.print("Scanning page " + START_PAGE + " of " + PAGES + ".    ");
            System.out.print("Current URL: " + anime_planet_url + START_PAGE + "    ");
            String html = GetHTMLUtil.getHTML(anime_planet_url + START_PAGE);
            String[] split = html.split("href=\"/manga/");
            for (int j = 1; j < split.length; j++) {
                String[] split2 = split[j].split("\"");
                String endpoint = split2[0];
                endpoints.add(manga_url + endpoint);
            }
            end = System.currentTimeMillis();
            System.out.println("Time elapsed: " + (end - begin) + " milliseconds.");
            if (end - begin <= 600) {
                System.out.println("WARNING! CLOUDFLARE MAY HAVE BLOCKED YOU!");
            }
            Thread.sleep(sleepTime);
        }
        return new ArrayList<>(endpoints);
    }

    private static List<String> scrapeAnimeLink(int sleepTime) throws InterruptedException {
        int START_PAGE = 1;
        final int PAGES = 654;
        Set endpoints = new HashSet<>();
        String anime_url = "https://www.anime-planet.com/anime/";
        String anime_planet_url = "https://anime-planet.com/anime/all?page=";
        for (; START_PAGE <= PAGES; START_PAGE++) {
            long begin = System.currentTimeMillis(), end;
            System.out.print("Scanning page " + START_PAGE + " of " + PAGES + ".    ");
            System.out.print("Current URL: " + anime_planet_url + START_PAGE + "    ");
            String html = GetHTMLUtil.getHTML(anime_planet_url + START_PAGE);
            String[] split = html.split("href=\"/anime/");
            for (int j = 1; j < split.length; j++) {
                String[] split2 = split[j].split("\"");
                String endpoint = split2[0];
                endpoints.add(anime_url + endpoint);
            }
            end = System.currentTimeMillis();
            System.out.println("Time elapsed: " + (end - begin) + " milliseconds.");
            if (end - begin <= 600) {
                System.out.println("WARNING! CLOUDFLARE MAY HAVE BLOCKED YOU!");
            }
            Thread.sleep(sleepTime);
        }
        ArrayList<String> ep = new ArrayList<>(endpoints);
        for (int i = 0; i < ep.size(); i++) {
            if (ep.get(i).toString().contains("/videos/")) {
                ep.remove(i);
            }
        }
        return new ArrayList<>(endpoints);
    }
}