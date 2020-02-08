package network.locales;

import java.util.HashMap;
import java.util.Locale;

public class LocalFinder {

    private static HashMap<String, String> countries;

    static {
        countries = new HashMap<>();
        for (String iso : Locale.getISOCountries())
            countries.put(iso.trim(), new Locale("", iso).getDisplayCountry().trim());
    }

    public static String getCountryName(String countryCode) {
        return countries.get(countryCode.toUpperCase());
    }

    public static String getCountryCode(String countryName) {
        for (String code : countries.keySet()) {
            if (countries.get(code).trim().equals(countryName.trim())) return code;
        }
        return null;
    }

}
