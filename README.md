# README
To build use:
`mvn package`

To run use:
`./run`

This will give you a barebones REPL, where you can enter text and you will be output at most 5 suggestions sorted alphabetically.

To start the server use:
`./run --gui [--port=<port>]`

How to use:
This program is designed to take in given star data and calculate the k closest neighbors to a given star or location. To use this, start by using the stars command followed by the name of the input file to load the star data. The file should be in the format "StarID, StarName, X position, Y position, Z position" on each line in order to ensure accurate file parsing. From there, to find the k nearest neighbors type the command naive_neighbors. This should be followed by the arguments k (number of closest neighbors),  x, y, and z (the position for the stars to be compared to) separated by spaces. Additionally, if you would like to find the k closest neighbors to a named star, use the command naive_neighbors k starName where starName is the name of a star that exists in the given data file of which you want to find its neighbors. This program can also do addition and subtraction. Use add x y to add x and y together and subtract x y to subtract y from x. 


Command Guide:

add <x> <y> 
Where x and y should be any two real numbers. Returns the result of the two numbers added.

subtract <x> <y>
Where x and y should be any two real numbers. Returns the result of x-y.

stars <filename>
This will load the star data that is present in the given file. The file should be in the format "StarID, StarName, X, Y, Z" in order to work properly. The data will be loaded from  data/stars/ by default, so place any files you wish to use there. This command must be run before naive neighbors.

naive_neighbors <k> <x> <y> <z>
Given a point in space designated by the x, y, z arguments, this algorithm will find the k  number of closest neighbors to the given point. This data is returned as a list of the star IDs which are closest to the point. The stars command must be run first.

naive_neighbors <k> <starName>
Given the name of a particular star designated by starName, this algorithm will find the k number of closest neighbors to the given star. This data is returned as a list of the star IDs which are closest to the named star. The stars command must be run first.

