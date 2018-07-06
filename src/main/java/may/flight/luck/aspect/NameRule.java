package may.flight.luck.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Aspect
@Component
public class NameRule {

    @Pointcut("execution(public * may.flight.luck.service.AllMessageService.getNameAgeMap(..)) && args(size))")
    public void nameRule(int size) {

    }

    @Before("nameRule(size)")
    public void before(JoinPoint point, int size) {

    }

    @AfterReturning(value = "nameRule(size)", returning = "map")
    public void selectName(JoinPoint point, Map<String,String> map, int size) {
        System.out.println("args:"+size+";aspect:"+map.toString());
        map.remove("my");
    }
}
