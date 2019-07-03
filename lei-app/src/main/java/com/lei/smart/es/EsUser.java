package com.lei.smart.es;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.apache.lucene.document.FieldType;
import org.jboss.logging.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.util.Date;
@Getter
@Setter
public class EsUser {

    private Long date;
    private String country;
    private String name;
    private String age;
}
