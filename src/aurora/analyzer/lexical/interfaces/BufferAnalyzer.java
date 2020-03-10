package aurora.analyzer.lexical.interfaces;

import aurora.lang.Keyword;
import aurora.lang.Symbol;
import aurora.lang.Terminal;
import aurora.lang.Token;

import java.util.Optional;
import java.util.function.Function;

/*
 * @project aurora
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
        return buffer -> AnalyzerService.isKeyword(buffer) ? Optional.of(Token.toEnum(buffer)) : Optional.empty();
    }
    // verifica se e um identificador e instancia um Optional com IToken
    static BufferAnalyzer identifier() {
        return buffer -> AnalyzerService.isIdentifier(buffer)
                ? Optional.of(Token.ID) : Optional.empty();
    }
    // verifica se e um numero e instancia um Optional com IToken
    static BufferAnalyzer number() {
        return buffer -> AnalyzerService.isNumber(buffer)
                ? Optional.of(Token.NUMBER) : Optional.empty();
    }
    // verifica se e um simbolo e instancia um Optional com IToken
    static BufferAnalyzer symbol() {
        return buffer -> AnalyzerService.isSymbol(buffer)
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
