package org.specialiststeak.scraper.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import static org.specialiststeak.scraper.utils.ErrorUtils.errorMessager;

public final class GetHTMLUtil {
    public static String getHTML(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        String response = "";
        try (CloseableHttpResponse httpResponse = httpClient.execute(request)) {
            response = EntityUtils.toString(httpResponse.getEntity());
        } catch (Exception e) {
            errorMessager(e, true);
        }
        return response;
    }
}
