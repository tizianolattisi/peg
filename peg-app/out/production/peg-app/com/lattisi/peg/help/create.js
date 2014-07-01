/**
 * Created with IntelliJ IDEA.
 * User: tiziano
 * Date: 17/01/14
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */

var out =
Diagram(

    NonTerminal('create'),

    Optional(
        Sequence(
            NonTerminal('the'),
            Choice(1,
                NonTerminal('triangle'),
                NonTerminal('segment'),
                NonTerminal('point')
            )
        )
    ),

    Terminal('"name-1"'),

    Optional(
        Sequence(
            NonTerminal('with'),
            NonTerminal('measure'),
            NonTerminal('of'),
            NonTerminal('"name-2"')
        )
    )

);

document.write(out);