package org.aurora.core.analyzer;

import org.aurora.core.analyzer.lexical.utils.TokenContainer;

import java.util.List;

/*
 * @project org.aurora
 * @author Gabriel on 12/05/2020
 */
public interface IAnalyzer {
    void analyze(List<TokenContainer> tokens);
}
