package aurora.analyzer.syntactic.utils;

import aurora.lang.Language;

import java.util.LinkedList;
import java.util.List;

/*
 * @project aurora
 * @author Gabriel Honda on 09/03/2020
 */
public class SyntacticService {

    public static List<Language> reverseGrammar(List<Language> grammar) {
        var reverse = new LinkedList<Language>();
        new LinkedList<>(grammar)
                .descendingIterator()
                .forEachRemaining(reverse::add);
        return reverse;
    }
}
