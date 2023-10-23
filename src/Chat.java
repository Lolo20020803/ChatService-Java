import java.io.*;
import java.net.Socket;

public class Chat {

    volatile boolean chatEnded = false;
    Socket socket = null;
    Thread thKeyboard = null;
    Thread thScreen = null;
    public Chat(Socket socket){
        this.socket = socket;
    }
    public void start(){
        try {
            this.thKeyboard = new Thread(new ChatThread(this.socket.getInputStream(),System.out));
            this.thScreen = new Thread(new ChatThread(System.in,this.socket.getOutputStream()));
        }catch (IOException e){
            e.printStackTrace();
        }

        this.thScreen.start();
        this.thKeyboard.start();
    }
    private class ChatThread implements Runnable{
        private BufferedReader lineInputStream = null;
        private PrintWriter lineOutputStream = null;
        ChatThread(InputStream inputStream, OutputStream outputStream){
            lineInputStream = new BufferedReader( new InputStreamReader(inputStream));
            lineOutputStream = new PrintWriter(outputStream,true);
        }
        @Override
        public void run() {
            boolean endIsRead = false;
            String line = null;
            do{
                try {
                    line = lineInputStream.readLine();

                    lineOutputStream.println(line);

                    endIsRead = line.contentEquals("*");

                    if (chatEnded){
                        if (!endIsRead){
                            lineOutputStream.println("*");
                        }
                        socket.close();
                    } else if (endIsRead) {
                        chatEnded = endIsRead;
                    }
                }catch (IOException e){
                    e.printStackTrace();

                }
            }while (!chatEnded);
        }
    }

}
