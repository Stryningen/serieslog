package no.serieslog.serieslog;

import no.serieslog.serieslog.data.Series;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class APIHandler {

    RestTemplate restTemplate;

    public APIHandler(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Series> findMazeSeriesByQuery(String query){
        List<Series> listOfSeries = new ArrayList<>();
        ResponseEntity<String> response = restTemplate.getForEntity(
                "http://api.tvmaze.com/search/shows?q="+query, String.class
        );
        JSONArray joa = new JSONArray(response.getBody());
        for (int i=0; i<joa.length(); i++){
           listOfSeries.add(convertJsonToSeries(joa.getJSONObject(i).getJSONObject("show")));
        }
        return listOfSeries;

    }

    public Series findMazeSeriesById(Integer tvMazeId){
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(
                    "http://api.tvmaze.com/shows/"+tvMazeId, String.class
            );
            JSONObject jo = new JSONObject(response.getBody());
            return convertJsonToSeries(jo);
        } catch (JSONException e){
            return null;
        }
    }

    private Series convertJsonToSeries(JSONObject jo) {
        Series series = new Series();
        try {
            series.setMazeId(jo.getInt("id"));
        } catch (JSONException e) {
            return null;
        }
        try {
            series.setSeriesName(jo.getString("name"));
        } catch (JSONException e) {
            series.setSeriesName("Name not found");
        }
        try {
            series.setSeriesDescription(jo.getString("summary"));
        } catch (JSONException e) {
            series.setSeriesDescription("Description not found");
        }
        try {
            series.setSeriesImageUrl(jo.getJSONObject("image").getString("medium"));
        } catch (JSONException e) {
            series.setSeriesImageUrl(null);
        }
        try {
            series.setSeriesLinkUrl(jo.getString("url"));
        } catch (JSONException e) {
            series.setSeriesLinkUrl(null);
        }
        return series;
    }
}
