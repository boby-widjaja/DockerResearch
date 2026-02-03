package com.basiliskSB.utility;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatHelper {

    public static String formatMoney(Double money) {
        Locale indonesia = new Locale("id", "ID");
        var formatter = NumberFormat.getCurrencyInstance(indonesia);
        return formatter.format(money);
    }

    public static String formatPercentage(Double percentage){
        Locale indonesia = new Locale("id", "ID");
        var formatter = NumberFormat.getPercentInstance(indonesia);
        return formatter.format(percentage);
    }

    public static String formatLongDate(LocalDate date){
        Locale indonesia = new Locale("id", "ID");
        var formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return formatter.format(date);
    }
}
