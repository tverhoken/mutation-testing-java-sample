# Mutation testing Java sample

This project is a sample used for demoing mutation testing with Java technology.

**This is not production ready code**

## Getting started

### Prerequisites

You will need the following things properly installed on your computer :

* [Git](https://git-scm.com/)
* [Java](https://openjdk.org) >= 17
* [Maven](https://maven.apache.org)

### Installation

```
$> git clone <repository>
$> cd <repository>
$> mvn clean package
```

### Usage

You can find every steps of the demo listed by the command :

```shell
$> git tag -l
```

You can now navigate through the steps in order to see code evolution.

### Running tests

* `mvn clean test`
* Mutation testing : `mvn clean test pitest:mutationCoverage`


## Contacts

Thomas VERHOKEN [![https://twitter.com/tverhoken][1.1]][1]

[1]: https://twitter.com/tverhoken
[1.1]: http://i.imgur.com/wWzX9uB.png