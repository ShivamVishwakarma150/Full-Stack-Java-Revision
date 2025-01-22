### MultiThreading in Java: Explained in Detail

---

### **1. Different Ways of Creating Threads**
Java provides two primary ways to create threads:

#### a. **Extending the `Thread` Class**
- **Steps**:
  1. Create a class that extends the `Thread` class.
  2. Override the `run()` method to define the task that the thread will execute.
  3. Create an instance of the class and call the `start()` method.
- **Example**:
    ```java
    class MyThread extends Thread {
        public void run() {
            System.out.println("Thread is running...");
        }
    }
    public class Main {
        public static void main(String[] args) {
            MyThread thread = new MyThread();
            thread.start();
        }
    }
    ```

#### b. **Implementing the `Runnable` Interface**
- **Steps**:
  1. Create a class that implements the `Runnable` interface.
  2. Override the `run()` method to define the thread's task.
  3. Pass an instance of this class to a `Thread` object and call `start()`.
- **Example**:
    ```java
    class MyRunnable implements Runnable {
        public void run() {
            System.out.println("Thread is running...");
        }
    }
    public class Main {
        public static void main(String[] args) {
            Thread thread = new Thread(new MyRunnable());
            thread.start();
        }
    }
    ```

---

### **2. Setting and Getting Thread Names**

- **Setting Thread Name**:
  ```java
  thread.setName("MyThread");
  ```

- **Getting Thread Name**:
  ```java
  String name = thread.getName();
  ```

- **Example**:
    ```java
    class MyThread extends Thread {
        public void run() {
            System.out.println("Thread Name: " + getName());
        }
    }
    public class Main {
        public static void main(String[] args) {
            MyThread thread = new MyThread();
            thread.setName("WorkerThread");
            thread.start();
        }
    }
    ```

---

### **3. Lifecycle of a Thread**

The lifecycle of a thread includes the following states:

1. **New/Born**: A thread is in the new state when it is created but not yet started.
    ```java
    Thread t = new Thread();
    ```

2. **Runnable/Ready**: The thread transitions to the runnable state after calling `start()`. It is ready to run when the CPU assigns time.
    ```java
    t.start();
    ```

3. **Running**: The thread is actively executing its task (`run()` method).

4. **Terminated/Dead**: The thread completes its task and terminates.

---

### **4. Methods to Prevent a Thread from Execution**

#### a. **`join()`**:
- Makes one thread wait until another thread finishes execution.
- **Example**:
    ```java
    Thread t1 = new Thread(() -> {
        System.out.println("Thread 1 running...");
    });
    t1.start();
    t1.join(); // Main thread waits until t1 finishes
    System.out.println("Main thread resumes after t1.");
    ```

#### b. **`sleep()`**:
- Pauses the thread for a specified time without releasing the lock.
- **Example**:
    ```java
    Thread.sleep(1000); // Thread sleeps for 1 second
    ```

---

### **5. Synchronization**

Synchronization ensures that only one thread can execute a synchronized method or block at a time. It resolves issues like **data inconsistency** and **race conditions**.

#### Levels of Locking
1. **Class-Level Lock**:
    - Applicable to `static synchronized` methods or blocks.
    - Lock is tied to the `Class` object.
    ```java
    synchronized (MyClass.class) {
        // Critical section
    }
    ```

2. **Object-Level Lock**:
    - Applicable to `synchronized` methods or blocks.
    - Lock is tied to the instance of the object.
    ```java
    synchronized (this) {
        // Critical section
    }
    ```

- **Disadvantage**: Increases the waiting time for other threads.

---

### **6. InterThread Communication**

Java provides methods like `wait()`, `notify()`, and `notifyAll()` to facilitate communication between threads.

#### Example: Producer-Consumer Problem
```java
class SharedResource {
    private boolean dataAvailable = false;

    public synchronized void produce() throws InterruptedException {
        while (dataAvailable) {
            wait();
        }
        System.out.println("Producing data...");
        dataAvailable = true;
        notify();
    }

    public synchronized void consume() throws InterruptedException {
        while (!dataAvailable) {
            wait();
        }
        System.out.println("Consuming data...");
        dataAvailable = false;
        notify();
    }
}

public class Main {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread producer = new Thread(() -> {
            try {
                while (true) {
                    resource.produce();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                while (true) {
                    resource.consume();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
```

