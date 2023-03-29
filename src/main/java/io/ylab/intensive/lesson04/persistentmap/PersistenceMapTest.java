package io.ylab.intensive.lesson04.persistentmap;

import io.ylab.intensive.lesson04.DbUtil;

import javax.sql.DataSource;
import java.sql.SQLException;

public class PersistenceMapTest {
    public static void main(String[] args) throws SQLException {
        DataSource dataSource = initDb();
        PersistentMap persistentMap = new PersistentMapImpl(dataSource);

        System.out.println(persistentMap.containsKey("tim"));
        System.out.println(persistentMap.getKeys());
        System.out.println(persistentMap.get("tim"));
        persistentMap.remove("tim");
        System.out.println(persistentMap.getKeys());
        persistentMap.clear();
        persistentMap.put("tim", "the great");

        System.out.println();

        persistentMap.init("humans");
        persistentMap.put("Timur Khasmamedov", "the greatest");
        persistentMap.put("Vailiy Ivanovich", "the bravest");
        persistentMap.put("Ivan Ivanovich", "the modest");
        persistentMap.put("Timur Khasmamedov", "the greatest2");

        System.out.println(persistentMap.containsKey("Ivan Ivanovich"));
        System.out.println(persistentMap.getKeys());
        System.out.println(persistentMap.get("Ivan Ivanovich"));
        persistentMap.remove("Ivan Ivanovich");
        persistentMap.put("Timur Khasmamedov", null);
        System.out.println(persistentMap.getKeys());
        System.out.println(persistentMap.get("Ivan Ivanovich"));
        persistentMap.clear();
        System.out.println(persistentMap.getKeys());

        System.out.println();

        persistentMap.init("humans");
        persistentMap.put(null, "the greatest");
        System.out.println(persistentMap.get(null));
        persistentMap.clear();

        System.out.println();

        persistentMap.init("humans");
        persistentMap.put("Timur Khasmamedov", null);
        System.out.println(persistentMap.get("Timur Khasmamedov"));
        persistentMap.clear();

        System.out.println();

        persistentMap.init("humans");
        persistentMap.put("Timur Khasmamedov", "the greatest");
        persistentMap.put("Vailiy Ivanovich", "the bravest");
        persistentMap.put("Ivan Ivanovich", "the modest");
        persistentMap.put("Timur Khasmamedov", "the greatest2");

        persistentMap.init("animals");
        persistentMap.put("human", "70 kg");
        persistentMap.put("zebra", "250 kg");
        persistentMap.put("lion", "150 kg");

        System.out.println(persistentMap.getKeys());
        persistentMap.remove("Ivan Ivanovich");
        System.out.println(persistentMap.getKeys());
        persistentMap.remove("human");
        persistentMap.containsKey("Ivan Ivanovich");
        System.out.println(persistentMap.get("Ivan Ivanovich"));
        System.out.println(persistentMap.getKeys());

        persistentMap.init("humans");
        persistentMap.remove("human");
        System.out.println(persistentMap.getKeys());
        persistentMap.clear();
        System.out.println(persistentMap.getKeys());
    }

    public static DataSource initDb() throws SQLException {
        String createMapTable = ""
                + "drop table if exists persistent_map; "
                + "CREATE TABLE if not exists persistent_map (\n"
                + "   map_name varchar,\n"
                + "   KEY varchar,\n"
                + "   value varchar\n"
                + ");";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(createMapTable, dataSource);
        return dataSource;
    }
}
