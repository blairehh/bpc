package org.bpc.syntax;


import org.bpc.ast.Expr;

import java.util.List;

public interface SyntaxListener {
    default void startProcedure() {}
    default void exitProcedure() {}

    default void startProcedureDefinition(String name, String returnType, List<String> namespace) {}
    default void exitProcedureDefinition(String name, String returnType) {}

    default void startParameter(String name, String type) {}
    default void exitParameter(String name, String type) {}

    default void enterVariableDeclaration(String name, String type, List<String> namespace) {}
    default void exitVariableDeclaration() {};

    default void enterProcedureCall(String name, List<String> namespace) {}
    default void exitProcedureCall() {}

    default void enterProcedureExpr(String name, List<String> namespace) {}
    default void exitProcedureExpr() {}

    default void enterReturnStatement() {}
    default void exitReturnStatement() {}

    default void enterIdentifier(String name, List<String> namespace) {}
    default void exitIdentifier() {}

    // @TODO rename to literal expr
    default void enterExpr(Expr expr) {}
    default void exitExpr() {}
}
