# Earthquake Message Format Utilities

Message formatting and parsing utilities for
EQXML, Quakeml 1.1, Quakeml 1.2, and Quakeml RT 1.2.

[![Travis CI Badge](https://api.travis-ci.com/usgs/eqmessageutils.svg?branch=master)](https://travis-ci.com/usgs/eqmessageutils)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/e4eba2d644ba4979b42773b9aabb03a5)](https://www.codacy.com/project/usgs/eqmessageutils/dashboard)
[![codecov](https://codecov.io/gh/usgs/eqmessageutils/branch/master/graph/badge.svg)](https://codecov.io/gh/usgs/eqmessageutils)


This java library is built using a Gradle build script (build.gradle).

To build this project, you need:
    - JDK 1.8+ ( http://openjdk.java.net/ )
    - Gradle ( https://gradle.org/ )

JAXB generated classes are versioned in this repository,
and can be (re)generated by running `gradle jaxb` using JDK 1.9+.


## Files
- Source code is in the `src/main/java` directory.
- Tests are in the `src/test/java` directory.
- Generated source code is in the `src/generated/java` directory.
- Schemas and sample files are in the `etc` directory.

## Interesting Classes
- gov.usgs.earthquake.event.Converter has methods for converting most formats.
- gov.usgs.earthquake.quakeml.FileToQuakemlConverter is an interface for parsers to create Quakeml from other formats.


## Gradle

Run `gradle build` to compile the library and generated documentation.
Files are output to the `build` directory.