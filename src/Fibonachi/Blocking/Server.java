package Fibonachi.Blocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Server {
    public static void main(String[] args) throws IOException {
        boolean finish = false;
        ServerSocket serverSocket = new ServerSocket(8777);
        Socket clientSocket = serverSocket.accept(); // ждем подключения
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        clientSocket.getInputStream()));
        while (!finish) {
            String msg = in.readLine();
            try {
                int fiboLimit = Integer.parseInt(msg);
                List<BigInteger> listFibo = Stream
                        .iterate(new BigInteger[]{BigInteger.valueOf(0), BigInteger.valueOf(1)}, t -> new BigInteger[]{t[1], t[0].add(t[1])})
                        .limit(fiboLimit)
                        .map(t -> t[0])
                        .collect(Collectors.toList());
                msg = fiboLimit + "-ое число фибоначи: " + listFibo.get(listFibo.size() - 1);
                listFibo.clear();
                Thread.sleep(2000);
            } catch (Exception e) {
                if (!msg.equals("end")) {
                    msg = "Ваше сообщение: " + msg + " не цифра, попробуйте ввести цифру";
                }
            }
            out.println(msg);

            if (msg.equals("end")) {
                finish = false;
                in.close();
                out.close();
                clientSocket.close();
            }
        }
    }
}
