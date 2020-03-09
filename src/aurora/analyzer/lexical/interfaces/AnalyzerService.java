package aurora.analyzer.lexical.interfaces;

import aurora.analyzer.lexical.Lexical.Controls;
import aurora.analyzer.lexical.lang.Keyword;
import aurora.analyzer.lexical.lang.Symbol;
import aurora.analyzer.lexical.lang.Token;
import aurora.analyzer.lexical.utils.Tokens;
import aurora.analyzer.lexical.log.LogLexical;
import aurora.analyzer.lexical.utils.TokenContainer;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Consumer;

import static aurora.analyzer.lexical.Lexical.Controls.*;
import static aurora.analyzer.lexical.interfaces.BufferAnalyzer.*;

/*
 * @project aurora
 * @author Gabriel Honda on 01/03/2020
 */
public interface AnalyzerService {

    static void tokenAnalyzer(String buffer) {
        /*executa varios testes consecutivos utilizando o pattern composite
         * e a interface funcional predicate, se o buffer for enquadrado em
         * alguma das classificacoes sera instanciado um Enum que implementa
         * a interface de marcacao IToken para ser inserido na lista logo em seguida
         * */
        Optional<IToken> optToken = BufferAnalyzer.keyword().orElse(identifier())
                .orElse(number()).orElse(symbol()).apply(buffer);
        // insere no obj Tokens o obj do tipo IToken
        // e cria um log no console informando o usuario
        Consumer<IToken> addAndLog = tk -> {
            var temp = new TokenContainer(tk, buffer, Controls.getLine(), Controls.getColumn());
            Tokens.add(temp);
            LogLexical.add(temp);
        };
        // caso o lexema nao seja reconhecido cria um log no
        // console informando o erro, a linha e a coluna
        Runnable logError = () -> LogLexical.error("the lexeme was not recognized: " + buffer,
                                                   Controls.getLine(), Controls.getColumn()
        );
        /*
         * optToken e do tipo Optional para evitar erros com NullPointerException
         * o metodo ifPresentOrElse() executa o metodo addAndLog() caso optToken
         * nao seja nulo, caso contrario executa o metodo logError()
         * */
        optToken.ifPresentOrElse(addAndLog, logError);
        // incrementa a coluna com o total de caracteres da string
        Controls.incrementColumn(buffer.length());
    }

    static boolean stringAnalyzer(ListIterator<String> it) {
        /*
         * ao encontrar um abre aspas checa se e uma string
         * itera o resto da linha adicionando cada String no buffer
         * ate chegar no final ou encontrar um fecha aspas
         * ao encontrar as aspas ativa a flag closeQuotes,
         * saindo do loop e adicionando na lista
         * uma instancia de IToken e incrementando a coluna
         * */
        var buffer = new StringBuilder();
        var closeQuotes = false;
        buffer.append('"');

        while(it.hasNext() && !closeQuotes) {
            var curr = it.next();
            buffer.append(curr);
            if(curr.equals("\"")) closeQuotes = true;
        }
        if(closeQuotes) {
            var tk = new TokenContainer(Token.STRING, buffer.toString(),
                                        getLine(), getColumn()
            );
            LogLexical.add(tk);
            Tokens.add(tk);
            incrementColumn(buffer.length());
        }
        else {
            LogLexical.error("missing closing quotes",
                             getLine(), getColumn()
            );
            return true;
        }
        return false;
    }

    static boolean isLineEmpty(List<String> line) {
        return line.stream().allMatch(s -> s.isEmpty() && s.isBlank());
    }

    static boolean isCharToken(String buffer) {
        return !isLetterOrDigit(buffer) && !buffer.equals(":");
    }

    static boolean isComment(String curr, String next) {
        return curr.equals("/") && next.equals("/");
    }

    static boolean isSymbol(String buffer) {
        return Symbol.getValues().contains(buffer);
    }

    static boolean isKeyword(String buffer) {
        return Keyword.getValues()
                .contains(buffer);
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
