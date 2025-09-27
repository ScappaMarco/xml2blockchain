package blockchain.util;

import java.util.Locale;

public class FormatterUtil {
    public static String formatNumber(long number) {
        return String.format(Locale.ITALY, "%,d", number);
    }
}
