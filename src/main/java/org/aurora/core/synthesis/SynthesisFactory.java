package org.aurora.core.synthesis;

import org.aurora.core.analyzer.semantic.utils.SemanticData;
import org.aurora.util.fs.IFileManager;
import org.aurora.util.parser.IPathContainer;

/**
 * @author daohn on 05/09/2020
 * @project aurora
 */
public class SynthesisFactory {

    private final IntermediateCode intermediateCode;
    private final FinalCode        finalCode;
    private final SemanticData     data;
    private final IFileManager     fileManager;
    private final IPathContainer   container;

    public SynthesisFactory(IFileManager fileManager, IPathContainer container, SemanticData data) {
        this.fileManager      = fileManager;
        this.container        = container;
        this.data             = data;
        this.intermediateCode = new IntermediateCode();
        this.finalCode        = new FinalCode();
    }

    public void initateSynthesis() {
        try {
            this.intermediateCode.extractSemanticData(data);
            var generatedPseudoAssembly = this.intermediateCode.build();
            this.finalCode.setPseudoAsm(generatedPseudoAssembly);
            var asm = this.finalCode.build();
            fileManager.writeDataOnAsmFile(asm, container);
        }
        catch(SynthesisException e) {
            e.printStackTrace();
        }
    }

}
