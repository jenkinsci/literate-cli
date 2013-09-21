# Literate CLI

 This is a utility to check and validate your literate config (.cloudbees.md and README.md)


# Build

To build the api locally:

    mvn clean
    mvn package

Now make the jar executable directly:

    cat stub.sh  target/literate-api.jar > target/literate && chmod +x target/literate
    
# Usage


put `target/literate` on your path and then:

    literate

will show help.

An example:

    literate /home/directory build | say

This will validate, and extract the first build command it finds - and then read it aloud.

To build this using literate: 

    literate . build | sh