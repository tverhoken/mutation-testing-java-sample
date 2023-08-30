package fr.norsys.tverhoken.mutationtesting.almostgood;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Stream;

import static java.net.HttpURLConnection.*;
import static java.net.http.HttpResponse.BodyHandlers.ofLines;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DatasourceTest {

    Datasource datasource;
    HttpClient httpClientMock;

    @BeforeEach
    void setup() {
        httpClientMock = mock(HttpClient.class);
        datasource = new Datasource(httpClientMock);
    }

    @Test
    @DisplayName("Should return a client error cause when calling resource api returns a response with 4xx code.")
    void shouldReturnClientErrorWhen4xxResponseCode() throws Exception {
        HttpResponse<Stream<String>> httpResponse = mock(HttpResponse.class);
        when(httpClientMock.send(any(), eq(ofLines()))).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(HTTP_UNAUTHORIZED);

        assertThatThrownBy(() -> datasource.getAll()).isInstanceOf(InvalidDatasourceExchangeException.class);
    }

    @Test
    @DisplayName("Should return a server error cause when calling resource api returns a response with 5xx code.")
    void shouldReturnServerErrorWhen5xxResponseCode() throws Exception {
        HttpResponse<Stream<String>> httpResponse = mock(HttpResponse.class);
        when(httpClientMock.send(any(), eq(ofLines()))).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(HTTP_UNAVAILABLE);

        assertThatThrownBy(() -> datasource.getAll()).isInstanceOf(UnreachableDataSourceException.class);
    }

    @Test
    @DisplayName("Should return a server error cause when calling resource api fails.")
    void shouldReturnServerErrorWhenException() throws Exception {
        when(httpClientMock.send(any(), eq(ofLines()))).thenThrow(new IOException());

        assertThatThrownBy(() -> datasource.getAll()).isInstanceOf(UnreachableDataSourceException.class);
    }

    @Test
    @DisplayName("Should return expected data when successfully called targeted API.")
    void shouldReturnDataWhenAllGood() throws Exception {
        List<String> expectedData = of("data 1",  "data 2", "data 3");

        HttpResponse<Stream<String>> httpResponse = mock(HttpResponse.class);
        when(httpClientMock.send(any(), eq(ofLines()))).thenReturn(httpResponse);
        when(httpResponse.statusCode()).thenReturn(HTTP_OK);
        when(httpResponse.body()).thenReturn(expectedData.stream());

        assertThat(datasource.getAll()).isEqualTo(expectedData);
    }

}
