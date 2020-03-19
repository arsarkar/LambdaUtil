# LambdaUtil
LambdaUtil is swiss-knife utility tool to kickstart lambda style functional programming in Java. Lambda-styled programaming syntaxes are partly supported by java from version 8.0 onwards. However, the monads for object and collection supplied by the factory are not complete. The functional interfaces lack error handling. Finally, it becomes impossible to write clean chain-like code by using the standard java library for functional programming. LambdaUtil is an extremely lightweight and barebone collections of monads and functional interfaces. Still, it allows developers to write clean chain-like code for mainly transforming data by performing a sequence of oeprations in a clean chain-like code block. As the exceptions are handled by the monad automatically, no messy try-catch block is necessary. In short, this tool may write your entire java program without using curly braces.

# Availibility
* Maven: Add the following block in `.pom` file in your project.
```
<dependency>
  <groupId>edu.ohiou.mfgresearch</groupId>
  <artifactId>LambdaUtil</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>jar</packaging>
</dependency>
```
* Compile from source: Build the project by Maven using the following commands (Maven required)

# How to use

## Monads: 
LambdaUtil provides two monads, which are called `Uni` and `Omni`, to wrap around a single java object and collection of java object(s), respectively. Primitive type classes are also supported but not primitive types, i.e `Integer` is supported but not `int`. 

### Uni
#### Constructor
Any object can be wrapped in `Uni` by passing either the object or a *supplier* for that object the using the static method `of`. 
```
Uni.of("Hello, world!");
Uni.of(new String("Hello, world!"));
Uni.of(()->"Hello, world!");
Uni.of(()->new String("Hello, world!"));
```
#### Transformation
  * **map**:
    `map` applies an implementation of `Func` interface, which receives the object, already wrapped in the `Uni` object and returns an object of same or other types. This object, wrapped in `Uni`, is returned by `map`.    
    ```
    Uni.of("Hello, world!").map(t->5.0); 
    ```
  * **fMap**:
    `fMap` applies an implementation of `Func` interface, which receives the object, already wrapped in the `Uni` object and returns a collection, wrapped in Omni. This Omni-typed object is returned by `fMap`.        
    ```
    Uni.of("Hello, world!").fMap(s->Omni.of(s.split(",")));
    ```
  * **fMap2Stream**:
    `fMap2Stream` applies an implementation of `Func` interface, which receives the object, already wrapped in the `Uni` object and returns a collection of `Uni`, wrapped in `java.util.stream.Stream`. This stream of `Uni` is returned by `fMap2Stream`. This is a utility method, which breaks the chain-like code but helps in switching to standard `Stream` implementation from Java.        
    ```
    Uni.of("Hello, world!").fMap(s->Omni.of(s.split(",")));
    ```  
* Selection
  * **filter**: 
  `filter` applies an implementation of `Pred` interface and returns the same `Uni` object if the supplied predicate succeeds. It returns a `Failure` object if the predicates fails.
    ```
    Uni.of("Hello, world!")
       .filter(s->s.contains("Hello"))  //returns same Uni
       .filter(s->s.contains("Hola"));  //returns failure object 
    ```  
  * **select**: 
  `select` applies an implementation of `Pred` interface and then passes the object to an implementation of `Cons` if the supplied predicate succeeds. It returns the same `Uni` object irreespective of the given predicate succeeds but does not pass the object to the given consumer if the predicate fails.
    ```
		Uni.of(5)
			.select(i->i%2==0, i->System.out.println(i+" is even. "))
			.select(i->i%2!=0, i->System.out.println(i+" is odd. "));
    (prints "5 is odd.")   
    ```  
  * **selectMap**: 
  `selectMap` applies an implementation of `Pred` interface and then passes the object to an implementation of `Func` if the supplied predicate succeeds. It returns an object, which is returned by the function after wrapping in `Uni`. It returns the same `Uni` object and does not pass the object to the given function if the predicate fails.
    ```
		Uni.of(5)
			.select(i->i%2!=0, i->i+1);
    (returns Uni<6>)   
    ``` 
