package in.programming.userauthentication.controller;



import in.programming.userauthentication.proxy.TestFeignProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    TestFeignProxy tFeignProxy;

    @GetMapping("/status")
    public String welcoe(){
        return tFeignProxy.getStatus();
    }

}
