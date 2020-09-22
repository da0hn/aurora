package org.aurora.core.analyzer.factory;

import org.aurora.core.analyzer.lexical.Lexical;
import org.aurora.core.analyzer.lexical.utils.TokenContainer;
import org.aurora.core.analyzer.semantic.Semantic;
import org.aurora.core.analyzer.semantic.utils.SemanticData;
import org.aurora.core.analyzer.syntactic.Syntactic;
import org.aurora.core.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Honda on 03/07/2020
 * @project org.aurora
 */
public class AnalyzerFactory {

    private final Lexical                   lex;
    private final Syntactic                 syn;
    private final Semantic                  sem;
    private       ArrayList<TokenContainer> tokens;

    public AnalyzerFactory(List<String> code) {
        this.tokens = new ArrayList<>();
        this.lex    = new Lexical(code);
        this.syn    = new Syntactic();
        this.sem    = new Semantic();
    }

    public AnalyzerFactory initializeAllAnalysis() {
        Logger.log("Iniciando análise léxica");
        lex.analyze(tokens);
        Logger.log("Análise léxica concluída");

        Logger.log("Iniciando análise sintática");
        syn.analyze(tokens);
        Logger.log("Análise sintática concluída");

        Logger.log("Iniciando semântica sintática");
        sem.analyze(tokens);
        Logger.log("Análise semântica concluída");
        return this;
    }

    public AnalyzerFactory initializeLexicalAnalysis() {
        Logger.log("Iniciando análise léxica");
        lex.analyze(tokens);
        Logger.log("Análise léxica concluída");
        return this;
    }

    public AnalyzerFactory initializeSyntacticAnalysis() {
        Logger.log("Iniciando análise sintática");
        syn.analyze(tokens);
        Logger.log("Análise sintática concluída");
        return this;
    }

    public AnalyzerFactory initializeSemanticAnalysis() {
        Logger.log("Iniciando semântica sintática");
        sem.analyze(tokens);
        Logger.log("Análise semântica concluída");
        return this;
    }


    public SemanticData getGeneratedData() {
        return sem.getSemanticData();
    }
}
