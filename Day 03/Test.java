class MyThread extends Thread{

    @Override
    public void start() {
        System.out.println("start method called");
    }

    @Override
    public void run() {
        System.out.println("No arg run method");
    }
}


public class Test{
    public static void main(String[] args) {
        MyThread t = new MyThread();

        // since our class start() is called, no new thread is created.
        t.start();


        for(int i=0;i<=10;i++){
            System.out.println("Main Thread");
        }
    }
}