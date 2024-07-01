package com.mstramohz.BankingApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Response extends RepresentationModel<Response> {
    private boolean status;
    private String message;
    private Map<String, Object> data = new HashMap<>();

    public static Response build(boolean status, String message, String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return new Response(status, message, map);
    }


}
