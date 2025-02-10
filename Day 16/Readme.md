# **Points to Remember - Wrapper Class Utility Methods**  

#### **1. `valueOf()` Method**  
- Used to create a wrapper object from a **primitive type** or **String**.  
- Preferred over constructors (deprecated in newer Java versions).  
- All wrapper classes (except `Character`) contain `valueOf()`.  

##### **Examples:**  
```java
Integer i = Integer.valueOf("10");   // String to Wrapper  
Double d = Double.valueOf("10.5");   // String to Wrapper  
Boolean b = Boolean.valueOf("nitin");  // "nitin" is not "true" -> false  
```

- **Radix-based Conversion:**  
```java
Integer i2 = Integer.valueOf("1111", 2); // Binary to Integer -> 15  
```
- If an invalid radix is used (`Integer.valueOf("1111", 37);`), it throws **NumberFormatException**.  

---

#### **2. `xxxValue()` Method**  
- Used to convert a **wrapper object to a primitive type**.  
- Available in **all numeric wrapper classes** (`Byte`, `Short`, `Integer`, `Long`, `Float`, `Double`).  
- **Methods available:**  
  - `byteValue()`, `shortValue()`, `intValue()`, `longValue()`, `floatValue()`, `doubleValue()`.  
- **Example:**  
```java
Integer i = new Integer(130);
System.out.println(i.byteValue()); // -126 (overflow)
System.out.println(i.intValue());  // 130  
System.out.println(i.doubleValue()); // 130.0  
```
- **For `Character` and `Boolean`:**  
  - `charValue()` in `Character`  
  - `booleanValue()` in `Boolean`  

---

#### **3. `parseXXX()` Method**  
- Converts a **String to primitive type**.  
- Available in all wrapper classes **except `Character`**.  
- **Syntax:**  
  ```java
  public static primitive parseXXX(String s);
  ```
- **Example:**  
  ```java
  int i = Integer.parseInt("10");  
  double d = Double.parseDouble("10.5");  
  boolean b = Boolean.parseBoolean("true");  
  ```
- **Radix-based Parsing:**  
  ```java
  int i = Integer.parseInt("1111", 2); // Binary -> 15  
  ```

---

#### **4. `toString()` Method**  
- Converts **wrapper object or primitive** to **String**.  
- Available in **all wrapper classes**.  
- **Example:**  
  ```java
  Integer i = Integer.valueOf(10);
  System.out.println(i.toString()); // "10"  
  ```
- **Static `toString()` Method (Primitive to String)**  
  ```java
  String s = Integer.toString(10); // "10"  
  String s = Boolean.toString(true); // "true"  
  String s = Character.toString('a'); // "a"  
  ```

---

### **5. AutoBoxing & AutoUnBoxing**  
- **Before Java 1.5**, manual conversion between primitive and wrapper types was needed.  
- **From Java 1.5 onwards**, Java automatically converts **primitive to wrapper (AutoBoxing)** and **wrapper to primitive (AutoUnBoxing)**.  

##### **Example of AutoBoxing:**  
```java
Integer i = 10;  // AutoBoxing (Integer.valueOf(10))
```
##### **Example of AutoUnBoxing:**  
```java
Integer i = new Integer(10);
int j = i;  // AutoUnBoxing (i.intValue())
```

---

### **6. Object Reference Comparisons (`==`) in AutoBoxing**
#### **Case 1:**  
```java
Integer x = new Integer(10);
Integer y = new Integer(10);
System.out.println(x == y); // false (Different objects)
```

#### **Case 2:**  
```java
Integer x = new Integer(10);
Integer y = 10; // AutoBoxing
System.out.println(x == y); // false
```

#### **Case 3:**  
```java
Integer x = 10;
Integer y = 10;
System.out.println(x == y); // true (Both refer to the same cached object)
```

#### **Case 4:** (Buffer Concept)  
- **JVM maintains a cache for `Integer`, `Byte`, `Short`, `Long`, `Character`, and `Boolean` objects within specific ranges.**  
- **Buffer Cache Range:**
  - `Byte` → `-128` to `127`
  - `Short` → `-128` to `127`
  - `Integer` → `-128` to `127`
  - `Long` → `-128` to `127`
  - `Character` → `0` to `127`
  - `Boolean` → `true`, `false`
  
```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b); // true (cached objects)

Integer x = 128;
Integer y = 128;
System.out.println(x == y); // false (new objects)
```

#### **Case 5:** (For `Boolean`)  
```java
Boolean b1 = true;
Boolean b2 = true;
System.out.println(b1 == b2); // true (cached)
```

#### **Case 6:** (For `Double`, `Float`)  
```java
Double d1 = 10.0;
Double d2 = 10.0;
System.out.println(d1 == d2); // false (new objects, no caching for Double)
```

---

### **Summary of Key Concepts**  
| **Feature**       | **Converts**               | **Applicable Classes** | **Example** |
|------------------|-------------------------|--------------------|-----------|
| `valueOf()`       | Primitive/String → Wrapper | All except `Character` | `Integer.valueOf(10)` |
| `xxxValue()`      | Wrapper → Primitive       | Number-type Wrappers  | `i.intValue()` |
| `parseXXX()`      | String → Primitive        | All except `Character` | `Integer.parseInt("10")` |
| `toString()`      | Wrapper/Primitive → String | All                 | `Integer.toString(10)` |
| **AutoBoxing**    | Primitive → Wrapper       | All                 | `Integer i = 10;` |
| **AutoUnBoxing**  | Wrapper → Primitive       | All                 | `int j = i;` |

---

These key points will help you remember Wrapper Class utility methods effectively. 🚀