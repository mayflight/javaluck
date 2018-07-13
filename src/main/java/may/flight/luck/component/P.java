package may.flight.luck.component;

public class P implements Runnable{

    private Q q;
    private Thread thread;
    public P(Q q, String name) {
        this.q = q;
        thread = new Thread(this, name);
        thread.start();
    }

    @Override
    public void run() {
        int i = 0;
        while (i<= 20) {
            i = i + 1;
            q.setI(i);
        }
    }
}
