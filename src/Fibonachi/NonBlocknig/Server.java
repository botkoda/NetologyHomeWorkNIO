package Fibonachi.NonBlocknig;

import java.io.IOException;
import java.math.BigInteger;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Server {
    public static void main(String[] args) throws IOException {

        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 23334));
        while (true) {
            try (SocketChannel socketChannel = serverChannel.accept()) {
                final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int byteCount = socketChannel.read(inputBuffer);
                    if (byteCount == -1) break;
                    String msg = new String(inputBuffer.array(), 0, byteCount, StandardCharsets.UTF_8);
                    try {
                        int fiboLimit = Integer.parseInt(msg);
                        List<BigInteger> listFibo = Stream
                                .iterate(new BigInteger[]{BigInteger.valueOf(0), BigInteger.valueOf(1)}, t -> new BigInteger[]{t[1], t[0].add(t[1])})
                                .limit(fiboLimit)
                                .map(t -> t[0])
                                .collect(Collectors.toList());
                        msg = fiboLimit + "-ое число фибоначи: " + listFibo.get(listFibo.size() - 1);
                        Thread.sleep(2000);
                    } catch (Exception e) {

                        msg = "Ваше сообщение: " + msg + " не цифра, попробуйте ввести цифру";
                    }
                    inputBuffer.clear();
                    socketChannel.write(ByteBuffer.wrap((msg).getBytes(StandardCharsets.UTF_8)));
                }
            } catch (Exception e) {
                System.out.println("Ошибка");
            }
        }
    }
}