---

### **Key Points**
1. **`wait()`**: Causes the thread to wait until another thread calls `notify()` or `notifyAll()` on the same object.
2. **`notify()`**: Wakes up a single waiting thread.
3. **`notifyAll()`**: Wakes up all waiting threads.

Unlike `sleep()` or `join()`, `wait()` releases the lock it holds, allowing other threads to execute synchronized blocks/methods.


<br/>
<br/>

### **Detailed Explanation of Notes**

---

### **Inefficient Thread Communication (First Code)**

#### **Concept**
- **Producer-Consumer Problem**:
  - The `Producer` thread generates data and sets a flag (`dataProvider = true`) to indicate the data is ready.
  - The `Consumer` thread constantly checks this flag in a `while` loop until it turns `true`.

#### **Why Inefficient?**
1. **Polling**:
   - The `Consumer` thread repeatedly checks the `dataProvider` flag.
   - This is known as **busy-waiting**, which wastes CPU resources as the thread does not perform useful work during this time.

2. **Thread Sleeping**:
   - The consumer thread uses `Thread.sleep(10)` to reduce the frequency of flag checks.
   - Even with sleep, the CPU remains idle, and there is no guarantee of precise coordination between the threads.

3. **Lack of Synchronization**:
   - No mechanism ensures atomicity or safe access to shared resources like `dataProvider` and `StringBuffer`.

---

### **Efficient Thread Communication Using `wait()` and `notify()` (Second Code)**

#### **Concept**
- **Thread Synchronization with `wait()` and `notify()`**:
  - A thread calling `wait()` releases the lock on the object and enters a waiting state.
  - Another thread, after completing its task, calls `notify()` to wake up the waiting thread.

#### **Flow of Communication**

1. **`Main Thread` (Consumer)**:
   - Needs a calculated value (`total = 5050`) from the `Child Thread` (Producer).
   - Calls `wait()` to pause and release the lock on the `Demo` object.
   - Waits for the `Child Thread` to complete its task and notify it.

2. **`Child Thread` (Producer)**:
   - Calculates the sum of the first 100 numbers.
   - Once done, it calls `notify()` to wake up the `Main Thread`.
   - Must be inside a `synchronized` block to ensure proper communication.

---

### **Key Differences Between Inefficient and Efficient Communication**

| **Aspect**                | **Inefficient Communication**                                                                                     | **Efficient Communication**                                                                      |
|---------------------------|-------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------|
| **CPU Usage**             | Wastes CPU cycles in busy-waiting.                                                                                | Saves CPU time as the thread sleeps in a waiting state.                                         |
| **Synchronization**       | No synchronization; multiple threads can access shared data concurrently.                                         | Synchronization ensures safe access to shared data.                                             |
| **Thread State**          | Consumer thread is active and repeatedly checks the condition.                                                    | Consumer thread is in a blocked state until notified.                                           |
| **Lock Handling**         | No explicit lock management, leading to possible race conditions.                                                 | Proper lock handling with `wait()` and `notify()`.                                              |
| **Scalability**           | Difficult to manage more threads due to busy-waiting.                                                             | Scales better as threads are only active when required.                                         |

---

### **Details About `wait()`, `notify()`, and `notifyAll()`**

#### **1. `wait()`**
- **Purpose**: Puts the thread into a waiting state and releases the lock on the synchronized object.
- **Usage**:
  - The thread that needs updated data or some signal calls `wait()`.
  - Must be inside a synchronized block or method.
- **Impact**:
  - Reduces CPU usage as the thread enters a blocked state.
  - Automatically releases the lock, allowing other threads to proceed.

#### **2. `notify()`**
- **Purpose**: Wakes up **one** thread waiting on the same object’s lock.
- **Usage**:
  - The thread that has completed its task or has updated the shared data calls `notify()`.
  - Must be inside a synchronized block or method.
- **Impact**:
  - Signals one waiting thread to resume execution.
  - Useful for one-to-one communication.

#### **3. `notifyAll()`**
- **Purpose**: Wakes up **all threads** waiting on the same object’s lock.
- **Usage**:
  - Called when multiple threads may be waiting and all need to be notified.
  - Must be inside a synchronized block or method.
