package org.aurora.core.analyzer.semantic.utils;

import org.aurora.core.analyzer.lexical.utils.TokenContainer;

import java.util.List;

/**
 * @author Gabriel Honda on 05/07/2020
 * @project org.aurora
 */
public class SemanticData {
    private final List<TokenContainer> tokens;
    private final List<NameMangling> table;

    public SemanticData(List<TokenContainer> tokens,
                        List<NameMangling> table) {
        this.tokens = tokens;
        this.table = table;
    }

    public List<TokenContainer> tokens() {
        return tokens;
    }

    public List<NameMangling> table() {
        return table;
    }
}
