/*
 *  Dato un triangolo ABC si prolunghino i lati AC, BC, dalla parte di C, rispettivamente dei segmenti CD = BC e CE = AC.
 *  Sia H l'intersezione delle rette DE ed AB.
 *  Dimostrare  che il triangolo AEH è isoscele.
 */
create triangle name "ABC"
extend "AC" to "D" with measure:"BC"
extend "BC" to "E" with measure:"AC"
create segment name "ED"
extend "DE" to "H"
extend "BA" to "H"
apply "10.8" on "ad", "bc"
apply "10.3" on "CED", "ABC"
apply "10.6" on "ABC", "cba", "CED", "edc"
create segment name "BD"
apply "10.10" on "BCD", "BC", "CD"
sum "edc" and "cdb"
sum "cba" and "dbc"