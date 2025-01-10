package com.currencyconverter.app;

import com.currencyconverter.model.CurrencyConverter;
import com.currencyconverter.service.APIService;
import com.currencyconverter.service.FileService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class CurrencyConverterGUI extends JFrame {

    private JTextField baseCurrencyField;
    private JTextField targetCurrencyField;
    private JTextField amountField;
    private JTextArea resultArea;

    public CurrencyConverterGUI() {
        setTitle("Convertidor de Divisas");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1));

        // Entrada para la divisa a convertir
        add(new JLabel("Divisa a convertir (Ej: COP): "));
        baseCurrencyField = new JTextField();
        add(baseCurrencyField);

        // Entrada para la divisa objetivo
        add(new JLabel("Divisa objetivo (Ej: USD): "));
        targetCurrencyField = new JTextField();
        add(targetCurrencyField);

        // Entrada para la cantidad
        add(new JLabel("Cantidad a convertir:"));
        amountField = new JTextField();
        add(amountField);

        // Cuadro de resultado
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        // Botón Convertir
        JButton convertButton = new JButton("Convertir");
        add(convertButton);

        // Acción para el botón Convertir
        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performConversion();
            }
        });

        // Botón para mostrar todas las divisas
        JButton showCurrenciesButton = new JButton("Mostrar divisas disponibles");
        add(showCurrenciesButton);

        // Acción para el botón Mostrar monedas
        showCurrenciesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAvailableCurrencies();
            }
        });

        setVisible(true);
    }

    private void performConversion() {
        String baseCurrency = baseCurrencyField.getText().toUpperCase();
        String targetCurrency = targetCurrencyField.getText().toUpperCase();
        String amountText = amountField.getText();

        // Lista de divisas permitidas
        Set<String> allowedCurrencies = Set.of("USD", "EUR", "GBP", "JPY", "AUD", "CAD");

        // Validar que los campos estén llenos
        if (baseCurrency.isEmpty() || targetCurrency.isEmpty() || amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar que la divisa objetivo esté permitida
        if (!allowedCurrencies.contains(targetCurrency)) {
            JOptionPane.showMessageDialog(this, "Divisa no disponible, seleccione otra.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Validar que el monto sea un número válido
            double amount = Double.parseDouble(amountText);
            CurrencyConverter converter = new CurrencyConverter(baseCurrency);
            APIService apiService = new APIService(baseCurrency);
            FileService fileService = new FileService();

            // Realizar la conversión
            double exchangeRate = apiService.fetchExchangeRate(targetCurrency);
            double convertedAmount = amount * exchangeRate;

            // Mostrar resultado
            resultArea.setText(String.format("%.2f %s = %.2f %s", amount, baseCurrency, convertedAmount, targetCurrency));

            // Guardar en un .txt
            fileService.saveToFile(amount, baseCurrency, convertedAmount, targetCurrency);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Cantidad Inválida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAvailableCurrencies() {
        // Lista de divisas permitidas
        Set<String> allowedCurrencies = Set.of("USD", "EUR", "GBP", "JPY", "AUD", "CAD");

        // Formatear las divisas en una lista vertical
        StringBuilder currencyList = new StringBuilder();
        for (String currency : allowedCurrencies) {
            currencyList.append(currency).append("\n");
        }

        // Mostrar divisas
        JOptionPane.showMessageDialog(this, "Divisas disponibles:\n" + currencyList.toString());
    }

    public static void main(String[] args) {
        new CurrencyConverterGUI();
    }
}
