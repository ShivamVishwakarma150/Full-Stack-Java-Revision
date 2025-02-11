
### **Wrapper Object Creation**
- **Recommended:** Use `valueOf()` instead of constructors to create wrapper objects.

---

### **Var-Arg Methods**
- Introduced in **JDK 1.5**, allowing a method to accept **variable-length arguments** of the same type.
- **Syntax:**  
  ```java
  void methodName(dataType... variableName)
  ```
- The **ellipsis (`...`)** represents a var-arg parameter.

**Example:**
```java
class Demo {
    public void add(int... x) {
        System.out.println("Var-arg method called");
    }
}

class Test {
    public static void main(String[] args) {
        Demo d = new Demo();
        d.add(); // Valid
        d.add(10);
        d.add(10, 20, 30);
    }
}
```

#### **Rules for Var-Arg Methods**
1. **Valid Signatures:**
   ```java
   public void methodOne(int... x)
   public void methodOne(String s, int... x)
   ```
2. **Mixing Normal and Var-Arg Parameters**
   - **Var-arg must always be the last parameter.**
   - ❌ `public void methodOne(int... x, int y);` (Invalid)
3. **Only One Var-Arg Allowed**
   - ❌ `public void methodOne(int... x, int... y);` (Invalid)
4. **Var-Arg vs. Overloading**
   - If a **specific match is found**, it is called first.
   - Otherwise, the **var-arg method is called**.

**Example:**
```java
class Test {
    public void methodOne(int... i) {
        System.out.println("Var-arg method");
    }
    public void methodOne(int i) {
        System.out.println("Int arg method");
    }

    public static void main(String[] args) {
        Test t = new Test();
        t.methodOne(10);  // Calls "Int arg method"
        t.methodOne();    // Calls "Var-arg method"
        t.methodOne(10, 20, 30); // Calls "Var-arg method"
    }
}
```

#### **Var-Arg vs. Arrays**
- ✅ `void methodOne(int... x) ⟶ int[] x`  
- ❌ `void methodOne(int[] x) ⟶ int... x` (Invalid)

**Example:**
```java
class Test {
    public void methodOne(int... x) {
        for (int data : x) {
            System.out.println(data);
        }
    }

    public static void main(String... args) {
        Test t = new Test();
        t.methodOne(10, 20, 30);
    }
}
```
Here, `x` is treated as a **1D array**.

---

### **Type Conversion Rules in Java**
**Priority Order:**  
1. **Widening (Primitive Promotion)**
2. **Autoboxing (Primitive to Wrapper Class)**
3. **Var-Arg Method (Least Preferred)**

#### **Case 1: Widening vs. Autoboxing**
```java
class Test {
    public static void methodOne(long l) {
        System.out.println("Widening");
    }
    public static void methodOne(Integer i) {
        System.out.println("Autoboxing");
    }

    public static void main(String[] args) {
        int x = 10;
        methodOne(x); // Widening wins (int → long)
    }
}
```
**Output:** `Widening`

#### **Case 2: Widening vs. Var-Arg**
```java
class Test {
    public static void methodOne(long l) {
        System.out.println("Widening");
    }
    public static void methodOne(int... i) {
        System.out.println("Var-arg method");
    }

    public static void main(String[] args) {
        int x = 10;
        methodOne(x); // Widening wins (int → long)
    }
}
```
**Output:** `Widening`

#### **Case 3: Autoboxing vs. Var-Arg**
```java
class Test {
    public static void methodOne(Integer i) {
        System.out.println("Autoboxing");
    }
    public static void methodOne(int... i) {
        System.out.println("Var-arg method");
    }

    public static void main(String[] args) {
        int x = 10;
        methodOne(x); // Autoboxing wins (int → Integer)
    }
}
```
**Output:** `Autoboxing`

#### **Case 4: Widening Followed by Autoboxing (Not Allowed)**
```java
class Test {
    public static void methodOne(Long l) {
        System.out.println("Long");
    }

    public static void main(String[] args) {
        int x = 10;
        methodOne(x); // Compilation Error
    }
}
```
❌ **Widening (int → long) then Autoboxing (long → Long) is NOT allowed.**  
✅ **Autoboxing followed by Widening is allowed.**

#### **Case 5: Autoboxing + Widening**
```java
class Test {
    public static void methodOne(Object o) {
        System.out.println("Object");
    }

    public static void main(String[] args) {
        int x = 10;
        methodOne(x); // Autoboxing (int → Integer) → Widening (Integer → Object)
    }
}
```
**Output:** `Object`

---

### **Valid and Invalid Declarations**
| Declaration         | Valid/Invalid | Reason |
|--------------------|--------------|--------|
| `int i = 10;` | ✅ | Normal primitive assignment |
| `Integer I = 10;` | ✅ | Autoboxing (`valueOf()`) |
| `int i = 10L;` | ❌ | Long to int conversion not allowed |
| `Long l = 10L;` | ✅ | Autoboxing |
| `Long l = 10;` | ❌ | Autoboxing tries Integer first, fails |
| `long l = 10;` | ✅ | Widening (`int → long`) |
| `Object o = 10;` | ✅ | Autoboxing (`int → Integer → Object`) |
| `double d = 10;` | ✅ | Widening (`int → double`) |
| `Double d = 10;` | ❌ | Same issue as Long |

---

