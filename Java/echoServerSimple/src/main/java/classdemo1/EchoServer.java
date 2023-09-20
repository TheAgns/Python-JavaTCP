package classdemo1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class ClientHandler extends Thread {
    Socket socket;
    PrintWriter pw;
    Scanner sc;
         
    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            pw = new PrintWriter(socket.getOutputStream(), true);
            sc = new Scanner(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            handleClient();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClient() throws IOException {

        pw.println("You are connected");
        boolean go = true;
        while (go) {
            String message = sc.nextLine(); //Blocking call
            String[] messageArr = message.split("#");
            switch (messageArr[0]) {
                case "HELP":
                    break;
                case "CLOSE":
                    pw.println("Connection is closing, type HELP for commands");
                    go = false;
                    break;
                case "UPPER":
                    pw.println(messageArr[1].toUpperCase());
                    break;
                case "LOWER":
                    pw.println(messageArr[1].toLowerCase());
                    break;
                case "REVERSE":
                    StringBuilder input = new StringBuilder();
                    input.append(messageArr[1]);
                    pw.println(input.reverse());
                    break;
                case "TRANSLATE":
                    Map<String, String> translateMap = new HashMap();
                    translateMap.put("hund", "dog");
                    if (messageArr[1].equals(translateMap.get("hund"))) {
                        pw.println(translateMap.values());
                    } else {
                        pw.println("NOT_FOUND");
                    }
                    break;
                default:
                    pw.println("Connection is closing");
                    go = false;
                    socket.close();
                    break;
            }
        }
        socket.close();
    }
}

public class EchoServer {
    public static final int DEFAULT_PORT = 2345;
    private ServerSocket serverSocket;


    private void startServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Server started, listening on: " + port);
        while (true) {
            System.out.println("Waiting for a client");
            Socket socket = serverSocket.accept(); //Blocking call
            System.out.println("New client connected");
            ClientHandler clientHandler = new ClientHandler(socket);
            clientHandler.start();
        }
    }

    public static void main(String[] args) throws IOException {
        int port = DEFAULT_PORT;
        if (args.length == 1) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid port number, use default port : " + DEFAULT_PORT);
            }
        }
        EchoServer server = new EchoServer();
        server.startServer(port);
    }
}
