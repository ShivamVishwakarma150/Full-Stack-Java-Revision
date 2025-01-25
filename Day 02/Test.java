/*

    class Thread
    {

        // Heart of Multitheading
        public void start(){
            1. Register the thread with ThreadScheduler
            2. All other mandatory low level activities
            3. invoke or call run() method
        }
        public void run(){
            //no implementation
        }

    }
 */


class MyThread extends Thread{
    @Override
    public void run() {
        for(int i=0;i<=10;i++){
            System.out.println("Child Thread");
        }
    }
}


public class Test{
    public static void main(String[] args) {
        MyThread t = new MyThread();
        // This line will create a new Thread which is responsible to execute run().
        t.start();

        // 2 threads started and egarly waiting for CPU time, scheduling is done by T.S

        for(int i=0;i<=10;i++){
            System.out.println("Main Thread");
        }
    }
}