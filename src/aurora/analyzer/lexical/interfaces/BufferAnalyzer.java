package aurora.analyzer.lexical.interfaces;

import aurora.analyzer.lexical.tokens.Keyword;
import aurora.analyzer.lexical.tokens.Symbol;
import aurora.analyzer.lexical.tokens.Token;

import java.util.Optional;
import java.util.function.Function;

/*
 * @project aurora
 * @author Gabriel Honda on 26/02/2020
 */
public interface BufferAnalyzer extends Function<String, Optional<IToken>> {

    static BufferAnalyzer keyword() {
        return buffer -> AnalyzerService.isKeyword(buffer) ? Optional.of(Keyword.toEnum(buffer)) : Optional.empty();
    }
    static BufferAnalyzer identifier() {
        return buffer -> AnalyzerService.isIdentifier(buffer)
                ? Optional.of(Token.ID) : Optional.empty();
    }
    static BufferAnalyzer number() {
        return buffer -> AnalyzerService.isNumber(buffer)
                ? Optional.of(Token.NUMBER) : Optional.empty();
    }
    static BufferAnalyzer symbol() {
        return buffer -> AnalyzerService.isSymbol(buffer)
                ? Optional.of(Symbol.toEnum(buffer)) : Optional.empty();
    }
    default BufferAnalyzer orElse(BufferAnalyzer other) {
        return buffer -> {
            Optional<IToken> result = other.apply(buffer);
            return result.or(() -> this.apply(buffer));
        };
    }
}
