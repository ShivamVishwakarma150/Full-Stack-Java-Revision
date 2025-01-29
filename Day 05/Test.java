class MyThread extends Thread{
    @Override
    public void run() {
        try{
            for (int i = 0; i <=5; i++) {
                System.out.println("Child thread");
                Thread.sleep(3000);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
}
class Test {
    public static void main(String[] args) {
        
    }
}