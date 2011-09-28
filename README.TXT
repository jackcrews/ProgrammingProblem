During an interview process I was given a "Programming Problem" so they could have a sample of code that I've written.

I really enjoyed working on this as I was able to practice TDD and the KISS principle.

PROBLEM REQUIREMENTS:

Write a password validation service, meant to be configurable via IoC (using dependency injection engine of your choice).  The service is meant to check a text string for compliance to any number of password validation rules.  The rules currently known are:

    Must consist of a mixture of lowercase letters and numerical digits only, with at least one of each.
    Must be between 5 and 12 characters in length.
    Must not contain any sequence of characters immediately followed by the same sequence. 