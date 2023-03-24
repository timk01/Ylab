package thirdweek.trasnliterator;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Represents a transliterator interface implementation.
 * Here it's functionality is to convert symbols from cyrillic (Russian, for example) to english .
 *
 * @author Khasmamedov T.
 * @version 1.2
 */
public class TransliteratorMainImpl implements Transliterator {
    /**
     * Simple Hashmap structure.
     * Key = cyrillic Character (immutable), Value = appropriate english translate.
     */
    private static final Map<Character, String> CYRILLIC_TO_ENGLISH_MAP = new HashMap<>();

    static {
        CYRILLIC_TO_ENGLISH_MAP.put('А', "A");
        CYRILLIC_TO_ENGLISH_MAP.put('Б', "B");
        CYRILLIC_TO_ENGLISH_MAP.put('В', "V");
        CYRILLIC_TO_ENGLISH_MAP.put('Г', "G");
        CYRILLIC_TO_ENGLISH_MAP.put('Д', "D");
        CYRILLIC_TO_ENGLISH_MAP.put('Е', "E");
        CYRILLIC_TO_ENGLISH_MAP.put('Ё', "E");
        CYRILLIC_TO_ENGLISH_MAP.put('Ж', "ZH");
        CYRILLIC_TO_ENGLISH_MAP.put('З', "Z");
        CYRILLIC_TO_ENGLISH_MAP.put('И', "I");
        CYRILLIC_TO_ENGLISH_MAP.put('Й', "I");
        CYRILLIC_TO_ENGLISH_MAP.put('К', "K");
        CYRILLIC_TO_ENGLISH_MAP.put('Л', "L");
        CYRILLIC_TO_ENGLISH_MAP.put('М', "M");
        CYRILLIC_TO_ENGLISH_MAP.put('Н', "N");
        CYRILLIC_TO_ENGLISH_MAP.put('О', "O");
        CYRILLIC_TO_ENGLISH_MAP.put('П', "P");
        CYRILLIC_TO_ENGLISH_MAP.put('Р', "R");
        CYRILLIC_TO_ENGLISH_MAP.put('С', "S");
        CYRILLIC_TO_ENGLISH_MAP.put('Т', "T");
        CYRILLIC_TO_ENGLISH_MAP.put('У', "U");
        CYRILLIC_TO_ENGLISH_MAP.put('Ф', "F");
        CYRILLIC_TO_ENGLISH_MAP.put('Х', "KH");
        CYRILLIC_TO_ENGLISH_MAP.put('Ц', "TS");
        CYRILLIC_TO_ENGLISH_MAP.put('Ч', "CH");
        CYRILLIC_TO_ENGLISH_MAP.put('Ш', "SH");
        CYRILLIC_TO_ENGLISH_MAP.put('Щ', "SHCH");
        CYRILLIC_TO_ENGLISH_MAP.put('Ы', "Y");
        CYRILLIC_TO_ENGLISH_MAP.put('Ь', "");
        CYRILLIC_TO_ENGLISH_MAP.put('Ъ', "IE");
        CYRILLIC_TO_ENGLISH_MAP.put('Э', "E");
        CYRILLIC_TO_ENGLISH_MAP.put('Ю', "IU");
        CYRILLIC_TO_ENGLISH_MAP.put('Я', "IA");
    }

    /**
     * Method for conversion (here from cyrillic to english alphabet)
     * implementation of Transliterator interface
     * <p>
     * Traverses through initial string, checks via utility getProperStringFromMap if it's proper char
     * and only if it is, adds to final string; otherwise adds original char
     *
     * @param source String used in conversion
     * @return converted String
     * @see Transliterator
     */
    @Override
    public String transliterate(String source) {
        if (source == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < source.length(); i++) {
            char charToCheck = source.charAt(i);

            if (CYRILLIC_TO_ENGLISH_MAP.containsKey(charToCheck)) {
                sb.append(CYRILLIC_TO_ENGLISH_MAP.get(charToCheck));
            } else {
                sb.append(charToCheck);
            }
        }

        return sb.toString();
    }
}
