package may.flight.luck.component;

import com.yadong.ye.spi.Say;

import java.util.ServiceLoader;



public class SpiTest {
    public static void main(String[] args) {
        ServiceLoader<Say> loader = ServiceLoader.load(Say.class);
        loader.forEach(Say::say);
    }

}
