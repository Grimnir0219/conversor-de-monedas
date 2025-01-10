package com.currencyconverter.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileService {
    private static final String FILE_NAME = "Historial de conversiones.txt";

    // Metodo para guardar los datos en un archivo
    public void saveToFile(double originalAmount, String baseCurrency, double convertedAmount, String targetCurrency){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME,true))){
            writer.write(String.format("%.2f %s = %.2f %s%n", originalAmount, baseCurrency, convertedAmount, targetCurrency));
        } catch (IOException e) {
            System.out.println("Failed to save to file: " + e.getMessage());
        }
    }
}
