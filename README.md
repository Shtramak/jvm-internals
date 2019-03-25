```java
public class TextService {

   public String staticText() {
       return "Some static text";
   }

   public String variable(String variable) {
       return variable;
   }

   public String exception(String text) throws RuntimeException {
       //TODO throw your custom exception
       return text;
  }
} 
```

#### Custom classloader

Your task is to create a custom classloader for TextService.
To test how it works, create an infinite loop that prints TextService#staticText() method result._<br>
If TextService#staticText() text updated and class TextService was recompiled an updated text version should start printing without restart the app.


```java
while (true) {
   println(TextService#staticText());
}
```

#### Proxy object

Your task is to create a Dynamic Proxy object - EnvVariableProxyReplacer for TextService class that replaces templates ${variable} in a string on the variable value.

For example, if we run java and setting system property value<br>
```
java -Dport="8080" SomeClass
and invoke proxed method
TextService#variable() 
with text 
“server.port = ${port}”
it should return
“server.port = 8080”

assertEquals(“server.port = 8080”, TextService#variable(“server.port = ${port}”)) 
```



#### Micro-benchmarking

Perform micro-benchmarking with JMH util for: 
method throwing and then processing exception with/without stack trace
Stream vs for loop
Stream with/without parallel
