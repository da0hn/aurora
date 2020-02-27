package aurora.analyzer.lexical.interfaces;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * @project aurora
 * @author Gabriel Honda on 26/02/2020
 */
public interface AnalyzerService extends UnaryOperator<List<String>> {

    static AnalyzerService splitBy(String delimiter) {
//        final String formatter = "((%1$s=>?)|(?=%1$s))";
        final String formatter = "((?<=%1$s)|(?=%1$s))";
        final String formattedDelimiter = String.format(formatter, delimiter);
        return line -> line.stream()
                .map(str -> str.split(formattedDelimiter))
                .flatMap(Stream::of)
                .collect(Collectors.toList());
    }

    default AnalyzerService andThen(AnalyzerService other) {
        return line -> {
            List<String> otherResult = other.apply(line);
            return this.apply(otherResult);
        };
    }

}