- **Impact**:
  - Allows multiple threads to compete for the lock.
  - Useful for one-to-many communication scenarios.

---

### **Why These Methods Are Part of the `Object` Class**
- **Scope**:
  - These methods operate on any object, not just threads.
- **Inheritance**:
  - All objects in Java inherit from the `Object` class.
  - Including these methods in `Object` allows any object to be used for thread synchronization.
- **Thread-Specific Methods**:
  - Methods like `join()`, `sleep()`, and `yield()` are specific to threads and belong to the `Thread` class.

---

### **Differences Between `notify()` and `notifyAll()`**

| **Feature**      | **notify()**                                   | **notifyAll()**                                   |
|------------------|-----------------------------------------------|-------------------------------------------------|
| **Notification** | Wakes up one waiting thread.                  | Wakes up all waiting threads.                   |
| **Use Case**     | Suitable for one-to-one thread communication. | Suitable for one-to-many thread communication.  |
| **Lock Handling**| Only one thread competes for the lock.        | All threads compete for the lock.               |

---

### **Efficient Communication Code Recap**

```java
class SharedResource {
    private int total = 0;
    private boolean isCalculated = false;

    public synchronized void produce() {
        System.out.println("Producer thread starts the calculation.");
        for (int i = 1; i <= 100; i++) {
            total += i;
        }
        isCalculated = true;
        System.out.println("Producer thread notifies consumer.");
        notify(); // Notify the waiting consumer thread
    }

    public synchronized void consume() {
        while (!isCalculated) { // Ensure the consumer waits until data is ready
            try {
                System.out.println("Consumer thread is waiting...");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Consumer thread consumed data: Total = " + total);
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();

        Thread producer = new Thread(resource::produce);
        Thread consumer = new Thread(resource::consume);

        consumer.start(); // Consumer starts first and waits
        producer.start(); // Producer produces data and notifies
    }
}
```

---

### **Output Explanation**

1. The `Consumer` thread starts first and waits on the `SharedResource` object.
2. The `Producer` thread starts, calculates the total, and calls `notify()`.
3. The `Consumer` thread resumes and reads the calculated value.

**Output**:
```
Consumer thread is waiting...
Producer thread starts the calculation.
Producer thread notifies consumer.
Consumer thread consumed data: Total = 5050
```

<br/>
<br/>

### **Explanation of Multithreading with Different Approaches**

Multithreading in Java enables the concurrent execution of two or more parts of a program for maximum CPU utilization. In your examples, various approaches to creating threads are demonstrated:

---

### **1. Using Anonymous Inner Class**
Anonymous inner classes are used to define a one-time implementation of an interface or a class. This approach is beneficial when the thread logic is simple and used only once.

#### **Code Explanation**
```java
new Thread(new Runnable() {
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            try {
                System.out.println("Child thread");
                Thread.sleep(1000); // Pause for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}).start();
```

#### **How It Works**
1. A new `Thread` is created, and the `Runnable` interface is implemented using an **anonymous inner class**.
2. The `run()` method is overridden to define the thread's behavior.
3. The thread is started using the `start()` method.

#### **Use Case**
- Useful for a **one-time thread logic** where creating a separate class would be unnecessary overhead.
- Example: Loading data from a file asynchronously.

---

### **2. Using Lambda Expression (Java 8 Feature)**
Lambda expressions simplify the syntax for functional interfaces like `Runnable`. They provide a concise way to define thread behavior without creating anonymous classes.

#### **Code Explanation**
```java
Thread t = new Thread(() -> {
    for (int i = 1; i <= 5; i++) {
        try {
            System.out.println("Child thread");
            Thread.sleep(1000); // Pause for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
});
t.start();
```

#### **How It Works**
1. A new `Thread` is created with a **lambda expression** implementing the `Runnable` interface.
2. The lambda expression directly provides the logic for the `run()` method.
3. The thread is started with the `start()` method.

#### **Use Case**
- Ideal for **short, one-time thread logic** with minimal overhead.
- Example: Running lightweight background tasks such as logging or periodic updates.


By choosing the appropriate thread creation method based on your use case, you can write efficient and maintainable multithreaded programs.