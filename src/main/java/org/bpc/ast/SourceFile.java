package org.bpc.ast;

import org.bpc.syntax.SyntaxListener;

import java.util.*;

public class SourceFile implements SyntaxListener {
    private final List<Use> uses;
    private final List<Procedure> procedures;
    private Procedure currentProcedure;
    private Block currentBlock;
    private Stack<Assignable> assignable = new Stack<>();
    private boolean enteredProcedureStatement = false;


    public SourceFile(List<Use> use, Procedure... procedures) {
        this.uses = use;
        this.procedures = List.of(procedures);
    }

    public SourceFile(Procedure... procedures) {
        this.uses = new ArrayList<>();
        this.procedures = List.of(procedures);
    }

    public SourceFile() {
        this.uses = new ArrayList<>();
        this.procedures = new ArrayList<>();
        this.currentProcedure = null;
    }

    @Override
    public void startProcedureDefinition(String name, String returnType, List<String> namespace) {
        this.currentProcedure = new Procedure(
            name,
            returnType == null ? null : new Type(returnType, namespace)
        );
        this.currentBlock = this.currentProcedure.block();
        this.procedures.add(this.currentProcedure);
    }

    @Override
    public void exitProcedure() {
        this.currentProcedure = null;
        this.currentBlock = null;
    }

    @Override
    public void startParameter(String name, String type, List<String> namespace) {
        Parameter parameter = new Parameter(name, new Type(type, namespace));
        this.currentProcedure.parameters().add(parameter);
    }

    @Override
    public void exitParameter(String name, String type) {
    }

    @Override
    public void enterVariableDeclaration(String name, String type, List<String> namespace) {
        VariableDeclaration declaration = new VariableDeclaration(name, new Type(type, namespace));
        this.assignable.push(declaration);
        this.currentBlock.addStatement(declaration);
    }

    @Override
    public void exitProcedureDefinition(String name, String returnType) {
        if (!this.assignable.isEmpty()) {
            this.assignable.pop();
        }
    }

    @Override
    public void exitVariableDeclaration() {
        this.assignable.pop();
    }

    @Override
    public void enterProcedureCall(String name, List<String> namespace) {
        ProcedureCall procedureCall = new ProcedureCall(new Identifier(name, namespace));
        this.assignable.push(procedureCall);
        this.currentBlock.addStatement(procedureCall);
        this.enteredProcedureStatement = true;
    }

    @Override
    public void exitProcedureCall() {
        this.enteredProcedureStatement = false;
        if (!this.assignable.isEmpty()) {
            this.assignable.pop();
        }
    }

    @Override
    public void enterExpr(Expr expr) {
        this.enteredProcedureStatement = false;
        this.assignable.peek().assign(expr);
    }

    @Override
    public void exitExpr() {
    }

    @Override
    public void enterProcedureExpr(String name, List<String> namespace) {
        if (this.enteredProcedureStatement) {
            this.enteredProcedureStatement = false;
            return;
        }
        ProcedureCall call = new ProcedureCall(new Identifier(name, namespace));
        this.assignable.peek().assign(call);
        this.assignable.push(call);
    }

    @Override
    public void exitProcedureExpr() {
        this.assignable.pop();
    }

    @Override
    public void enterReturnStatement() {
        ReturnStatement returnStatement = new ReturnStatement(new ExprRef());
        this.assignable.push(returnStatement);
        this.currentBlock.addStatement(returnStatement);
    }

    @Override
    public void exitReturnStatement() {
        this.assignable.pop();
    }

    @Override
    public void enterIdentifier(String name, List<String> namespace) {
        this.assignable.peek().assign(new Identifier(name, namespace));
    }

    @Override
    public void exitIdentifier() {
    }

    @Override
    public void enterUse(List<String> namespace) {
        this.uses.add(new Use(namespace));
    }

    @Override
    public void exitUse() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || !this.getClass().equals(o.getClass())) {
            return false;
        }

        SourceFile that = (SourceFile) o;

        return Objects.equals(this.procedures, that.procedures)
            && Objects.equals(this.currentProcedure, that.currentProcedure)
            && Objects.equals(this.currentBlock, that.currentBlock)
            && Objects.equals(this.assignable, that.assignable)
            && Objects.equals(this.uses, that.uses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(procedures, currentProcedure, currentBlock, assignable, uses);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SourceFile.class.getSimpleName() + "[", "]")
            .add("procedures=" + procedures)
            .add("currentProcedure=" + currentProcedure)
            .add("currentScope=" + currentBlock)
            .add("assignable=" + assignable)
            .add("uses=" + uses)
            .toString();
    }
}
