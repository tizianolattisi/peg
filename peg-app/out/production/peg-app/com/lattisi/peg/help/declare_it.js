/**
 * Created with IntelliJ IDEA.
 * User: tiziano
 * Date: 17/01/14
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */

var out =
Diagram(

    NonTerminal('dichiara'),

    Optional(
        Sequence(
            NonTerminal('il'),
            Choice(1,
                NonTerminal('triangolo'),
                NonTerminal('segmento')
            )
        )
    ),

    NonTerminal('"nome1"'),

    NonTerminal('uguale'),

    NonTerminal('"nome2"'),

    NonTerminal('per'),

    NonTerminal('"nome-teorema"')

);

document.write(out);