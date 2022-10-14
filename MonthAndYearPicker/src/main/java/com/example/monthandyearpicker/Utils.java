package com.example.monthandyearpicker;

import com.ibm.icu.text.RuleBasedNumberFormat;

import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

public class Utils {

    public static String numberToOrdinalWord(int number) {
        RuleBasedNumberFormat nf = new RuleBasedNumberFormat(Locale.UK, RuleBasedNumberFormat.SPELLOUT);
        String floor = nf.format(number, "%spellout-ordinal").replace("-", " ");
        floor = capitalize(floor);
        if (number == -1) {
            floor = "Basement";
        } else if (number == 0) {
            floor = "Ground";
        }
        return floor + " Floor";
    }

    public static String numberToOrdinal(int number) {
        RuleBasedNumberFormat nf = new RuleBasedNumberFormat(Locale.UK, RuleBasedNumberFormat.ORDINAL);
        String floor = nf.format(number);
        if (number == -1) {
            floor = "Basement";
        } else if (number == 0) {
            floor = "Ground";
        }
        return floor + " Floor";
    }

    public static String capitalize(String str) {
        return StringUtils.capitalize(str);
    }
}