### **Difference: `new` vs. `newInstance()`**
| Feature | `new` Operator | `newInstance()` Method |
|---------|---------------|----------------|
| Type | Operator | Method (from `Class` class) |
| Usage | Known class names | Unknown class names at runtime |
| Syntax | `Test t = new Test();` | `Object o = Class.forName("Test").newInstance();` |
| Exception | `NoClassDefFoundError` if class is missing | `ClassNotFoundException` if class is missing |
| Constructor Requirement | No restriction | Requires a **no-arg constructor** |

#### **Example: Using `newInstance()`**
```java
class Test {
    public Test() {
        System.out.println("Test constructor called");
    }

    public static void main(String[] args) throws Exception {
        Object obj = Class.forName("Test").newInstance();
        System.out.println(obj.getClass().getName());
    }
}
```
- If the class is not found, **`ClassNotFoundException`** occurs.
- If the constructor is missing, **`InstantiationException`** occurs.

---

### **Difference: `ClassNotFoundException` vs. `NoClassDefFoundError`**
| Feature | `ClassNotFoundException` | `NoClassDefFoundError` |
|---------|--------------------------|------------------------|
| Type | Checked Exception | Runtime Error |
| When it occurs | When a dynamically loaded class is missing | When a class used in the code is missing |
| Example | `Class.forName("Test")` when `Test.class` is not found | `new Test();` when `Test.class` is missing |

---

### **Object Creation Process (`new` Operator)**
1. **JVM searches for the class file** in the current directory.
2. **If found, it loads the `.class` file** into the **Method Area**.
3. **Static variables are initialized** with default values.
4. **Heap memory allocation** for object instance variables.
5. **Instance blocks execute (if any).**
6. **Constructor is called** to set meaningful values.
7. **JVM assigns the object a unique hash code.**

---


<br/>
<br/>

# **Points to Remember**  

#### **1. valueOf() vs Constructor for Wrapper Objects**  
- It is recommended to use `valueOf()` instead of constructors for creating wrapper objects in Java.  

#### **2. Var-args (Variable Argument Methods)**  
- Introduced in **JDK 1.5** to handle methods with a variable number of arguments.  
- Syntax: `methodOne(dataType ... variableName)` (`...` stands for an ellipsis).  
- All arguments should be of the **same type**.  

##### **Rules for Var-arg Methods**  
1. **Valid Signatures:**  
   - `public void methodOne(int ... x)`  
   - `public void methodOne(String ... x)`  
2. **Mixing Normal and Var-arg Arguments:**  
   - Allowed: `public void methodOne(int x, int ... y)`  
   - Allowed: `public void methodOne(String s, int ... x)`  
   - Not Allowed: `public void methodOne(int ... x, int y)` (Var-arg must be the last parameter)  
3. **Only One Var-arg Per Method:**  
   - `public void methodOne(int ... x, int ... y);` ❌ (Invalid)  
4. **Overloading Var-arg Methods:**  
   - If an exact match is not found, the var-arg method is used as a fallback.  
5. **Var-arg and Arrays:**  
   - `public void methodOne(int ... x)` is equivalent to `public void methodOne(int[] x)`.  
   - A method with `int[] ... x` is treated as a **2D array**.  

#### **3. Widening vs Autoboxing vs Var-arg**  
- **Priority Order:** Widening > Autoboxing > Var-arg  
- **Examples:**  
  - `void methodOne(long l)` → Called if an `int` is passed (**widening**).  
  - `void methodOne(Integer i)` → Called if an `int` is passed (**autoboxing**).  
  - `void methodOne(int ... i)` → Called only if no other match is found (**var-arg**).  

##### **Important Rules:**  
1. **Widening followed by Autoboxing is NOT allowed.**  
2. **Autoboxing followed by Widening is allowed.**  
3. **If both Autoboxing and Var-arg are available, Autoboxing is preferred.**  

#### **4. new vs newInstance()**  
- **`new` (Operator):**  
  - Used when the class name is **known at compile-time**.  
  - Example: `Test t = new Test();`  
  - If the class is missing at runtime, it throws **NoClassDefFoundError** (unchecked).  
  - **No need for a no-argument constructor.**  

- **`newInstance()` (Method of Class Class):**  
  - Used when the class name is **not known at compile-time** (loaded dynamically).  
  - Example: `Object o = Class.forName("Test").newInstance();`  
  - If the class is missing at runtime, it throws **ClassNotFoundException** (checked).  
  - **The class must have a no-argument constructor.**  

#### **5. ClassNotFoundException vs NoClassDefFoundError**  
- **NoClassDefFoundError:**  
  - Occurs when a **hardcoded class** is missing at runtime.  
  - Example: `Test t = new Test();` (Test.class is missing).  
  - **Unchecked Exception**.  

- **ClassNotFoundException:**  
  - Occurs when a **dynamically loaded class** is missing.  
  - Example: `Object o = Class.forName("Test").newInstance();`  
  - **Checked Exception**.  

#### **6. Memory Allocation by `new` Operator**  
- **Heap Area:** Memory is allocated for the object.  
- **Method Area:** Static variables and blocks are executed.  
- **Instance Blocks and Constructor Execution:**  
  - Instance variables are initialized with default values.  
  - **Instance block (if any) is executed before the constructor.**  
  - Constructor assigns meaningful values to instance variables.  
- **HashCode:** JVM assigns an address, which is converted into a **hashCode**.