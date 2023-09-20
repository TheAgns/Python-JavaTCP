package classdemo1;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    Socket socket;
    PrintWriter pw;
    Scanner sc;

    public void connect(String address, int port) throws IOException {
        socket = new Socket(address, port);
        pw = new PrintWriter(socket.getOutputStream(), true);
        sc = new Scanner(socket.getInputStream());
        System.out.println(sc.nextLine());
        Scanner keyboard = new Scanner(System.in);
        boolean keepRunning = true;
        while (keepRunning) {
            String messageToSend = keyboard.nextLine(); //Blocking call
            pw.println(messageToSend);
            System.out.println(sc.nextLine());
            if (messageToSend.equals("stop")) {
                keepRunning = false;
            }
        }
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new EchoClient().connect("10.50.137.133",2345);
    }
}

