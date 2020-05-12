package aurora.analyzer.utils;

import java.util.List;

/*
 * @project aurora
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

    static boolean isNumber(String buffer) {
        return buffer.chars().allMatch(Character::isDigit);
    }
}
