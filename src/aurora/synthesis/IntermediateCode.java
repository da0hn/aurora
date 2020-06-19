package aurora.synthesis;

import aurora.analyzer.lexical.utils.TokenContainer;
import aurora.analyzer.lexical.utils.Tokens;
import aurora.analyzer.semantic.utils.NameMangling;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Gabriel Honda on 19/06/2020
 * @project aurora
 */
public class IntermediateCode {

    // representa o código fonte (fila)
    private LinkedList<String> code;
    private List<TokenContainer> tokens;
    private List<NameMangling> variables;

    public IntermediateCode(List<NameMangling> variables) {
        this.variables = variables;
        this.code = new LinkedList<>();
        this.tokens = new ArrayList<>(Tokens.get());
    }


}
