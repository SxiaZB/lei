package com.lei.smart.remote;

import org.springframework.stereotype.Service;

@Service
public class RestServiceImpl implements  RestService {
    @Override
    public String getRemote() {
        return "降级---";
    }
}
