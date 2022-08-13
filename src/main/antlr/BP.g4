grammar BP;


prog: (mimport | BlankLine | NewLine)* (proc | BlankLine | NewLine)+;

mimport: 'use ' (Identifier | IdentifierWithDot) '\n';

proc: proc_def ' ' '{\n' (procedureStatement | BlankLine | '\n')* (returnStatement | BlankLine | NewLine)* '}';
proc_def : 'proc ' Identifier ('(' parameter (', ' parameter)* ')' | '()') (' ' Namespace? Identifier)?;
parameter: Namespace? Identifier ' ' Identifier;

procedureStatement: variableDeclaration | procedureCall '\n';

returnStatement: Indentation 'return ' expr;

variableDeclaration: Indentation Namespace? Identifier ' ' Identifier ' = ' expr;
procedureCall: Indentation procedureCallExpr;
procedureCallExpr: Namespace? Identifier('(' (expr (', ' expr)*) ')' | '()');

expr: literal | procedureCallExpr | identifier;
literal: numberExpr | booleanExpr | charExpr | stringExpr;

identifier: Namespace? Identifier;

numberExpr: NUMBER;
booleanExpr: BOOLEAN;
charExpr: CHAR;
stringExpr: STRING;

NUMBER: '-'?DIGIT+ ([.] DIGIT+)?;
fragment DIGIT: [0-9];

BOOLEAN: 'true' | 'false';
CHAR: '\'' . '\'';
STRING: '"' .* '"'; // @TODO replace .* with something to so greedy

Identifier: [a-z][a-zA-Z0-9-]*;
Namespace: [a-z][.a-z-]* ':';

IdentifierWithDot: [a-z][.a-zA-Z-]+;

NewLine: '\n';
BlankLine: ('\t' | ' ')* '\n' { skip(); };
Indentation:  ('\t' | ' ')+;

