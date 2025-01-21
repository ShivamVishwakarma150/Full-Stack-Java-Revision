class Producer extends Thread {

    StringBuffer sb;

    boolean dataProvider = false;

    public Producer() {
        // String buffer object is create with a default capacity 16
        sb = new StringBuffer();
    }

    @Override
    public void run() {
        synchronized (sb) {
            for (int i = 1; i <= 10; i++) {
                try {
                    sb.append(i + ": ");
                    Thread.sleep(100);
                    System.out.println("appending");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //send the notification to waiting thread
            sb.notify();
        }

    }
}

class Consumer extends Thread {
    Producer producer;

    public Consumer(Producer producer) {
        this.producer = producer;
    }

    @Override
    public void run() {

        synchronized (producer.sb) {
            try {
                // wait till the notification send by producer
                producer.sb.wait();
                // consume the data produced by the producer
                System.out.println(producer.sb);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

// Efficient way of interthread coomunication using wait() and notify()
public class BetterCommunication {
    public static void main(String[] args) {
        Producer obj1 = new Producer();
        Consumer obj2 = new Consumer(obj1);

        Thread t1 = new Thread(obj1);// Producer thread
        Thread t2 = new Thread(obj2);// consumer thread

        t2.start(); // consumer should wait
        t1.start(); // producer should wait

    }
}