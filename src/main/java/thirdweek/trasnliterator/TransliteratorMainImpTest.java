package thirdweek.trasnliterator;

public class TransliteratorMainImpTest {
    public static void main(String[] args) {
        Transliterator transliterator = new TransliteratorMainImpl();

        String res = transliterator.transliterate("HELLO! ПРИВЕТ! Go, boy!");
        System.out.println(res);

        res = transliterator.transliterate(null);
        System.out.println(res);

        res = transliterator.transliterate("");
        System.out.println(res);

        res = transliterator.transliterate("HELLO! привет! Go, boy!");
        System.out.println(res);

        res = transliterator.transliterate("КРАКОZyabra");
        System.out.println(res);

        res = transliterator.transliterate("ЪъэЭыЫZyabra");
        System.out.println(res);
    }
}
