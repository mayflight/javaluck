package may.flight.luck.component;

public class Caller {

    public void print(String text) throws InterruptedException {
        System.out.println(text+" start");
        Thread.sleep(1000);
        System.out.println(text+" end");
    }

}
