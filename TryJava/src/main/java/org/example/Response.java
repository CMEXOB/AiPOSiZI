package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response extends Http{
    private String version;
    private Integer status;

    public Response(String version, Integer status) {
        this.version = version;
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%s %s\r\n", version, status));
        result.append(super.toString());
        return result.toString();
    }
}
