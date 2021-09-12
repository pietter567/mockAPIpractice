package com.example.mockapi.web.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;


//whole response body
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> {

    private Integer status;
    private T data;
    private Map<String, List<String>> errors;

}
