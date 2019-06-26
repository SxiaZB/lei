package com.lei.smart.remote;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "app-client", fallback = RestServiceImpl.class,path = "app")
public interface RestService {
    @GetMapping("demo")
    public String getRemote();
}
