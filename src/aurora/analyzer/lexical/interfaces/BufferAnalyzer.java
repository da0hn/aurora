package aurora.analyzer.lexical.interfaces;

import aurora.analyzer.lexical.enums.Keyword;
import aurora.analyzer.lexical.enums.Token;

import java.util.Optional;
import java.util.function.Function;

import static aurora.analyzer.lexical.interfaces.BufferAnalyzer.isIdentifier;
import static aurora.analyzer.lexical.interfaces.BufferAnalyzer.isNumber;

/*
 * @project aurora
 * @author Gabriel Honda on 26/02/2020
 */
public interface BufferAnalyzer extends Function<String, Optional<IToken>> {

    static BufferAnalyzer isKeyword() {
        return buffer -> Keyword.getValues()
                .contains(buffer) ? Optional.of(Keyword.toEnum(buffer)) : Optional.empty();
    }
    static BufferAnalyzer isIdentifier() {
        return buffer -> Character.isDigit(buffer.charAt(0))
                || buffer.chars().allMatch(Character::isLetterOrDigit)
                ? Optional.of(Token.ID) : Optional.empty();
    }
    static BufferAnalyzer isNumber() {
        return buffer -> buffer.chars().allMatch(Character::isDigit)
                ? Optional.of(Token.NUMBER) : Optional.empty();
    }

    default BufferAnalyzer orElse(BufferAnalyzer other) {
        return buffer -> {
            Optional<IToken> result = other.apply(buffer);
            return result.or(() -> this.apply(buffer));
        };
    }

    public static void main(String[] args) {
        var buff = "aurora::init";
        Optional<IToken> apply = BufferAnalyzer.isKeyword().orElse(isIdentifier())
                .orElse(isNumber()).apply(buff);
    }

}
