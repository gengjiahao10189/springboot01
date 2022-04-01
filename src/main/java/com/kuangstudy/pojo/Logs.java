package com.kuangstudy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Logs {
    private String id;
    private String className;
    private String methodName;
    private String params;
    private String time;
}
