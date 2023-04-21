package org.example;

import picocli.CommandLine.*;
import picocli.CommandLine;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

@Command(
        name="Server.jar",
        description = "Commands:"
)
public class Server implements Runnable{

    private static String storePath = "repository\\store\\";
    private String logsPath = "repository\\logs\\";
    private static Socket socket;
    private static ServerSocket serverSocket;
    private static BufferedReader in;
    private static BufferedWriter out;
    @Option(names = {"-h", "--host"}, description = "server host")
    private String host = "localhost";
    @Option(names = {"-p", "--port"}, description = "server port")
    private Integer port = 8080;
    private String logFile = "log.txt";
    @Option(names = { "--help" }, usageHelp = true, description = "display a help message")
    private boolean helpRequested;


    public static void main(String[] args) throws IOException {
        File theDir = new File("repository/store");
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        theDir = new File("repository/logs");
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        CommandLine.run(new Server(), args);
    }

    @Override
    public void run(){
        String log;
        try {
            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logsPath + logFile));
            ServerSocket serverSocket = new ServerSocket(port);
            log = String.format("INFO - Server work on %s:%s", host, port);
            System.out.println(log);
            logWriter.write(log);
            logWriter.newLine();
            while (true) {
                Socket socket = serverSocket.accept();

                BufferedReader inputStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                StringBuilder stringBuffer = new StringBuilder();


                String inputLine;
                while ((inputLine = inputStream.readLine()) != null) {
                    stringBuffer.append(inputLine);
                    stringBuffer.append("\r\n");
                    if(inputLine.equals("")  || inputLine.equals("\r"))  break;
                }
                while (inputStream.ready() && (inputLine = inputStream.readLine()) != null) {
                    stringBuffer.append(inputLine);
                    stringBuffer.append("\r\n");
                }
                Request request = Request.createRequest(stringBuffer);
                Response response = handleRequest(request);
                log = String.format("INFO - %s %s %s %s", request.getMethod(), request.getUrl(), response.getVersion(), response.getStatus());
                System.out.println(log);
                logWriter.write(log);
                logWriter.newLine();

                OutputStream outStream = socket.getOutputStream();

                try {
                    outStream.write(response.toString().getBytes());
                    outStream.flush();
                    outStream.close();
                }
                catch (SocketException e) {
                    socket.close();
                }
                inputStream.close();

                socket.close();

                log = String.format("INFO - Connection %s:%s closed", socket.getInetAddress(), socket.getPort());
                System.out.println(log);
                logWriter.write(log);
                logWriter.newLine();
                logWriter.flush();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Response handleRequest(Request request) throws IOException {
        Response response = null;
        if(request.getMethod().equals("POST")){
            BufferedWriter writer = new BufferedWriter(new FileWriter(storePath + request.getUrl()));
            writer.write(request.getContent().toString());
            writer.close();

            response = new Response("HTTP/1.1", 200);
        }
        else if(request.getMethod().equals("GET")){
            try {
                BufferedReader reader = new BufferedReader(new FileReader(storePath+ request.getUrl()));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                    sb.append("\r\n");
                }
                reader.close();

                response = new Response("HTTP/1.1", 200);
                response.addContent(sb.toString());
            }
            catch (FileNotFoundException e){
                response = new Response("HTTP/1.1", 404);
            }
        }
        else if(request.getMethod().equals("OPTIONS")){

        }
        return response;
    }
}
