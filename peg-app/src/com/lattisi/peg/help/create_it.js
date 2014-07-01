/**
 * Created with IntelliJ IDEA.
 * User: tiziano
 * Date: 17/01/14
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */

var out =
Diagram(

    NonTerminal('crea'),

    Optional(
        Sequence(
            NonTerminal('il'),
            Choice(1,
                NonTerminal('triangolo'),
                NonTerminal('segmento'),
                NonTerminal('punto')
            )
        )
    ),

    Terminal('"nome1"'),

    Optional(
        Choice(1,
            Sequence(
                NonTerminal('con'),
                NonTerminal('[attributo]'),
                NonTerminal('di'),
                NonTerminal('"nome2"')
            ),
            Sequence(
                NonTerminal('come'),
                NonTerminal('[ruolo]'),
                NonTerminal('di'),
                NonTerminal('"nome2"')
            )
        )
    )

);

document.write(out);