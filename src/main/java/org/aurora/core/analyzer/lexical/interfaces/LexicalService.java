package org.aurora.core.analyzer.lexical.interfaces;

import org.aurora.core.analyzer.lexical.Lexical.Controls;
import org.aurora.core.analyzer.lexical.log.LogLexical;
import org.aurora.core.analyzer.lexical.utils.TokenContainer;
import org.aurora.core.lang.Symbol;
import org.aurora.core.lang.Terminal;
import org.aurora.core.lang.Token;

import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.Consumer;

import static org.aurora.core.analyzer.lexical.Lexical.Controls.getColumn;
import static org.aurora.core.analyzer.lexical.Lexical.Controls.getLine;
import static org.aurora.core.analyzer.lexical.Lexical.Controls.incrementColumn;
import static org.aurora.core.analyzer.lexical.interfaces.BufferAnalyzer.identifier;
import static org.aurora.core.analyzer.lexical.interfaces.BufferAnalyzer.number;
import static org.aurora.core.analyzer.lexical.interfaces.BufferAnalyzer.symbol;

/*
 * @project org.aurora
 * @author Gabriel Honda on 01/03/2020
 */
public interface LexicalService {

    static void tokenAnalyzer(List<TokenContainer> tokens, String buffer) {
        /*executa varios testes consecutivos utilizando o pattern composite
         * e a interface funcional predicate, se o buffer for enquadrado em
         * alguma das classificacoes sera instanciado um Enum que implementa
         * a interface de marcacao IToken para ser inserido na lista logo em seguida
         * */
        Optional<Terminal> optToken = BufferAnalyzer.keyword().orElse(identifier())
                .orElse(number()).orElse(symbol()).apply(buffer);
        // insere no obj Tokens o obj do tipo IToken
        // e cria um log no console informando o usuario
        Consumer<Terminal> addAndLog = tk -> {
            var temp = new TokenContainer(tk, buffer, Controls.getLine(), Controls.getColumn());
            tokens.add(temp);
            LogLexical.add(temp);
        };
        // caso o lexema nao seja reconhecido cria um log no
        // console informando o erro, a linha e a coluna
        Runnable logError = () -> LogLexical.error("the lexeme was not recognized: " + buffer,
                                                   Controls.getLine(), Controls.getColumn()
        );
        /*
         * optToken e do tipo Optional para evitar erros com NullPointerException
         * o método ifPresentOrElse() executa o metodo addAndLog() caso optToken
         * nao seja nulo, caso contrario executa o método logError()
         * */
        optToken.ifPresentOrElse(addAndLog, logError);
        // incrementa a coluna com o total de caracteres da string
        Controls.incrementColumn(buffer.length());
    }

    static boolean stringAnalyzer(List<TokenContainer> tokens, ListIterator<String> it) {
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
            var tk = new TokenContainer(
                    Token.STRING,
                    buffer.toString(),
                    getLine(),
                    getColumn()
            );
            LogLexical.add(tk);
            tokens.add(tk);
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

    static boolean isSymbol(String buffer) {
        return Symbol.getValues().contains(buffer);
    }

    static boolean isKeyword(String buffer) {
        return Token.getKeywords().contains(buffer);
    }
}
