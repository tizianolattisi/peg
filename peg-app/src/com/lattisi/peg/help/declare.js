/**
 * Created with IntelliJ IDEA.
 * User: tiziano
 * Date: 17/01/14
 * Time: 22:31
 * To change this template use File | Settings | File Templates.
 */

var out =
Diagram(

    NonTerminal('declare'),

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

    NonTerminal('"name-1"'),

    NonTerminal('equals'),

    NonTerminal('"name-2"'),

    NonTerminal('due'),

    NonTerminal('"theorem-name"')

);

document.write(out);