## Prerequisites

- If you would like to build the JAR file yourself, you will need to install the Java Development Kit (JDK 19.0.1)
on your machine
- If you would only like to run the provided JAR file, you will just need Java installed

## Running the Program

To run this file, run the following command while being in the root directory of this project: \
`java -jar out/artifacts/spellcheck_jar/spellcheck.jar <PATH TO DICTIONARY FILE> <PATH TO INPUT FILE>` \
Make sure to replace the two arguments with the paths to your respective files. For an example output, you can use the
two files included in this project by running the command:
`java -jar out/artifacts/spellcheck_jar/spellcheck.jar dictionary.txt inputfile.txt`

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
9. I am making a soft assumption that the input file does not contain multiple whitespace characters next to each other
for the purpose of simplifying the process of providing context to the misspelled words. Given more time, I would've
opted to process context using an in-place method that would preserve the line as is and reduce duplicated processing,
most likely using a "sliding window" type of approach. I would've also potentially re-organized my files in order to 
cleanly provide context to _only_ the misspelled words instead of every word, as I'm doing now. I opted to not blur the
lines of my **SpellCheckService** and **InputFileService** over the small performance optimization of only providing
context for misspelled words.
10. I would have chosen a different word suggestion algorithm given more time because after implementing and testing 
further, I realized that Levenshtein heavily favors insertions and deletions over substitutions. This means that 
transposing two letters, which anecdotally seem to be the most common type of typos, will be given accurate
suggestions much less frequently as each transposition commonly causes a levenshtein distance of 2 from the desired 
word.
11. For spellchecking proper nouns, I am simply ignoring words that start with an uppercase letter. Given more time, I 
would try to implement an intelligent context analysis approach.