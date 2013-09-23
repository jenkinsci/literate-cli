# Literate CLI

 This is a utility to check and validate your literate config (.cloudbees.md and README.md)


# Build

To build the api locally:

    mvn clean verify

Now make the jar executable directly:

    cat stub.sh  target/literate-api.jar > target/literate && chmod +x target/literate
    
# Usage


put `target/literate` on your path and then:

    literate

will show help.

An example:


To build this using literate: 

    literate . build | sh

This will validate the literate build (look for a .cloudbees.md and a README.md, this file, in this case) and then run the build section.
You can also put content in the .cloudbees.md - in which case, it will read it form there.



