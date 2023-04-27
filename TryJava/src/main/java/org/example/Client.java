package org.example;

import picocli.CommandLine;
import picocli.CommandLine.*;

import java.io.*;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

@Command
 (
        name="Client.jar",
        description = "Commands:"
)
public class Client implements Runnable{
    private static Socket socket;
    private static BufferedReader reader;
    private static BufferedReader in;
    private static BufferedWriter out;

    @Option(names = {"-c", "--content"}, description = "http body")
    private String content = "";
    @Option(names = {"-u", "--url"}, description = "unique resource identifier")
    private String url;
    @Option(names = {"-m", "--method"}, description = "http method")
    private String method;
    @Option(names = {"-h", "--host"}, description = "connection host")
    private String host = "localhost";
    @Option(names = {"-p", "--port"}, description = "connection port")
    private Integer port = 8080;
    @Option(names = {"-f", "--file"}, description = "request file")
    private String filename = null;
    private String userAgent = "CMEXOB";
    private String accept = "text/plain";
    private String contentType = "text/plain";
    private String acceptEncoding = "utf-8";
    @Option(names = { "--help" }, usageHelp = true, description = "display a help message")
    private boolean helpRequested;

    public static void main(String[] args) {
        CommandLine.run(new Client(), args);
    }

    @Override
    public void run() {
        Request request;
        if(filename != null){
            try {
                BufferedReader inputStream = new BufferedReader(new FileReader(filename));
                StringBuilder stringRequest = new StringBuilder();

                String inputLine;
                while ((inputLine = inputStream.readLine()) != null) {
                    stringRequest.append(inputLine);
                    stringRequest.append("\r\n");
                    if(inputLine.equals("")  || inputLine.equals("\r"))  break;
                }
                while (inputStream.ready() && (inputLine = inputStream.readLine()) != null) {
                    stringRequest.append(inputLine);
                    stringRequest.append("\r\n");
                }
                request = Request.createRequest(stringRequest);
                connect(request);

            }
            catch (Exception e){
                String log = String.format("Can't find file %s", filename);
                System.out.println(log);
            }
        }
        else if(method != null && url != null){
            request = new Request(method, "\\" + url, "HTTP/1.1");
            request.addContent(content +"\n");
            request.addHeader("Host", String.format("%s:%s",host, port));
            request.addHeader("User-Agent", userAgent);
            request.addHeader("Accept", accept);
            request.addHeader("Accept-Encoding", acceptEncoding);
            request.addHeader("Content-Type", contentType);
            connect(request);
        }
        else{
            String log = "Input method(-m, --method) and url(-u, --url) or request file(-f, --file)";
            System.out.println(log);
        }
    }

    public void connect(Request request){
        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(System.in));

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            out.write(request.toString());
            out.newLine();
            out.flush();

            StringBuilder stringBuffer = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                stringBuffer.append(line);
                stringBuffer.append("\r\n");
            }
            System.out.println(stringBuffer);

            reader.close();
            in.close();
            out.close();
        }
        catch (ConnectException | UnknownHostException e) {
            String log = String.format("Can't connect to %s", request.getHeaders().get("Host"));
            System.out.println(log);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
