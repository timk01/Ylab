package thirdweek.trasnliterator;

/**
 * Represents a Transliterator.
 * Usually intefrace helps with conversion of a source to another one.
 *
 * @author Ylab
 * @version 1.0
 */
public interface Transliterator {
    /**
     * Helps with conversion one source to another one.
     *
     * @param source String used in conversion
     * @return converted String
     */
    String transliterate(String source);
}