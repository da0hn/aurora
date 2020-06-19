package aurora.analyzer.lexical;

import aurora.analyzer.IAnalyzer;
import aurora.analyzer.lexical.interfaces.LexicalService;
import aurora.analyzer.lexical.log.LogLexical;
import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.analyzer.lexical.utils.Tokens;
import aurora.analyzer.utils.PredicateService;
import aurora.lang.Token;

import java.util.Collections;
import java.util.List;

import static aurora.analyzer.lexical.Lexical.Controls.incrementColumn;
import static aurora.analyzer.lexical.Lexical.Controls.incrementLine;
import static aurora.analyzer.lexical.Lexical.Controls.resetColumn;
import static aurora.analyzer.lexical.Lexical.Controls.setLineLength;
import static aurora.analyzer.lexical.interfaces.LexicalService.stringAnalyzer;
import static aurora.analyzer.lexical.interfaces.LinesParserService.splitBy;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public class Lexical implements IAnalyzer {
    /*
     * classe interna responsavel pelo controle das linhas,
     * colunas e tamanho da linha
     * */
    public static class Controls {
        private static Integer line = 1;
        private static Integer column = 1;
        private static Integer lineLength;

        public static void setLineLength(List<String> line) {
            // recebe a linha atual e soma os caracteres
            // de cada String inserida na lista
            lineLength = line.stream()
                    .mapToInt(String::length)
                    .sum();
        }

        public static void incrementColumn() { column++;}

        public static void incrementColumn(Integer column) {
            Controls.column += column;
        }

        public static void incrementLine() { line++;}

        public static void resetColumn() { column = 1;}

        public static Integer getColumn() {
            return column;
        }

        public static Integer getLine() {
            return line;
        }

        public static Integer getLineLength() {
            return lineLength;
        }
    }

    private final List<String> auroraProgram;

    public Lexical(List<String> auroraProgram) {
        this.auroraProgram = auroraProgram;
    }

    @Override
    public void analyze() {
        // metodo splitBy() quebra a linha de acordo com o delimitador
        // o delimitador é mantido na lista criada para analise
        for(String line : auroraProgram) {
            var parsedLines = splitBy("\\)")
                    .andThen(splitBy("\\("))
                    .andThen(splitBy("\\s"))    // espaço
                    .andThen(splitBy("\\t"))
                    .andThen(splitBy(";"))
                    .andThen(splitBy("\""))
                    .andThen(splitBy(">"))
                    .andThen(splitBy("<"))
                    .andThen(splitBy("="))
                    .andThen(splitBy("\\+"))
                    .andThen(splitBy("\\-"))
                    .andThen(splitBy("\\*"))
                    .andThen(splitBy("/"))
                    .apply(Collections.singletonList(line));
//            System.out.println(parsedLines + " size -> " + parsedLines.size());
            analyzeLines(parsedLines);
            incrementLine();
            resetColumn();

        }
        var tk_final = new TokenContainer(Token.$, "$",
                                          Controls.getLine(), Controls.getColumn()
        );
        // LogLexical utiliza uma fila para armazenar os logs,
        // seu uso e necessario para manter a sincronizacao dos logs
        LogLexical.add(tk_final);
        // print
        LogLexical.log();
        // adiciona o simbolo '$' que sera utilizado nas outras etapas do compilador
        Tokens.add(tk_final);
        System.out.println("Lexical OK");
    }

    private void analyzeLines(List<String> lines) {
        // se a linha for considerada vazia sai do metodo
        // uma linha e considerada vazia se possui apenas espaços em brancos
        // ate o seu final
        if(PredicateService.isLineEmpty(lines)) return;
        // seta o tamanho total de caracteres da linha
        setLineLength(lines);
        var it = lines.listIterator();

        // itera a lista que representa uma linha do arquivo .au
        while(it.hasNext()) {
            // ao iniciar o iterador ele começa uma posicao fora do array[0],
            // e necessario chamar o metodo next() para move-lo para a posicao 0 (inicial)
            var current = it.next();
            // testa se é comentario
            if(current.equals("/") && it.hasNext()) {
                // checagem necessaria, já que o simbolo de divisao é o mesmo de comentario
                // para que o comentario seja reconhecido é necessario o seguinte algoritmo
                // primeiro, checa-se se o caractere atual é '/' e se o próximo caracetere existe
                // depois armazena o próximo caractere (nesse caso, 'it.next()' move o cursor do iterador para o
                // proximo caractere) e checa se é '/', assim, pode-se ignorar a linha
                // caso contrario, 'it.previous()' volta o cursor do iterador para o caractere atual
                // esse rollback é necessario para não causar bugs ou pular caracteres em caso de divisão
                var next = it.next();
                if(next.equals("/")) return;
                it.previous();
            }
            // testa se é um espaço em branco
            if(current.equals(" ") || current.equals("\t")) {
                incrementColumn();
                continue;
            }
            // testa se é uma string
            if(current.equals("\"")) {
                if(stringAnalyzer(it)) return;
                continue;
            }
            // caso nao entre em nenhuma das classificacoes anteriores
            // provavelmente entra na classificacao de token
            LexicalService.tokenAnalyzer(current);
        }
    }
}
