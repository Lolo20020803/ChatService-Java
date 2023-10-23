import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    ServerSocket serverSocket = null;
    int maxConnections =2;
    int connections = 0;
    public ChatServer(int port){
        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void start(){
        try {
            /*
            Con esto conseguimos multiples conexiones hacia el servidor, pero por la implementacion
            de la consola y del teclado en java se produce una condicion de carrera entre los
            clientes, al mandar varios mensajes desde el servidor si hay multiples clientes
            no llegaran a todos los clientes y puede que llegeuen en distinto orden
            No he encontrado como solucionar esto ya que se depende de que los clientes usa el mismo System
            por tanto la consola es compartida para todos y no se puede hacer que lleguen los mensajes de manera
            independiente.

             */
            while (connections<maxConnections) {

                Socket socket = serverSocket.accept();
                Chat chat = new Chat(socket);
                chat.start();
                connections++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            connections--;

        }
    }


    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(9999);
        chatServer.start();
    }
}
