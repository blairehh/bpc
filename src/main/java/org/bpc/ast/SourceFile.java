package org.bpc.ast;

import org.bpc.syntax.SyntaxListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class SourceFile implements SyntaxListener {
    private final List<Procedure> procedures;
    private Procedure currentProcedure;
    private Scope currentScope;
    private Assignable currentAssignable;

    public SourceFile(Procedure... procedures) {
        this.procedures = List.of(procedures);
    }

    public SourceFile() {
        this.procedures = new ArrayList<>();
        this.currentProcedure = null;
    }

    @Override
    public void startProcedureDefinition(String name, String returnType) {
        this.currentProcedure = new Procedure(name, returnType);
        this.currentScope = this.currentProcedure.scope();
        this.procedures.add(this.currentProcedure);
    }

    @Override
    public void exitProcedure() {
        this.currentProcedure = null;
        this.currentScope = null;
    }

    @Override
    public void startParameter(String name, String type) {
        Parameter parameter = new Parameter(name, type);
        this.currentProcedure.parameters().add(parameter);
    }

    @Override
    public void exitParameter(String name, String type) {
    }

    @Override
    public void enterVariableDeclaration(String name, String type) {
        VariableDeclaration declaration = new VariableDeclaration(name, type);
        this.currentAssignable = declaration;
        this.currentScope.addStatement(declaration);
    }

    @Override
    public void exitProcedureDefinition(String name, String returnType) {
        this.currentAssignable = null;
    }

    @Override
    public void exitVariableDeclaration() {
        this.currentAssignable = null;
    }

    @Override
    public void enterProcedureCall(String name) {
        ProcedureCall procedureCall = new ProcedureCall(name);
        this.currentAssignable = procedureCall;
        this.currentScope.addStatement(procedureCall);
    }

    @Override
    public void exitProcedureCall() {
        this.currentAssignable = null;
    }

    @Override
    public void enterExpr(Expr expr) {
        this.currentAssignable.assign(expr);
    }

    @Override
    public void exitExpr() {
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
            && Objects.equals(this.currentScope, that.currentScope)
            && Objects.equals(this.currentAssignable, that.currentAssignable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(procedures, currentProcedure, currentScope, currentAssignable);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SourceFile.class.getSimpleName() + "[", "]")
            .add("procedures=" + procedures)
            .add("currentProcedure=" + currentProcedure)
            .add("currentScope=" + currentScope)
            .add("currentAssignable=" + currentAssignable)
            .toString();
    }
}
