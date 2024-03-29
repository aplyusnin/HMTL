# HMTL

A project for Hindley-Milner Typed Lisp

## Type system

Unlike Clojure, HMTL provides type system and type inference during compiling/interpreting.

### Basic types

There are 5 basic types represented in HMTL:

1. Numeric 
2. Byte
3. Character
4. List
5. Function

### Functions

HTML allows passing functors as arguments and works with them as with variable of every other type.

#### Currying

HMTL does not provide any functional polymorphism, it always works with 1-arity functions. That allows to store and pass "partially" applied functions as arguments to other functions.


### HMTL to Java type

Only first 4 types are related to existing Java types.

Now there is no way to call java methods from HMTL without breaking type system. D:

## Syntax and Grammar

HTML grammar is the same as Clojure's.
HTML syntax is derived from Clojure's with changes that allow 
to type inference.

### Function application

Suppose `f` is a 2-arity function. Following source would apply variables `a` and `b` to it.

```
    (f a b)
```

In fact, due to carrying, source above is the same as source below.

```
    ((f a) b)
```

This part of source allows compiler to deduce following:

Type of `f` is 
```
    (A -> (B -> F))
```

where type `A` is common with variable `a` (it can also be a function),
type `B`is common with variable `b` and `F` is a resulting type after applying variable `a` and `b`.

### Function abstraction

Following construction allows to create an abstraction:

```
fn ?name ?:Type [a ?:Type b ?:Type] (BODY)
```

There are 5 parts in this declaration: 

1. Declaration macros
2. Maybe naming
3. Maybe type
4. Arguments
5. Function body

#### Maybe naming

Function may be 'binded' to some name to be recursively called inside its body.

For example:
```
    (fn fact [x] (if (= x 0) 1 (fact (- x 1))))
```

#### Maybe type

If function should return specific type, it can be explicitly assigned to the function. If function is may not return 
some specific basic type it can be used to show that type should be equal to something else.

Some examples:

This function should return Numeric as the result
```
    (fn :Numeric [a b] (+ a b))
```

This function maps function f to every element of list l
```
    (fn map :[Y] [l :[X] f :(X -> Y)] (concat (f (head l)) (map (tail l) f)))
```

#### Argument list

Arguments list is just the arguments list :P.

It is being split in multiple abstraction for currying.

For example:

```
    (fn [a b] (...))
```
has type: `(a -> (b-> f))`

### Binding to name

Every variable may be binded to the name to be used further.

```
    (def add (fn [a b] (+ a b)))
```

### Let-context

Multiple assignment may be applied inside block, rewriting constants for this block only:

```
    (let 
        [
            (name1 value1)
            (name2 value2)
            .
            .
            .
        ]
        (body)    
    ) 
```
Here values `value1`, `value2`, ... are binded to `name1`, `name2`, ... correspondingly.
