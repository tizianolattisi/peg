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

    Choice(2,
        NonTerminal('triangle'),
        NonTerminal('segment'),
        NonTerminal('point'),
        NonTerminal('angle'),
        NonTerminal('direction')
    ),

    NonTerminal('name'),
    Terminal('"item-name"'),

    Optional(
        Sequence(
            NonTerminal('with'),
            OneOrMore(
                Sequence(
                    Choice(1,
                        Sequence(NonTerminal('type:'), Terminal('"type"')),
                        Sequence(NonTerminal('measure:'), Terminal('"measure-string"'))
                    ),
                    Optional(",")
                )
            )
        )
    )
);

document.write(out);