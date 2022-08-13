package org.bpc.ast;

import org.bpc.compile.CodeFile;
import org.bpc.syntax.SyntaxListener;

import java.util.*;

public class SourceFile implements SyntaxListener {
    private final String path;
    private final List<Import> imports;
    private final List<Procedure> procedures;
    private Procedure currentProcedure;
    private Block currentBlock;
    private Stack<Assignable> assignable = new Stack<>();
    private boolean enteredProcedureStatement = false;


    public SourceFile(String path, List<Import> imports, Procedure... procedures) {
        this.path = path;
        this.imports = imports;
        this.procedures = List.of(procedures);
    }

    public SourceFile(String path, Procedure... procedures) {
        this.path = path;
        this.imports = new ArrayList<>();
        this.procedures = List.of(procedures);
    }

    public SourceFile(String path) {
        this.path = path;
        this.imports = new ArrayList<>();
        this.procedures = new ArrayList<>();
        this.currentProcedure = null;
    }

    @Override
    public void startProcedureDefinition(String name, String returnType, List<String> namespace) {
        this.currentProcedure = new Procedure(
            name,
            returnType == null ? null : new Type(returnType, new Namespace(namespace))
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
        Parameter parameter = new Parameter(name, new Type(type, new Namespace(namespace)));
        this.currentProcedure.parameters().add(parameter);
    }

    @Override
    public void exitParameter(String name, String type) {
    }

    @Override
    public void enterVariableDeclaration(String name, String type, List<String> namespace) {
        VariableDeclaration declaration = new VariableDeclaration(name, new Type(type, new Namespace(namespace)));
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
        ProcedureCall procedureCall = new ProcedureCall(new Identifier(name, new Namespace(namespace)));
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
        ProcedureCall call = new ProcedureCall(new Identifier(name, new Namespace(namespace)));
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
        this.assignable.peek().assign(new Identifier(name, new Namespace(namespace)));
    }

    @Override
    public void exitIdentifier() {
    }

    @Override
    public void enterImport(List<String> namespace) {
        this.imports.add(new Import(new Namespace(namespace)));
    }

    @Override
    public void exitImport() {
    }

    public CodeFile toCodeFile() {
        return new CodeFile(this.path, this.imports, this.procedures);
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
            && Objects.equals(this.imports, that.imports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(procedures, currentProcedure, currentBlock, assignable, imports);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SourceFile.class.getSimpleName() + "[", "]")
            .add("procedures=" + procedures)
            .add("currentProcedure=" + currentProcedure)
            .add("currentScope=" + currentBlock)
            .add("assignable=" + assignable)
            .add("imports=" + imports)
            .toString();
    }
}
