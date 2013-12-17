log.info "Test Plane Euclidean Geometry Domain Specific Language"
/*
make triangle name "ABC" with type:equilateral
make segment name "BC" // è un doppione!
make segment name "CD"
declare "BC" equals "CD"
*/
make triangle name "ABC"
extend "AC" to "D" with metric:"BC"
extend "BC" to "E" with metric:"AC"
make segment name "ED"
make direction name "ED"
make direction name "AB"