package may.flight.luck.component;



public class ThreadTest implements Runnable{
    private Thread thread;
    private String name;

    private  Caller caller;

    public Caller getCaller() {
        return caller;
    }

    public void setCaller(Caller caller) {
        this.caller = caller;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    ThreadTest(Caller caller, String name) {
        thread = new Thread(this, name);
        this.caller = caller;
        thread.start();
    }


    ThreadTest(String name) {
        thread = new Thread(this, name);
        thread.start();
        System.out.println("new thread "+thread.getName()+" has started");
    }

    @Override
    public void run() {
        try {
            if (null != caller) {
//                synchronized (caller) {
//                    caller.print(thread.getName());
//                }
                caller.print(thread.getName());
                return;
            }
            for (int i = 0; i< 3; i++) {
                Thread.sleep(1000);
                System.out.println(thread.getName()+":"+ i);
            }
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
