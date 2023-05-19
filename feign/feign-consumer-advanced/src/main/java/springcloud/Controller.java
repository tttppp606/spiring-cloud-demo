package springcloud;

import org.example.springcloud.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private IService iService;

    @GetMapping("/sayHi")
    public String sayHi(){
        return iService.sayHi();
    }

    @GetMapping("/retry")
    public String retry (int timeout) {
        return iService.retry(timeout);
    }

}
