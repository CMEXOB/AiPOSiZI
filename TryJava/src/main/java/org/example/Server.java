package org.example;

import picocli.CommandLine.*;
import picocli.CommandLine;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    @Option(names = {"-l", "--log"}, description = "log file")
    private String logFile = "log.txt";
    @Option(names = { "--help" }, usageHelp = true, description = "display a help message")
    private boolean helpRequested;
    private Set<String> methods = new HashSet<>(Arrays.asList("GET","HEAD","POST","PUT","DELETE","CONNECT","OPTIONS","TRACE","PATCH"));;


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
        String fullLog;
        SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        Date date;
        try {
            BufferedWriter logWriter = new BufferedWriter(new FileWriter(logsPath + logFile));
            ServerSocket serverSocket = new ServerSocket(port);
            date = new Date();
            log = String.format("%s INFO - Server work on %s:%s", format.format(date), host, port);
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

                date = new Date();
                log = String.format("%s INFO - %s %s %s %s", format.format(date), request.getMethod(), request.getUrl(), response.getVersion(), response.getStatus());
                System.out.println(log);
                logWriter.write(log);
                fullLog = String.format("\r\n--Request--\r\n%s--Request--\r\n--Response--\r\n%s--Response--\r\n", request, response);
                logWriter.write(fullLog);
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

                date = new Date();
                log = String.format("%s INFO - Connection %s:%s closed", format.format(date), socket.getInetAddress(), socket.getPort());
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
    public Response handleRequest(Request request) throws IOException {
        Response response = new Response("HTTP/1.1", 200);

        response.setAccessControlAllowOrigin(String.format("%s:%s", host, port));
        response.setAccessControlAllowMethods("GET, POST, OPTIONS");

        if(request.getMethod().equals("POST")){
            BufferedWriter writer = new BufferedWriter(new FileWriter(storePath + request.getUrl()));
            writer.write(request.getContent().toString());
            writer.close();
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

                response.addContent(sb.toString());
            }
            catch (FileNotFoundException e){
                response.setStatus(404);
            }
        }
        else if(request.getMethod().equals("OPTIONS")){

        }
        else if(methods.contains(request.getMethod())){
            response.setStatus(405);
        }
        else{
            response.setStatus(400);
        }
        return response;
    }
}
