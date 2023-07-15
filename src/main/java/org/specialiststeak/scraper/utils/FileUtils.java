package org.specialiststeak.scraper.utils;

import java.io.*;

public final class FileUtils {

    public static void main(String[] args) {
        readAndWrite("src/main/resources/anime-planet-anime.json", "src/main/resources/anime-planet-anime-cleaned.json", "all?page\\u003d");
        readAndWrite("src/main/resources/anime-planet-manga.json", "src/main/resources/anime-planet-manga-cleaned.json", "all?page\\u003d");
    }

    public static void readAndWrite(String fileToRead, String fileToWrite, String stringToDeleteEachLineContainingIt) {
        try {
            File file = new File(fileToRead);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            FileWriter fw = new FileWriter(fileToWrite);
            BufferedWriter bw = new BufferedWriter(fw);

            String line;
            while ((line = br.readLine()) != null) {
                if (!line.contains(stringToDeleteEachLineContainingIt)) {
                    bw.write(line + "\n");
                }
            }

            br.close();
            fr.close();
            bw.close();
            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
