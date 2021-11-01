package Fibonachi.Blocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        String host = "localhost";
        int port = 8777;
        Socket clientSocket = new Socket(host, port);
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        try (Scanner scanner = new Scanner(System.in)) {
            String msg;
            while (true) {
                System.out.println("Введите число до которого будем считать числа фибоначи, либо введите 'end' чтоб закончить");
                msg = scanner.nextLine();
                if ("end".equals(msg)) {
                    out.close();
                    in.close();
                    clientSocket.close();
                    break;
                }
                out.println(msg);
                Thread.sleep(1000);
                System.out.println("1сек. работа");
                String resp = in.readLine();
                System.out.println(resp);
            }
        }
    }
}
