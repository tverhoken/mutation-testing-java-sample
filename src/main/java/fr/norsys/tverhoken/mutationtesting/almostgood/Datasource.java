package fr.norsys.tverhoken.mutationtesting.almostgood;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Stream;

import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.http.HttpResponse.BodyHandlers.ofLines;

public class Datasource {
    private final HttpClient httpClient;

    public Datasource(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public List<String> getAll() throws InvalidDatasourceExchangeException, UnreachableDataSourceException {
        var httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("http://private.api.norsys.io"))
                .build();
        try {
            HttpResponse<Stream<String>> response = httpClient.send(httpRequest, ofLines());
            if (HTTP_OK == response.statusCode()) {
                return response.body().toList();
            }
            if (response.statusCode() < HTTP_INTERNAL_ERROR) {
                throw new InvalidDatasourceExchangeException();
            }
            throw new UnreachableDataSourceException();
        } catch (IOException | InterruptedException e) {
            throw new UnreachableDataSourceException();
        }
    }
}
