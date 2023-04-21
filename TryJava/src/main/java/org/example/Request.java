package org.example;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Getter
@Setter
public class Request {
    private String method;
    private String url;
    private String version;
    private String host;
    private String userAgent;
    private String accept;
    private String acceptEncoding;
    private StringBuilder content;

    public Request(String method, String url, String version) {
        this.method = method;
        this.url = url;
        this.version = version;
        content = new StringBuilder();
    }

    public void readFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        while (reader.ready()){
            content.append(reader.read());
        }
        reader.close();
    }

    public void addContent(String content){
        this.content.append(content);
    }

    public String getStartLine(){
        String result = method + " " + url + " " + version;
        return result;
    }

    public static Request createRequest(StringBuilder builder){
        String str[] = builder.toString().split("\r\n");
        String s[] = str[0].split(" ");
        Request request = new Request(s[0], s[1], s[2]);
        boolean headers = true;
        for(int index = 1; index < str.length; index++){
            if(str[index].equals("")){
                headers = false;
            }
            else if(headers) {
                s = str[index].split(": ");
                if (s[0].equals("Accept")) {
                    request.setAccept(s[1]);
                }
                else if (s[0].equals("Accept-Encoding")) {
                    request.setAcceptEncoding(s[1]);
                }
                else if (s[0].equals("Host")) {
                    request.setHost(s[1]);
                }
                else if (s[0].equals("User-Agent")) {
                    request.setUserAgent(s[1]);
                }
            }
            else {
                request.addContent(str[index] +"\r\n");
            }
        }
        return request;
    }

    @Override
    public String toString() {
        String result = method + " " + url + ' ' + version + "\r\n" +
                "Host: " + host + "\r\n" +
                "User-Agent: " + userAgent + "\r\n" +
                "Accept: " + accept + "\r\n" +
                "Accept-Encoding: " + acceptEncoding + "\r\n";
        if (content.length() != 0) {
            result += "\r\n" + content;
        }
        return result;
    }
}
