# Checkstyle Documentation
### The documentation for our checkstyle

We are going to have 15 rules on our checkstyle that are going to sustain our project.

> **_IMPORTANT:_**  It is important to know that this if the rules are not respected the pipeline will fail.

-------------------

### 1.MissingJavadocMethod
Checks for missing Javadoc comments for a method or constructor. Javadoc is not required on a method that is tagged with the @Override annotation.

```java
public class Test {
  public Test() {} // violation, missing javadoc for constructor
  public void test() {} // violation, missing javadoc for method
  /**
   * Some description here.
   */
  public void test2() {} // OK

  @Override
  public String toString() { // OK
    return "Some string";
  }
}
```

### 2.JavadocMethod
Checks the Javadoc of a method or constructor. Our requirements are very permissive so because of that the JavaDoc of the method is not required to contain any information about the parameters or about the return value.

```java
public class Test {
    /**
     * Lambda expressions are ignored as we do not know when the exception will be thrown.
     *
     * @param s a String to be printed at some point in the future
     * @return a Runnable to be executed when the string is to be printed
     */
    public Runnable printLater(String s) {
        return () -> {
            if (s == null) {
                throw new NullPointerException(); // ok
            }
            System.out.println(s);
        };
    } 
    //OK
    
    /**
     * Lambda expressions are ignored as we do not know when the exception will be thrown.
     *
     */
    public Runnable printLater(String s) {
        return () -> {
            if (s == null) {
                throw new NullPointerException(); // ok
            }
            System.out.println(s);
        };
    }
    //OK
    
    public Runnable printLater(String s) {
        return () -> {
            if (s == null) {
                throw new NullPointerException(); // ok
            }
            System.out.println(s);
        };
    }
    //NOT OK
}
```

### 3.MethodLength
Checks for long methods and constructors. This would verify if the body of the method reaches the specified number of lines. 
Our checkstyle encourages the programmers to write a method in less ten 50 lines.

### 4.ParameterNumber
Checks the number of parameters of a method or constructor. In our case the number is set for 10 parameters.

### 5.OuterTypeFilename
Checks that the outer type name and the file name match.

```java

/**
 * Example of class Test in a file named Test.java
 **/

public class Test { // OK

}

/**
 * Example of class Foo in a file named Cards.java
 **/

class Foo { // violation

}

```


### 6.CyclomaticComplexity
Checks cyclomatic complexity against a specified limit. By pure theory level 1-4 is considered easy to test, 5-7 OK,
 8-10 consider re-factoring to ease testing, and 11+ re-factor now as testing will be painful.
In our case the limit is 6. 


### 7.Indentation
Checks correct indentation of Java code. Basic offset indentation is used for indentation inside code blocks. 
For any lines that span more than 1, line wrapping indentation is used for those lines after the first. Brace adjustment, case, and throws indentations are all used only if those specific identifiers start the line.

### 8.UnusedImports
Checks for unused import statements.

```java
import java.awt.Component;
import static AstTreeStringPrinter.printFileAst;
import static DetailNodeTreeStringPrinter.printFileAst;
class FooBar {
  private Object Component; // a bad practice in my opinion
  void method() {
      printFileAst(file); // two imports with the same name
  }
}
```

### 9.MemberName
Checks that instance variable names conform a specified pattern

### 10.MethodName
Checks that method names conform to a specified pattern. Also, checks if a method name has the same name as the residing class.

### 11.ParameterName
Checks that method parameter names conform to a specified pattern.

### 12.LocalVariableName
Checks that local, non-final variable names conform to a specified pattern.

### 13.StaticVariableName
Checks that static, non-final variable names conform to a specified pattern.

### 14.ClassTypeParameterName
Checks that class type parameter names conform to a specified pattern.

### 15.LineLength
Checks for long lines. In our case we check for a maximum of 121 characters per line.
If there are more than that this would create a warning.
