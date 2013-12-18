log.info "Test Plane Euclidean Geometry Domain Specific Language"

make triangle name "ABC"
/*
 * make triangle name "ABC" with type:scalene
 *
 */

extend "AC" to "D" with metric:"BC"
/*
 * extend "AC" to "D"
 * declare "CD" equal "AC"
 *
 * make segment "CD"
 * declare "CD" equal "AC"
 * make direction name "ac"
 * // add "D" to "ac" // TODO: add item to icontainer
 *
 */

extend "BC" to "E" with metric:"AC"

make segment name "ED"
