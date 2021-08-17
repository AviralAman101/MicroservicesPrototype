package in.programming.testfeign.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignControllerEx {

    @GetMapping("/feign/status")
    public String getStatus(){
        return "Working";
    }
}
