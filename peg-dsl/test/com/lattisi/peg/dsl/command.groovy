log.info "Test Plane Euclidean Geometry Domain Specific Language"

create triangle name "ABC"
/*
 * create triangle name "ABC" with type:scalene
 *
 */

extend "AC" to "D" with metric:"BC"
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

extend "BC" to "E" with metric:"AC"

create segment name "ED"
