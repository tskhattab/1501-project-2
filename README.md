# CS1501 Project 2: Autocomplete Engine

## Goal
This project aims to build a backend for an autocompletion engine, enhancing the user experience by predicting words as the user types. The focus is on implementing search algorithms and symbol tables.

## Background
Autocomplete features on mobile devices rely heavily on efficient search algorithms. This project implements the backend logic for such a feature, without the front-end user input collection.

## Specifications
- The project consists of three main classes: `DLB`, `UserHistory`, and `AutoCompleter`, which are used to store the dictionary of English words, track user-selected words, and bring together the autocomplete functionality, respectively.

### DLB (De La Briandais Trie)
- Implemented as per the lecture description.
- Uses `DLBNode` to construct the DLB trie.
- Implements all methods from the `Dict` interface.
- Returns results of `suggest()` in "ASCIIbetical" order.

### UserHistory
- Tracks words selected by the user and their frequencies.
- Implements the `Dict` interface with the same worst-case asymptotic runtime as DLB.
- `suggest()` method returns up to 5 most frequently selected words based on a given prefix.

### AutoCompleter
- Combines `DLB` and `UserHistory` to provide autocomplete functionality.
- Supports two constructors for initializing with dictionary data and user history.
- Implements methods from `AutoComplete_Inter` interface.

## Running the Project
- Use `./gradlew run` to compile and run the application on Linux or macOS.
- The English dictionary text file should be placed in `./app/src/main/resources/`.

## Testing
- Tests are located in `./app/src/test/java/cs1501_p2/AllTest.java`.
- Run `./gradlew test` to execute tests.

## Additional Notes
- The autocomplete engine operates in a case-sensitive manner.
- Ensure all source files are part of the `cs1501_p2` package.
- Additional tests beyond those provided are encouraged for thorough validation.
