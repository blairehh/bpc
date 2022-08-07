package org.bpc.syntax;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.bpc.grammar.BPParser;

public class SyntaxCompilation {
    private final BPParser parser;

    public SyntaxCompilation(BPParser parser) {
        this.parser = parser;
    }

    public boolean hasErrors() {
        return parser.getNumberOfSyntaxErrors() != 0;
    }

    public void walk(SyntaxListener listener) {
        ParseTree tree = parser.prog();

        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new SyntaxListenerWrapper(listener), tree);
    }
}
