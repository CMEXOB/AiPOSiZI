package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public abstract class Http {
    private StringBuilder content;
    private Map<String, String> headers;

    public Http() {
        headers = new HashMap<>();
        content = new StringBuilder();
    }

    public void addContent(String content){
        this.content.append(content);
    }

    public void addHeader(String key, String value){
        headers.put(key, value);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for(Map.Entry<String, String> entry : headers.entrySet()){
            result.append(String.format("%s: %s\r\n", entry.getKey(), entry.getValue()));
        }
        if (content.length() != 0) {
            result.append("\r\n").append(content);
        }
        return result.toString();
    }
}
