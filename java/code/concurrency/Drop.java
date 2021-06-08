package concurrency;

public class Drop {
    //Message sent from producer
    //to consumer
    private String message;
    //True if consumer should wait
    //for producer to send message
    //false if producer should wait for
    //consumer to retrieve message
    private boolean empty = true;

    public synchronized String take(){
        //wait until message is
        //available
        while (empty){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //Toggle status
        empty = true;
        //Notify producer that
        //status has changed
        notifyAll();
        return message;
    }

    public synchronized void put(String message){
        //wait until message has
        //been retrieved
        while (!empty){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //toggle status
        empty = false;
        //store message
        this.message = message;
        //notify consumer that status
        //has changed
        notifyAll();
    }
}
