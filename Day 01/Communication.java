class Producer extends Thread{

    StringBuffer sb;

    boolean dataProvider = false;

    public Producer(){
        //String buffer object is create with a default capacity 16
        sb = new StringBuffer();
    }

    @Override
    public void run(){
        for(int i=1;i<=10;i++){
            try{
                sb.append(i+": ");
                Thread.sleep(100);
                System.out.println("appending");
            }
            catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        // informing the consumer that data is produced
        dataProvider = true;
    }
}

class Consumer extends Thread{
     Producer producer;

     public Consumer(Producer producer){
        this.producer = producer;
     }


    @Override
    public void run(){
        while(producer.dataProvider == false){
            try{
                Thread.sleep(10);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        // consume the data produced by the producer
        System.out.println(producer.sb);
    }
}

// Inefficient way of interthread coomunication
public class Communication {
    public static void main(String[] args) {
        Producer obj1 = new Producer();
        Consumer obj2 = new Consumer(obj1);
    
        Thread t1 = new Thread(obj1);// Producer thread 
        Thread t2 = new Thread(obj2);// consumer thread
    
        t2.start(); // consumer should wait
        t1.start(); // producer should wait

    }
}