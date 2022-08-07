package org.bpc.syntax;


import org.bpc.ast.Expr;

public interface SyntaxListener {
    default void startProcedure() {}
    default void exitProcedure() {}

    default void startProcedureDefinition(String name, String returnType) {}
    default void exitProcedureDefinition(String name, String returnType) {}

    default void startParameter(String name, String type) {}
    default void exitParameter(String name, String type) {}

    default void enterVariableDeclaration(String name, String type) {}
    default void exitVariableDeclaration() {};

    default void enterProcedureCall(String name) {}
    default void exitProcedureCall() {}

    default void enterExpr(Expr expr) {}
    default void exitExpr() {}
}
