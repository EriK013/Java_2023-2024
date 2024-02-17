import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SerwerKiK {

    private static List<Client> clients = new ArrayList<>();
    private static class Client implements Runnable{
        Socket socket;
        PrintWriter out;
        BufferedReader in;
        char gracz;
        int kolumny, wiersze, dlugosc_wygranej;

        public Client(Socket socket, int kolumny, int wiersze, int dlugosc_wygranej){
            this.socket = socket;
            this.kolumny = kolumny;
            this.wiersze = wiersze;
            this.dlugosc_wygranej = dlugosc_wygranej;
        }

        @Override
        public void run() {
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                przyporzadkujSymbol();
                out.println("[START]" + " " + wiersze + " " + kolumny + " " + dlugosc_wygranej + " " + gracz);

                while (true){
                    String msg = in.readLine();
                    if (msg != null){
                        if (msg.startsWith("[MOVE]") || msg.startsWith("[END]")){
                            sendMove(msg);
                        }
                    }
                }
            } catch (IOException e){
                throw new RuntimeException(e);
            }
        }

        private void sendMove(String msg){
            for (var to : clients) {
                if (to != this) {
                    to.out.println(msg);
                }
            }
        }

        private void przyporzadkujSymbol(){
            if (clients.size() % 2 != 0){
                gracz = 'X';
            }
            else {
                gracz = 'O';
            }
        }
    }


    public static void main(String[] args) throws IOException {
        if (args.length != 4){
            System.out.println("Usage: [port] [szerokość planszy] [wysokość planszy] [warunek wygranej]");
            System.exit(-1);
        }
        int port = Integer.parseInt(args[0]);
        int szerokosc = Integer.parseInt(args[1]);
        int wysokosc = Integer.parseInt(args[2]);
        int wwygranej = Integer.parseInt(args[3]);
        if (wwygranej > wysokosc && wwygranej > szerokosc){
            System.out.println("Warunek wygranej musi być mniejszy od rozmiarów");
            System.exit(-1);
        }

        try(var serverSocket = new ServerSocket(port)){
            System.out.println("[INFO] Serwer działa na porcie " + port);

            while (true){
                var clientSocket = serverSocket.accept();
                var client = new Client(clientSocket, szerokosc, wysokosc, wwygranej);
                clients.add(client);
                new Thread(client).start();
            }
        }
    }
}
