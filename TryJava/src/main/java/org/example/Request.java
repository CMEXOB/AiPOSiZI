package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request extends Http{
    private String method;
    private String url;
    private String version;

    public Request(String method, String url, String version) {
        super();
        this.method = method;
        this.url = url;
        this.version = version;
    }

    public static Request createRequest(StringBuilder builder){
        String[] requestLines = builder.toString().split("\r\n");
        String[] methodPathVersion = requestLines[0].split(" ");
        Request request = new Request(methodPathVersion[0], methodPathVersion[1], methodPathVersion[2]);
        boolean isHeaders = true;
        String[] headerValue;
        for(int index = 1; index < requestLines.length; index++){
            if(requestLines[index].equals("")){
                isHeaders = false;
            }
            else if(isHeaders) {
                headerValue = requestLines[index].split(": ");
                request.addHeader(headerValue[0], headerValue[1]);
            }
            else {
                request.addContent(requestLines[index] +"\r\n");
            }
        }
        return request;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(String.format("%s %s %s\r\n", method, url, version));

        result.append(super.toString());
        return result.toString();
    }
}
