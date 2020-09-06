package org.aurora.core.analyzer.utils;

import java.util.List;

/*
 * @project org.aurora
 * @author Gabriel on 12/05/2020
 */
public interface PredicateService {
    static boolean isLineEmpty(List<String> line) {
        return line.stream().allMatch(str -> str.isEmpty() && str.isBlank());
    }

    static boolean isLetterOrDigit(String buffer) {
        return buffer.chars().allMatch(Character::isLetterOrDigit);
    }

    static boolean isIdentifier(String buffer) {
        return !Character.isDigit(buffer.charAt(0))
                && isLetterOrDigit(buffer);
    }

    static boolean isDecoratedIdentifier(String buffer) {
        if(!buffer.contains("_")) return false;
        var temp = buffer.substring(0, buffer.indexOf("_"));
        return isIdentifier(temp);
    }

    static boolean isNumber(String buffer) {
        return buffer.chars().allMatch(Character::isDigit);
    }
}
