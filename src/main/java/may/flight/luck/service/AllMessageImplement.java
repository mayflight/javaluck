package may.flight.luck.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service("allMessageService")
public class AllMessageImplement implements AllMessageService{
    @Override
    public Map<String, String> getNameAgeMap(int size) {
        Map<String,String> map = new HashMap<>();
        map.put("my", "10");
        map.put("you", "20");
        System.out.println("map:"+map.toString());
        return map;
    }
}
