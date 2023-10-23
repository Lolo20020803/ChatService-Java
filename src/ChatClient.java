import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    private String host = null;
    private int port = 0;
    private Socket socket = null;
    private Chat chat = null;

    ChatClient (String host, int port){
        this.host = host;
        this.port = port;
        try {
            this.socket = new Socket(host,port);
            chat = new Chat(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    void start(){
        this.chat.start();
    }

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient("localhost",9999);
        chatClient.start();
    }
}
