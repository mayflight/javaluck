package may.flight.luck.component;

public class Q {
    private int i;
    private boolean isSet = false;

    synchronized int getI() {
        try {
            if (!isSet) wait();
            System.out.println("get " + i);
            isSet = false;
            notify();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    synchronized void setI(int i) {
        try {
            if (isSet) wait();
            this.i = i;
            System.out.println("set " + i);
            isSet = true;
            notify();
        }catch (Exception e) {
            e.printStackTrace();
        }

    }
}
