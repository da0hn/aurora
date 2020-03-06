package aurora.analyzer.lexical;

import aurora.analyzer.lexical.interfaces.AnalyzerService;
import aurora.analyzer.lexical.utils.LogLexical;
import aurora.parser.Flag;

import java.util.Collections;
import java.util.List;

import static aurora.analyzer.lexical.Lexical.Controls.*;
import static aurora.analyzer.lexical.interfaces.AnalyzerService.stringAnalyzer;
import static aurora.analyzer.lexical.interfaces.AnalyzerService.isLineEmpty;
import static aurora.analyzer.lexical.interfaces.LinesParserService.splitBy;

/*
 * @project aurora
 * @author Gabriel Honda on 22/02/2020
 */
public class Lexical {
    /*
     * classe interna responsavel pelo controle das linhas,
     * colunas e tamanho da linha
     * */
    public static class Controls {
        private static Integer line = 1;
        private static Integer column = 1;
        private static Integer lineLength;

        public static void setLineLength(List<String> line) {
            // recebe a linha atual e soma os caracteres de cada String inserida
            // na lista
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

    public void analyze(List<String> lines) {
        // metodo splitBy() quebra a linha de acordo com o delimitador
        // o delimitador é mantido na lista criada para analise
        for(String line : lines) {
            try {
                var parsedLines = splitBy("\\)")
                        .andThen(splitBy("\\("))
                        .andThen(splitBy("\\s"))
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
                if(Flag.READABLE.getValue()) {
                    Thread.sleep(400);
                }
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        LogLexical.message("Lexical OK");
    }

    private void analyzeLines(List<String> lines) {
        // se a linha for considerada vazia sai do metodo
        // uma linha e considerada vazia se possui apenas espaços em brancos
        // ate o seu final
        if(isLineEmpty(lines)) return;
        // seta o tamanho total da linha
        setLineLength(lines);
        var it = lines.listIterator();
        // itera a lista que representa uma linha do arquivo .au
        while(it.hasNext()) {
            var current = it.next();
            // teste se é comentario
            if(current.equals("/") && it.next().equals("/")) {
                return;
            }
            // testa se é uma linha em branco
            else if(current.equals(" ")) {
                incrementColumn();
            }
            // testa se é um comentario
            else if(current.equals("\"")) {
                if(stringAnalyzer(it)) return;
            }
            // caso nao entre em nenhuma das classificacoes anteriores
            // provavelmente entra na classificacao de token
            else {
                AnalyzerService.tokenAnalyzer(current);
            }
        }
    }
}
