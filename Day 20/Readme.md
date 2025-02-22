# **Java Map Interface and Implementations**

## **1. Introduction to Map Interface**
- `Map` is **not** a child interface of `Collection`.
- It is used to store key-value pairs.
- **Both keys and values are objects**.
- **Duplicate keys are not allowed**, but **duplicate values are allowed**.
- Each key-value pair is called an **entry**.

## **2. Methods in Map Interface**
The `Map` interface contains **12 commonly used methods**:

### **Basic Operations**
1. `Object put(Object key, Object value)` → Adds a key-value pair.
2. `void putAll(Map m)` → Adds another map's entries to the current map.
3. `Object get(Object key)` → Retrieves the value associated with the key.
4. `Object remove(Object key)` → Removes an entry based on the key.
5. `boolean containsKey(Object key)` → Checks if a key exists.
6. `boolean containsValue(Object value)` → Checks if a value exists.
7. `boolean isEmpty()` → Checks if the map is empty.
8. `int size()` → Returns the size of the map.
9. `void clear()` → Removes all entries from the map.

### **Views of a Map**
10. `Set keySet()` → Returns a `Set` containing all keys.
11. `Collection values()` → Returns a `Collection` containing all values.
12. `Set entrySet()` → Returns a `Set` containing all key-value entries.

---

## **3. Entry Interface (Nested inside Map Interface)**
- Each key-value pair in a `Map` is represented as an **Entry**.
- The `Entry` interface is **defined inside the Map interface**.
- Without a `Map`, an `Entry` object **cannot exist**.

### **Methods in Entry Interface**
```java
interface Map {
    interface Entry {
        Object getKey(); // Retrieves the key
        Object getValue(); // Retrieves the value
        Object setValue(Object newValue); // Updates the value
    }
}
```

---

## **4. HashMap**
- **Underlying Data Structure:** Hashtable.
- **Insertion Order:** Not preserved.
- **Duplicate Keys:** Not allowed.
- **Duplicate Values:** Allowed.
- **Heterogeneous Objects:** Allowed.
- **Null Insertion:**
  - `null` key → Allowed only once.
  - `null` values → Allowed multiple times.
- **Implements:** `Serializable`, `Cloneable`.

### **Constructors**
1. `HashMap hm = new HashMap();` // Default capacity: `16`, Load factor: `0.75`
2. `HashMap hm = new HashMap(int capacity);`
3. `HashMap hm = new HashMap(int capacity, float loadFactor);`
4. `HashMap hm = new HashMap(Map m);`

---

## **5. Difference Between HashMap and Hashtable**
| Feature         | HashMap | Hashtable |
|---------------|---------|-----------|
| Synchronization | **Not synchronized** | **Synchronized** |
| Thread-Safety | **Not thread-safe** | **Thread-safe** |
| Performance | **Faster** | **Slower** |
| Null Keys/Values | **Allowed** | **Not allowed** (Throws `NullPointerException`) |
| Introduced in | **Java 1.2** | **Java 1.0** |

---

## **6. LinkedHashMap**
- **Child class of HashMap**.
- **Preserves insertion order**.
- **Implements:** `Serializable`, `Cloneable`.

### **Differences from HashMap**
- `HashMap` → Underlying data structure is **Hashtable**.
- `LinkedHashMap` → Underlying data structure is **LinkedList + Hashtable**.
- `HashMap` → **No insertion order**.
- `LinkedHashMap` → **Maintains insertion order**.
- Introduced in **Java 1.4**.

> Used in **cache-based applications** where duplicate keys are not allowed, but order matters.

---

## **7. IdentityHashMap**
- **Uses `==` (reference comparison)** instead of `equals()` (content comparison) to compare keys.
- Example:

```java
import java.util.IdentityHashMap;

public class Test {
    public static void main(String[] args) {
        IdentityHashMap<Integer, String> map = new IdentityHashMap<>();
        Integer i1 = new Integer(10);
        Integer i2 = new Integer(10);
        map.put(i1, "Afridi");
        map.put(i2, "Sachin");
        System.out.println(map); // Both entries will be stored separately.
    }
}
```

---

## **8. WeakHashMap**
- **Allows garbage collection of keys**.
- `HashMap` **prevents** garbage collection of objects stored as keys.
- `WeakHashMap` **allows** garbage collection of keys once they become unreachable.
- Example:

```java
import java.util.*;

class Test {
    public static void main(String[] args) throws Exception {
        WeakHashMap<Employee, String> map = new WeakHashMap<>();
        Employee e = new Employee();
        map.put(e, "EmployeeData");
        e = null;
        System.gc(); // Will remove the entry from WeakHashMap
    }
}

class Employee {
    @Override
    public void finalize() {
        System.out.println("Cleaning the object");
    }
}
```

---

## **9. Hashtable**
- **Underlying Data Structure:** Hashtable.
- **Duplicate Keys:** Not allowed.
- **Duplicate Values:** Allowed.
- **Insertion Order:** Not preserved (based on key hashcode).
- **Thread-Safe:** Yes (Synchronized methods).
- **Null insertion:**
  - `null` key → **Not allowed**.
  - `null` value → **Not allowed**.
- **Implements:** `Serializable`, `Cloneable` (not `RandomAccess`).

### **Constructors**
1. `Hashtable h = new Hashtable();` // Default capacity: `11`, Load factor: `0.75`
2. `Hashtable h = new Hashtable(int initialCapacity);`
3. `Hashtable h = new Hashtable(int initialCapacity, float loadFactor);`
4. `Hashtable h = new Hashtable(Map m);`

---

## **10. Properties Class**
- **Child class of Hashtable**.
- Used for **configuration settings** (e.g., database credentials, application settings).
- **Avoids hardcoding sensitive information** in Java programs.
- Changes in the properties file **do not require recompilation**.

### **Methods in Properties Class**
1. `String getProperty(String key)` → Gets value by key.
2. `void setProperty(String key, String value)` → Sets a new property.
3. `Enumeration<?> propertyNames()` → Gets all property names.
4. `void load(InputStream is)` → Loads properties from a file.
5. `void store(OutputStream os, String comment)` → Stores properties into a file.

---

## **Conclusion**
The `Map` interface and its implementations (`HashMap`, `LinkedHashMap`, `IdentityHashMap`, `WeakHashMap`, `Hashtable`, `Properties`) offer various functionalities for key-value-based storage in Java. The choice of implementation depends on factors like **synchronization**, **performance**, **insertion order**, and **garbage collection behavior**.

