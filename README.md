## Running the Program
Insert instructions on running the program here

## Assumptions and Decisions Under Time Pressure

1. Column number of a line in the input file begins at 1
2. I am assuming the input file contains only words, punctuation, and whitespace.
3. For simplicity, when parsing the input file, I am not properly handling contractions, compound nouns that are made up
of two or more separate words (i.e. ice cream) or hyphenated words. The provided dictionary file didn't include any of
those types of words, so it seemed like a fine compromise to save some time.
4. I am only including a couple of unit tests using Jest and Mockito. Without time pressure,
I would normally include a full test suite (including tests that show off Mockito a bit more) 
testing positive, negative, and edge cases of each public method in each service and object.
5. I am showing duplicated misspelled words in their own blocks, when it could be nice to have an aggregate view that
shows all instances of a misspelled word under one block.
6. I am currently assuming correctness in the format of the dictionary file, and not handling validation of that format.
7. I am assuming that the input file is not so large that loading the whole file into memory would be an issue. If that 
was a concern under normal circumstances, I would break the processing of the file into smaller chunks.
8. I would normally include a config file instead of hardcoding constants for configuration values, but chose to do the
latter given the time constraint.
