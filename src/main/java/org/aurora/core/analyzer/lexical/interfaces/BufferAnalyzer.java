package org.aurora.core.analyzer.lexical.interfaces;

import org.aurora.core.analyzer.utils.PredicateService;
import org.aurora.core.lang.Symbol;
import org.aurora.core.lang.Terminal;
import org.aurora.core.lang.Token;

import java.util.Optional;
import java.util.function.Function;

/*
 * @project org.aurora
 * @author Gabriel Honda on 26/02/2020
 */
public interface BufferAnalyzer extends Function<String, Optional<Terminal>> {

    /*
     * BufferAnalyzer utiliza o pattern composite para encadear metodos,
     * os metodos sao responsaveis por testar o buffer e instanciar um enumerado
     * que implemente a interface IToken. Cada metodo pode devolver um Optional com
     * o enumerado encapsulado ou um Optional vazio que sera testado dentro do
     * método que encadeia os outros, caso seja encontrado uma instancia do enumerado
     * cancela o encadeamento e devolve o enumerado encapsulado
     * */

    // verifica se esta dentro do enumerado Keyword e instancia um Optional com IToken
    static BufferAnalyzer keyword() {
        return buffer -> LexicalService.isKeyword(buffer) ? Optional.of(
                Token.toEnum(buffer)) : Optional.empty();
    }

    // verifica se e um identificador e instancia um Optional com IToken
    static BufferAnalyzer identifier() {
        return buffer -> PredicateService.isIdentifier(buffer)
                ? Optional.of(Token.ID) : Optional.empty();
    }

    // verifica se e um numero e instancia um Optional com IToken
    static BufferAnalyzer number() {
        return buffer -> PredicateService.isNumber(buffer)
                ? Optional.of(Token.NUMBER) : Optional.empty();
    }

    // verifica se e um simbolo e instancia um Optional com IToken
    static BufferAnalyzer symbol() {
        return buffer -> LexicalService.isSymbol(buffer)
                ? Optional.of(Symbol.toEnum(buffer)) : Optional.empty();
    }

    //
    default BufferAnalyzer orElse(BufferAnalyzer other) {
        return buffer -> {
            Optional<Terminal> result = other.apply(buffer);
            return result.or(() -> this.apply(buffer));
        };
    }
}
