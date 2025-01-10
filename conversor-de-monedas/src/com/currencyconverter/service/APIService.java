package com.currencyconverter.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Set;

public class APIService {
    private String apiKey = "29a8348984b1863896b6b762";
    private String baseCurency;

    public  APIService(String baseCurency){
        this.baseCurency = baseCurency.toUpperCase();
    }

    // Metodo para obtener la taza de cambio desde la API
    public double fetchExchangeRate(String targetCurrency) throws IOException {
        String urlString = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", apiKey, baseCurency);
        URL url = new URL(urlString);

        // Establecer concexion
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200){
            throw new IOException("Failed to fetch data from API: Response Code " + responseCode);
        }

        // Leer respuesta de la API
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        JsonObject data = JsonParser.parseReader(reader).getAsJsonObject();
        reader.close();

        // Validar si la moneda objetivo esta soportada
        JsonObject conversionRates = data.getAsJsonObject("conversion_rates");
        if (!conversionRates.has(targetCurrency.toUpperCase())){
            throw new IOException("Divisa no soportada: " + targetCurrency);
        }

        return conversionRates.get(targetCurrency.toUpperCase()).getAsDouble();

    }

    // Mostrar divisas disponibles de la API
    public Set<String> getAvailableCurrencies() throws IOException {
        String urlString = String.format("https://v6.exchangerate-api.com/v6/%s/latest/%s", apiKey, baseCurency);
        URL url = new URL(urlString);

        // Establece conexion
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new IOException("Failed to fetch data from API: Response code " + responseCode);
        }

        // Leer la respuesta de la API
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());
        JsonObject data = JsonParser.parseReader(reader).getAsJsonObject();
        reader.close();

        // Extraer y devolver las divisas disponibles
        JsonObject conversionRates = data.getAsJsonObject("conversion_rates");
        return conversionRates.keySet(); //

    }
}
