package org.aurora.core.analyzer.semantic.utils;

import org.aurora.core.analyzer.lexical.utils.TokenContainer;

import java.util.List;

/**
 * @author Gabriel Honda on 05/07/2020
 * @project org.aurora
 */
public record SemanticData(List<TokenContainer> tokens,
                           List<NameMangling> table
) {
}
