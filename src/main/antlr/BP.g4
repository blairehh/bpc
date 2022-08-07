grammar BP;


prog: (proc | BlankLine | NewLine)+;

proc: proc_def ' ' '{\n' (procedureStatement | BlankLine | '\n')* (returnStatement | BlankLine | NewLine)* '}';
proc_def : 'proc ' Identifier ('(' parameter (', ' parameter)* ')' | '()') (' ' Identifier)?;
parameter: Identifier ' ' Identifier;

procedureStatement: variableDeclaration | procedureCall '\n';

returnStatement: Indentation 'return ' expr;

variableDeclaration: Indentation Identifier ' ' Identifier ' = ' expr;
procedureCall: Indentation procedureCallExpr;
procedureCallExpr: Identifier('(' (expr (', ' expr)*) ')' | '()');

expr: literal | procedureCallExpr | identifier;
literal: numberExpr | booleanExpr | charExpr | stringExpr;

identifier: Identifier;

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

NewLine: '\n';
BlankLine: ('\t' | ' ')* '\n' { skip(); };
Indentation:  ('\t' | ' ')+;

