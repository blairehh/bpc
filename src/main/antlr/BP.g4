grammar BP;


prog: WS* proc EOF;

proc: proc_def ' ' '{\n' (procedureStatement | BlankLine | '\n')* (returnStatement | BlankLine | '\n')* '}';
proc_def : 'proc ' Identifier ('(' parameter (', ' parameter)* ')' | '()') (' ' Identifier)?;
parameter: Identifier ' ' Identifier;

procedureStatement: variableDeclaration | procedureCall;

returnStatement: Indentation 'return ' expr;

variableDeclaration: Indentation Identifier ' ' Identifier ( ' = ' expr '\n' | '\n');
procedureCall: Indentation procedureCallExpr '\n';
procedureCallExpr: Identifier('(' (expr (', ' expr)*) ')' | '()');

expr: literal | procedureCallExpr;
literal: numberExpr | booleanExpr | charExpr | stringExpr;

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

BlankLine: ('\t' | ' ')* '\n' { skip(); };
Indentation:  ('\t' | ' ')*;


WS:  ('\t' | ' ' | '\r' | '\n' | '\u000C') { skip(); };
