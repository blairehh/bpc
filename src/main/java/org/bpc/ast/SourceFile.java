package org.bpc.ast;

import org.bpc.compile.CodeFile;
import org.bpc.syntax.SyntaxListener;

import java.sql.Ref;
import java.util.*;

public class SourceFile implements SyntaxListener {
    private final String path;
    private final List<Import> imports;
    private final List<Procedure> procedures;
    private Procedure currentProcedure;
    private Block currentBlock;
    private Stack<ExprStack> exprStack = new Stack<>();
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
        this.exprStack.push(declaration);
        this.currentBlock.addStatement(declaration);
    }

    @Override
    public void exitProcedureDefinition(String name, String returnType) {
        if (!this.exprStack.isEmpty()) {
            this.exprStack.pop();
        }
    }

    @Override
    public void exitVariableDeclaration() {
        this.exprStack.pop();
    }

    @Override
    public void enterProcedureCall(String name, List<String> namespace) {
//        ProcedureCall procedureCall = new ProcedureCall(new Identifier(name, new Namespace(namespace)));
//        this.assignable.push(procedureCall);
//        this.currentBlock.addStatement(procedureCall);
        this.enteredProcedureStatement = true;
    }

    @Override
    public void exitProcedureCall() {
        this.enteredProcedureStatement = false;
        if (!this.exprStack.isEmpty()) {
            this.exprStack.pop();
        }
    }

    @Override
    public void enterExpr(Expr expr) {
        this.enteredProcedureStatement = false;
        this.exprStack.peek().push(expr);
    }

    @Override
    public void exitExpr() {
    }

    @Override
    public void enterProcedureExpr(String name, List<String> namespace) {
        if (this.enteredProcedureStatement) {
            ProcedureExpr expr = new ProcedureExpr(new Reference(name, new Namespace(namespace)), new ArrayList<>());
            ProcedureCall procedureCall = new ProcedureCall(expr);
            this.currentBlock.addStatement(procedureCall);
            this.exprStack.push(expr);
            this.enteredProcedureStatement = false;
            return;
        }
        ProcedureExpr call = new ProcedureExpr(new Reference(name, new Namespace(namespace)));
        this.exprStack.peek().push(call);
        this.exprStack.push(call);
    }

    @Override
    public void exitProcedureExpr() {
        this.exprStack.pop();
    }

    @Override
    public void enterReturnStatement() {
        ReturnStatement returnStatement = new ReturnStatement(new ExprRef());
        this.exprStack.push(returnStatement);
        this.currentBlock.addStatement(returnStatement);
    }

    @Override
    public void exitReturnStatement() {
        this.exprStack.pop();
    }

    @Override
    public void enterIdentifier(String name, List<String> namespace) {
        this.exprStack.peek().push(new Reference(name, new Namespace(namespace)));
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
            && Objects.equals(this.exprStack, that.exprStack)
            && Objects.equals(this.imports, that.imports);
    }

    @Override
    public int hashCode() {
        return Objects.hash(procedures, currentProcedure, currentBlock, exprStack, imports);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SourceFile.class.getSimpleName() + "[", "]")
            .add("procedures=" + procedures)
            .add("currentProcedure=" + currentProcedure)
            .add("currentScope=" + currentBlock)
            .add("assignable=" + exprStack)
            .add("imports=" + imports)
            .toString();
    }
}
