package in.programming.userauthentication.proxy;

import in.programming.userauthentication.controller.validation.FeignConfig;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "test-feign" , url="http://localhost:8050"
        , configuration = {FeignConfig.class})
@RibbonClient(name = "test-feign")
public interface TestFeignProxy {


    @GetMapping(value = "/feign/status")
    public String getStatus();
}
