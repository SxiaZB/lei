package com.lei.smart.kafka;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Message {
    private String id;

    private String msg;

    private Date sendTime;
}
