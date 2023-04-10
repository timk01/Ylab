package io.ylab.intensive.lesson05.messagefilter.database.service;

import io.ylab.intensive.lesson05.messagefilter.database.DataBaseIntegrator;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class MessageChecker {
    private final DataBaseIntegrator dataBaseIntegrator;
    private final StringBuilder censoredStringBuilder = new StringBuilder();
    private final StringBuilder characterStringBuilder = new StringBuilder();
    private final StringBuilder replacementStringBuilder = new StringBuilder();
    private static final char REPLACEMENT_CHARACTER = '*';

    public MessageChecker(DataBaseIntegrator dataBaseIntegrator) {
        this.dataBaseIntegrator = dataBaseIntegrator;
    }

    public String getCheckedMessage(String badWordMsg) throws SQLException {
        clearAllStringBuilders();

        appendAllWordsToStringBuilder(badWordMsg);

        return this.censoredStringBuilder.toString();
    }

    private void clearAllStringBuilders() {
        this.censoredStringBuilder.setLength(0);
        this.characterStringBuilder.setLength(0);
        this.replacementStringBuilder.setLength(0);
    }

    private void appendAllWordsToStringBuilder(String badWordMsg) throws SQLException {
        char[] chars = badWordMsg.toCharArray();

        for (char aChar : chars) {
            if (aChar == ' ' || aChar == '.' || aChar == ','
                    || aChar == ';' || aChar == '?' || aChar == '!' || aChar == '\n') {
                if (this.dataBaseIntegrator.isTheWordBad(this.characterStringBuilder.toString())) {
                    this.replacementStringBuilder.append(replaceAll(this.characterStringBuilder.toString()));
                    if (!this.replacementStringBuilder.isEmpty()) {
                        this.censoredStringBuilder.append(this.replacementStringBuilder);
                        this.characterStringBuilder.setLength(0);
                        this.replacementStringBuilder.setLength(0);
                    }
                } else {
                    this.censoredStringBuilder.append(this.characterStringBuilder);
                    this.characterStringBuilder.setLength(0);
                }
                this.censoredStringBuilder.append(aChar);
            } else {
                this.characterStringBuilder.append(aChar);
            }
        }

        appendLastWord(this.dataBaseIntegrator, this.characterStringBuilder.toString());
    }

    private void appendLastWord(DataBaseIntegrator dataBaseIntegrator, String lastWordToCheck) throws SQLException {
        if (!dataBaseIntegrator.isTheWordBad(lastWordToCheck)) {
            this.censoredStringBuilder.append(lastWordToCheck);
        } else {
            this.censoredStringBuilder.append(replaceAll(lastWordToCheck));
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
