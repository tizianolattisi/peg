/**
 * Created with IntelliJ IDEA.
 * User: tiziano
 * Date: 17/01/14
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */

var out =
Diagram(

    NonTerminal('estendi'),

    Optional(
        Sequence(
            NonTerminal('il'),
            NonTerminal('segmento')
        )
    ),

    NonTerminal('"nome-segmento"'),

    NonTerminal('a'),

    Terminal('"nome-punto"'),

    Optional(
        Choice(1,
            Sequence(
                NonTerminal('con'),
                NonTerminal('[attributo]'),
                NonTerminal('di'),
                NonTerminal('"nome"')
            ),
            Sequence(
                NonTerminal('come'),
                NonTerminal('[ruolo]'),
                NonTerminal('di'),
                NonTerminal('"nome"')
            )
        )
    )


);

document.write(out);