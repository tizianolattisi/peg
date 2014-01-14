log.info "Test Plane Euclidean Geometry Domain Specific Language"

create triangle name "ABC"
/*
 * create triangle name "ABC" with type:scalene
 *
 */

extend "AC" to "D" with measure:"BC"
/*
 * extend "AC" to "D"
 * declare "CD" equal "AC"
 *
 * create segment "CD"
 * declare "CD" equal "AC"
 * create direction name "ac"
 * // add "D" to "ac" // TODO: add item to icontainer
 *
 */

extend "BC" to "E" with measure:"AC"

create segment name "ED"

extend "DE" to "H"

extend "BA" to "H"