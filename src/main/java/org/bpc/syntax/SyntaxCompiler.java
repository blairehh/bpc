package org.bpc.syntax;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.bpc.grammar.BPLexer;
import org.bpc.grammar.BPParser;

public class SyntaxCompiler {
    public SyntaxCompilation compile(String code) {
        BPLexer lexer = new BPLexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        return new SyntaxCompilation(new BPParser(tokens));
    }
}
