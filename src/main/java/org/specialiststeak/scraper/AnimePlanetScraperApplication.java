package org.specialiststeak.scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.specialiststeak.scraper.endpoint.AnimePlanetLinkFinder.*;

@SpringBootApplication
@RestController
public class AnimePlanetScraperApplication implements Runnable {
    @Override
    public void run() {
        System.out.println("Running");
    }

    public static void main(String[] args) {
        SpringApplication.run(AnimePlanetScraperApplication.class, args);
    }

    record homepage(List<String> welcomeMessage, List<String> endpoints){}

    @GetMapping("/")
    public homepage def() {
        return new homepage(
                List.of("You've reached the Anime Planet Scraper API!", "Please use one of the following endpoints:"),
                List.of(
                        "/anime-search/{search}",
                        "/anime-search-google-fallback/{search}",
                        "/manga-search/{search}",
                        "/manga-search-google-fallback/{search}",
                        "/anime-google-based-search/{search}",
                        "/manga-google-based-search/{search}"
                )
        );
    }

    @GetMapping("/anime-search/{search}")
    public String animeSearch(@PathVariable String search) {
        try {
            return searchAnime(search, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/anime-search-google-fallback/{search}")
    public String animeSearchGoogleFallback(@PathVariable String search) {
        try {
            return searchAnime(search, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/manga-search/{search}")
    public String mangaSearch(@PathVariable String search) {
        try {
            return searchManga(search, false);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/manga-search-google-fallback/{search}")
    public String mangaSearchGoogleFallback(@PathVariable String search) {
        try {
            return searchManga(search, true);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/anime-google-based-search/{search}")
    public String animeGoogleBasedSearch(@PathVariable String search) {
        try {
            return getFirstGoogleSearchResultLinkAnime(search);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/manga-google-based-search/{search}")
    public String mangaGoogleBasedSearch(@PathVariable String search) {
        try {
            return getFirstGoogleSearchResultLinkManga(search);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}