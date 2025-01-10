package com.currencyconverter.model;

import java.util.ArrayList;

public class CurrencyConverter {
    private String baseCurrency; //Moneda base
    private ArrayList<String> targetCurrencies; // Monedas objetivo

    // Inicializar la moneda base
    public CurrencyConverter(String baseCurrency){
        this.baseCurrency = baseCurrency.toUpperCase();
        this.targetCurrencies = new ArrayList<>();
    }

    // Metodo para agregar monedas objetivo
    public void addTargetCurrency(String currency){
        targetCurrencies.add(currency.toUpperCase());
    }

    // Metodo para obtener la lista de monedas objetivo
    public ArrayList<String> getTargetCurrencies() {
        return targetCurrencies;
    }

    // Metodo para obtener la moneda base
    public String getBaseCurrency(){
        return baseCurrency;
    }
}
