package unidad3pgv.pruebasSockets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class TCPCliente {
    public static void main(String[] args) {
        
        final String IP_SERVIDOR = "localhost";
        final int PUERTO_CONEXION = 6790;

        try {
            Socket socket = new Socket(IP_SERVIDOR, PUERTO_CONEXION);
            System.out.println("Conectado al servidor.");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);

            Thread inputThread = new Thread(() -> {
                String message;
                try {
                    while ((message = reader.readLine()) != null) {
                        System.out.println("Mensaje recibido: " + message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            inputThread.start();

            // Capturar entrada del usuario para enviar mensajes al servidor
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
            String userInput;
            while ((userInput = userInputReader.readLine()) != null) {
                writer.println(userInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}