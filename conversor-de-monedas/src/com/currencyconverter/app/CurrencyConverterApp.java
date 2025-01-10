package com.currencyconverter.app;

import com.currencyconverter.model.CurrencyConverter;
import com.currencyconverter.service.APIService;
import com.currencyconverter.service.FileService;

import java.util.Scanner;

public class CurrencyConverterApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar divisa base
        System.out.println("Escriba la divisa a convertir (Ejemplo: COP): ");
        String baseCurrency = scanner.nextLine();

        // Inicializa Servicio
        CurrencyConverter converter = new CurrencyConverter(baseCurrency);
        APIService apiService = new APIService(baseCurrency);
        FileService fileService = new FileService();

        //Solicita moneda objetivo
        System.out.println("Escriba la divisa objetivo (Ejemplo: USD): ");
        String targetCurrency = scanner.nextLine();
        converter.addTargetCurrency(targetCurrency);

        //Solicita cantidad a convertir
        System.out.println("Escriba la cantidad a convertir: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("Cantidad Inválida. Saliendo del programa");
            return;
        }

        // Realiza conversión
        try {
            double exchangeRate = apiService.fetchExchangeRate(targetCurrency);
            double convertedAmount = amount * exchangeRate;

            // Resultado
            System.out.printf("%.2f %s = %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);

            // Guarda en el .txt
            fileService.saveToFile(amount, baseCurrency, convertedAmount, targetCurrency);
        } catch (Exception e){
            System.out.println("Error durante la conversión: " + e.getMessage());
        }

        // Finaliza el programa
        System.out.println("Conversión completa. La información fue guardada en 'Historial de conversiones.txt'.");
        scanner.close();
    }
}
