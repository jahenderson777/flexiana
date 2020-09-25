# Flexiana technical test 


## Installation

Install JS dependencies:

    $ yarn

Compile the JS:

    $ npx shadow-cljs release app

Build an uberjar:

    $ clj -A:uberjar

Run the uberjar:

    $ java -jar flexiana.jar

Browse to http://localhost:3000/index.html

## Other 

Run the api without compilling an uberjar:

    $ clj -m flexiana.core

Run the project's tests:

    $ clj -A:test:runner

## Dev REPL instructions

From VS Code with Calva, run Jack-in, select `shadow-cljs`, select :app profile, wait, select :app build, wait for build to complete ~~, open `core.clj`, run `Calva: Load current file and dependencies`, wait for file to load and repl prompt to change, eval `(-main)` in the repl.~~

Browse to http://localhost:3000/index.html



## System Notes

Tested on OSX Catalina, openjdk 11.0.8

IDE = VS Code




## License

Copyright © 2020 Alex Jonathan Henderson

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
