package org.bpc.syntax;


import org.bpc.ast.Expr;
import org.bpc.ast.LiteralExpr;

import java.util.List;

public interface SyntaxListener {
    default void startProcedure() {}
    default void exitProcedure() {}

    default void startProcedureSignature(String name, String returnType, List<String> namespace) {}
    default void exitProcedureSignature(String name, String returnType) {}

    default void startParameter(String name, String type, List<String> namespace) {}
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

    default void enterImport(List<String> namespace) {}
    default void exitImport() {}

    default void enterLiteralExpr(LiteralExpr expr) {}
    default void exitLiteralExpr() {}
}
