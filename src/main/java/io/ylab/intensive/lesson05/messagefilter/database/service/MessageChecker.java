package io.ylab.intensive.lesson05.messagefilter.database.service;

import io.ylab.intensive.lesson05.messagefilter.database.DataBaseIntegrator;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MessageChecker {

    private final DataBaseIntegrator dataBaseIntegrator;
    private final StringBuilder sbCensoredString = new StringBuilder();
    private final StringBuilder sb = new StringBuilder();
    private final StringBuilder sbNew = new StringBuilder();
    private static final char REPLACEMENT_CHARACTER = '*';

    public MessageChecker(DataBaseIntegrator dataBaseIntegrator) {
        this.dataBaseIntegrator = dataBaseIntegrator;
    }

    public String getCheckedMessage(String badWordMsg) throws SQLException {
        zeroAllStringBuilders();

        appendAllWordsButLast(this.dataBaseIntegrator, badWordMsg, this.sbCensoredString, this.sb, this.sbNew);

        appendLastWord(this.dataBaseIntegrator, badWordMsg, this.sbCensoredString);

        return this.sbCensoredString.toString();
    }

    private void zeroAllStringBuilders() {
        sbCensoredString.setLength(0);
        sb.setLength(0);
        sbNew.setLength(0);
    }

    private void appendAllWordsButLast(DataBaseIntegrator dataBaseIntegrator, String badWordMsg,
                                       StringBuilder sbCensoredString,
                                       StringBuilder sb, StringBuilder sbNew) throws SQLException {
        char[] chars = badWordMsg.toCharArray();

        for (char aChar : chars) {
            if (aChar == ' ' || aChar == '.' || aChar == ','
                    || aChar == ';' || aChar == '?' || aChar == '!' || aChar == '\n') {
                if (dataBaseIntegrator.isTheWordBad(sb.toString())) {
                    sbNew.append(replaceAll(sb.toString()));
                    if (!sbNew.isEmpty()) {
                        sbCensoredString.append(sbNew);
                        sb.setLength(0);
                        sbNew.setLength(0);
                    }
                } else {
                    sbCensoredString.append(sb);
                    sb.setLength(0);
                }
                sbCensoredString.append(aChar);
            } else {
                sb.append(aChar);
            }
        }
    }

    private void appendLastWord(DataBaseIntegrator dataBaseIntegrator,
                                String badWordMsg, StringBuilder sbCensoredString) throws SQLException {
        String stringSplitter = "[ +.+,+;+?+!+\n+]";

        String[] splittedString = badWordMsg.split(stringSplitter);

        String s = splittedString[splittedString.length - 1];
        if (!dataBaseIntegrator.isTheWordBad(s)) {
            sbCensoredString.append(s);
        } else {
            sbCensoredString.append(replaceAll(s));
        }
    }

    private String replaceAll(String word) {
        StringBuilder ret = new StringBuilder();

        if (word.length() > 2) {
            ret.append(word.charAt(0));
            for (int i = 1; i < word.length() - 1; i++) {
                ret.append(REPLACEMENT_CHARACTER);
            }
            ret.append(word.charAt(word.length() - 1));
            return ret.toString();
        }

        return word;
    }
}
