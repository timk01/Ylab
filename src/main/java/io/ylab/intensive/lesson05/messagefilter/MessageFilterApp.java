package io.ylab.intensive.lesson05.messagefilter;

import io.ylab.intensive.lesson05.messagefilter.database.DataBaseIntegrator;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class MessageFilterApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        DataBaseIntegrator dataBaseIntegrator = applicationContext.getBean(DataBaseIntegrator.class);
        dataBaseIntegrator.fillDbWithBadWords();

        String splitter = "[ +.+,+;+?+!+\n+]";
        String badWord = "!!!fuck!!!!fuck! fuck? fucker; fucking, fuck \n" +
                "fucking \n" +
                "!fucker! Fucking fucK afuck fucki";
        String badWordChanged = "!!!f**k!!!!f**k! f**k? f****r; f*****g, f**k \n" +
                "f*****g \n" +
                "!f****r! F*****g f**K afuck fucki";
        String[] split = badWord.split(splitter);
        for (String s : split) {
            System.out.println(s);
        }
        String[] splittedN = badWordChanged.split(splitter);
        List<String> stringList = Arrays.stream(splittedN).toList();

        System.out.println();

        List<String> allBadWords = dataBaseIntegrator.findAllBadWords();
        System.out.println(allBadWords);

        StringBuilder sbForInitialString = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        StringBuilder sbNew = new StringBuilder();
        char[] chars = badWord.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char aChar = chars[i];
            if (aChar == ' ' || aChar == '.' || aChar == ','
                    || aChar == ';' || aChar == '?' || aChar == '!' || aChar == '\n') {



                sbNew.append(replaceAll(sb.toString(), '*'));

                if (!sbNew.isEmpty()) {
                    sbForInitialString.append(sbNew);
                    sb.setLength(0);
                    sbNew.setLength(0);
                }
                sbForInitialString.append(aChar);

            } else {
                sb.append(aChar);
            }
        }

        System.out.println(sbForInitialString);


        //обработал слова
        //взял изначаль

    }

    public static String replaceAll(String word, char replacer) {
        StringBuilder ret = new StringBuilder();
        if (word.length() > 2) {
            ret.append(word.charAt(0));
            for (int i = 1; i < word.length() - 1; i++) {
                ret.append(replacer);
            }
            ret.append(word.charAt(word.length() - 1));
            return ret.toString();
        }

        return word;
    }
}


