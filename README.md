Ident
=====

*Because IDs aren't always just integers.*

Ident is a Scala library for working with Identifiers (IDs) of arbitrary types.
By enforcing certain constraints, developers are prevented from doing things 
they shouldn't (like arithmetic operations on IDs, wtf?).

The project consists of several modules:

* `ident-core` - The core Identifier abstractions and basic Identifier types.
* `ident-uuid` - A module for working with all kinds of UUIDs in a sane, 
  type-safe and useful way.

More modules may be added to add support for other forms of IDs.

NOTE: The license for this project is the Apache Software License version 2.0.
LICENSE file to be added shortly.

Requirements
------------

* Scala 2.9.1

Dependencies
------------

To use `ident-core`, simply add it as a dependency:

```xml
<dependencies>
  <dependency>
    <groupId>net.nicktelford</groupId>
    <artifactId>ident-core_${scala.version}</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

Or whatever you need to do to make `ivy`/`sbt`/`gradle`/etc. happy.

For other modules, simply include them instead:

```xml
<dependencies>
  <dependency>
    <groupId>net.nicktelford</groupId>
    <artifactId>ident-uuid_${scala.version}</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

If you're not using `ident-core` directly, you can omit it from the 
dependencies; the other modules will grab it if they need it.

Usage
-----
...

