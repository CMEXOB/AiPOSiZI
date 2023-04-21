package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String version;
    private Integer status;
    private String accessControlAllowOrigin; //Access-Control-Allow-Origin
    private String accessControlAllowMethods; //Access-Control-Allow-Methods
    private StringBuilder content;

    public Response(String version, Integer status) {
        this.version = version;
        this.status = status;
        accessControlAllowOrigin = "localhost:8080";
        accessControlAllowMethods = "GET, POST, OPTIONS";
        content = new StringBuilder();
    }

    public void addContent(String content){
        this.content.append(content);
    }

    @Override
    public String toString() {
        String result = version + " " + status + "\r\n" +
                "Access-Control-Allow-Origin: " + accessControlAllowOrigin + "\r\n" +
                "Access-Control-Allow-Methods: " + accessControlAllowMethods + "\r\n";
        if(content.length() != 0){
            result += "\r\n" + content;
        }
        return result;
    }
}
