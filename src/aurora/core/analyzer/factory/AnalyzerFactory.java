package aurora.core.analyzer.factory;

import aurora.core.analyzer.lexical.Lexical;
import aurora.core.analyzer.lexical.utils.TokenContainer;
import aurora.core.analyzer.semantic.Semantic;
import aurora.core.analyzer.semantic.utils.SemanticData;
import aurora.core.analyzer.syntactic.Syntactic;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Honda on 03/07/2020
 * @project aurora
 */
public class AnalyzerFactory {

    private final Lexical lex;
    private final Syntactic syn;
    private final Semantic sem;

    public AnalyzerFactory(List<String> code) {
        this.lex = new Lexical(code);
        this.syn = new Syntactic();
        this.sem = new Semantic();
    }

    public AnalyzerFactory initializeAnalysis() {
        List<TokenContainer> tokens = new ArrayList<>();

        lex.analyze(tokens);
        syn.analyze(tokens);
        sem.analyze(tokens);

        return this;
    }

    public SemanticData getGeneratedData() {
        return sem.getSemanticData();
    }
}
