package org.bpc.syntax;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.bpc.ast.BoolExpr;
import org.bpc.ast.CharExpr;
import org.bpc.ast.NumberExpr;
import org.bpc.ast.StringExpr;
import org.bpc.grammar.BPListener;
import org.bpc.grammar.BPParser;

import java.util.List;
import java.util.Optional;

public class SyntaxListenerWrapper implements BPListener {

    private final SyntaxListener listener;

    public SyntaxListenerWrapper(SyntaxListener listener) {
        this.listener = listener;
    }

    public List<String> namespace(String value) {
        return Optional.ofNullable(value)
            .map((content) -> List.of(content.replace(":", "").split("\\.")))
            .orElse(List.of());
    }

    public List<String> namespace(TerminalNode node) {
        return Optional.ofNullable(node)
            .map(ParseTree::getText)
            .map((value) -> List.of(value.replace(":", "").split("\\.")))
            .orElse(List.of());
    }

    @Override
    public void enterProg(BPParser.ProgContext ctx) {

    }

    @Override
    public void exitProg(BPParser.ProgContext ctx) {

    }

    @Override
    public void enterProc(BPParser.ProcContext ctx) {
        this.listener.startProcedure();
    }

    @Override
    public void exitProc(BPParser.ProcContext ctx) {
        this.listener.exitProcedure();
    }

    @Override
    public void enterProc_def(BPParser.Proc_defContext ctx) {
        this.listener.startProcedureDefinition(
            ctx.Identifier().get(0).getText(),
            Optional.ofNullable(ctx.Identifier(1)).map(TerminalNode::getText).orElse(null),
            namespace(ctx.Namespace())
        );
    }

    @Override
    public void exitProc_def(BPParser.Proc_defContext ctx) {
        this.listener.exitProcedureDefinition(
            ctx.Identifier().get(0).getText(),
            Optional.ofNullable(ctx.Identifier(1)).map(TerminalNode::getText).orElse(null)
        );
    }

    @Override
    public void enterParameter(BPParser.ParameterContext ctx) {
        this.listener.startParameter(ctx.Identifier(1).getText(), ctx.Identifier(0).getText());
    }

    @Override
    public void exitParameter(BPParser.ParameterContext ctx) {
        this.listener.exitParameter(ctx.Identifier(1).getText(), ctx.Identifier(0).getText());
    }


    @Override
    public void enterProcedureStatement(BPParser.ProcedureStatementContext ctx) {

    }

    @Override
    public void exitProcedureStatement(BPParser.ProcedureStatementContext ctx) {

    }

    @Override
    public void enterVariableDeclaration(BPParser.VariableDeclarationContext ctx) {
        this.listener.enterVariableDeclaration(ctx.Identifier(1).getText(), ctx.Identifier().get(0).getText());
    }

    @Override
    public void exitVariableDeclaration(BPParser.VariableDeclarationContext ctx) {
        this.listener.exitVariableDeclaration();
    }

    @Override
    public void enterProcedureCall(BPParser.ProcedureCallContext ctx) {
        this.listener.enterProcedureCall(
            ctx.procedureCallExpr().Identifier().getText(),
            namespace(ctx.procedureCallExpr().Namespace())
        );
    }

    @Override
    public void exitProcedureCall(BPParser.ProcedureCallContext ctx) {
        this.listener.exitProcedureCall();
    }

    @Override
    public void enterProcedureCallExpr(BPParser.ProcedureCallExprContext ctx) {
        this.listener.enterProcedureExpr(
            ctx.Identifier().getText(),
            namespace(ctx.Namespace())
        );
    }

    @Override
    public void exitProcedureCallExpr(BPParser.ProcedureCallExprContext ctx) {
        this.listener.exitProcedureExpr();
    }

    @Override
    public void enterExpr(BPParser.ExprContext ctx) {

    }

    @Override
    public void exitExpr(BPParser.ExprContext ctx) {

    }

    @Override
    public void enterLiteral(BPParser.LiteralContext ctx) {

    }

    @Override
    public void exitLiteral(BPParser.LiteralContext ctx) {

    }

    @Override
    public void enterNumberExpr(BPParser.NumberExprContext ctx) {
        this.listener.enterExpr(new NumberExpr(ctx.NUMBER().getText()));
    }

    @Override
    public void enterBooleanExpr(BPParser.BooleanExprContext ctx) {
        this.listener.enterExpr(new BoolExpr(Boolean.parseBoolean(ctx.BOOLEAN().getText())));
    }

    @Override
    public void exitBooleanExpr(BPParser.BooleanExprContext ctx) {
        this.listener.exitExpr();
    }

    @Override
    public void enterCharExpr(BPParser.CharExprContext ctx) {
        String original = ctx.CHAR().getText();
        this.listener.enterExpr(new CharExpr(original.substring(1, original.length() - 1)));
    }

    @Override
    public void exitCharExpr(BPParser.CharExprContext ctx) {
        this.listener.exitExpr();
    }

    @Override
    public void enterStringExpr(BPParser.StringExprContext ctx) {
        String original = ctx.STRING().getText();
        if (original.equals("\"\"")) {
            this.listener.enterExpr(new StringExpr(""));
        } else {
            this.listener.enterExpr(new StringExpr(original.substring(1, original.length() - 1)));
        }
    }

    @Override
    public void exitStringExpr(BPParser.StringExprContext ctx) {
        this.listener.exitExpr();
    }

    @Override
    public void exitNumberExpr(BPParser.NumberExprContext ctx) {
        this.listener.exitExpr();
    }

    @Override
    public void enterReturnStatement(BPParser.ReturnStatementContext ctx) {
        this.listener.enterReturnStatement();
    }

    @Override
    public void exitReturnStatement(BPParser.ReturnStatementContext ctx) {
        this.listener.exitReturnStatement();
    }

    @Override
    public void enterIdentifier(BPParser.IdentifierContext ctx) {
        this.listener.enterIdentifier(ctx.Identifier().getText(), namespace(ctx.Namespace()));
    }

    @Override
    public void exitIdentifier(BPParser.IdentifierContext ctx) {
        this.listener.exitIdentifier();
    }

    @Override
    public void visitTerminal(TerminalNode node) {

    }

    @Override
    public void visitErrorNode(ErrorNode node) {

    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {

    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {

    }
}
