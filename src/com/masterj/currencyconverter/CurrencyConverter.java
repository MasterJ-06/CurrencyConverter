package com.masterj.currencyconverter;

import java.awt.*;
import java.io.Console;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import org.json.JSONObject;

public class CurrencyConverter {
    static Scanner read = new Scanner(System.in);
    static String fromCurrency;
    static String toCurrency;
    static double amount;
    static double newAmount;

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        Console console = System.console();
        if (console == null && !GraphicsEnvironment.isHeadless()) {
            String filename = (com.masterj.currencyconverter.CurrencyConverter.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getPath();
            System.out.println(filename.substring(1));
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "cmd", "/k", "java -jar \"" + filename.substring(1).replace("/", "\\") + "\""});
        } else {
            System.out.print("Enter the 3 letter abbreviation of the currency to convert: ");
            fromCurrency = read.nextLine();
            System.out.print("Enter the 3 letter abbreviation of the currency to convert to: ");
            toCurrency = read.nextLine();
            System.out.print("Enter the amount to convert: ");
            amount = read.nextInt();
            var client = HttpClient.newHttpClient();
            var request = HttpRequest.newBuilder(
                    URI.create("https://www.alphavantage.co/query?from_currency=" + fromCurrency + "&to_currency=" + toCurrency + "&function=CURRENCY_EXCHANGE_RATE&apikey=9XN2HKS99WU0AQRW")
            ).build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String res = response.body();
            JSONObject obj = new JSONObject(res);
            String value = obj.getJSONObject("Realtime Currency Exchange Rate").getString("5. Exchange Rate");
            double val = Double.parseDouble(value);
            newAmount = amount * val;
            System.out.println(amount + " " + fromCurrency + " approximately equals " + newAmount + " " + toCurrency);
        }
    }
}