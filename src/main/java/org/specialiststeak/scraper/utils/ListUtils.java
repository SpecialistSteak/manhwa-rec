package org.specialiststeak.scraper.utils;

import java.util.List;

public final class ListUtils {
    public static void listToString(List list) {
        for (Object o : list) {
            System.out.println(o.toString());
        }
    }
}
