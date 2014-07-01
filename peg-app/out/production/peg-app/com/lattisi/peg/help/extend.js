/**
 * Created with IntelliJ IDEA.
 * User: tiziano
 * Date: 17/01/14
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */

var out =
Diagram(

    NonTerminal('extend'),

    Optional(
        Sequence(
            NonTerminal('the'),
            NonTerminal('segment')
        )
    ),

    NonTerminal('"segment-name"'),

    NonTerminal('to'),

    Terminal('"point-name"'),

    Optional(
        Sequence(
            NonTerminal('with'),
            NonTerminal('measure'),
            NonTerminal('of'),
            NonTerminal('"segment-name"')
        )
    )

);

document.write(out);