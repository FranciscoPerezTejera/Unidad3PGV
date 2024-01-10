package unidad3pgv.pruebasSockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {

    private static final int PUERTO_CONEXION = 6790;
    private static List<ControladorCliente> clientes = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PUERTO_CONEXION);
            System.out.println("Servidor iniciado. Esperando conexiones...");
            
            int contadorCliente = 1;
            
            while (true) {
                Socket clienteSocket = serverSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket);

                ControladorCliente nuevoCliente = new ControladorCliente(clienteSocket, "Cliente " + contadorCliente);
                clientes.add(nuevoCliente);
                contadorCliente++;
                Thread thread = new Thread(nuevoCliente);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void transmisionMensaje(String mensaje, ControladorCliente clienteReceptor) {
        for (ControladorCliente cliente : clientes) {
            if (cliente != clienteReceptor) {
                cliente.enviarMensaje(mensaje, cliente);
            }
        }
    }
}

class ControladorCliente implements Runnable {

    private Socket clientSocket;
    private BufferedReader reader;
    private PrintWriter writer;
    private String nombreCliente;

    public ControladorCliente(Socket socket, String nombreCliente) {
        this.clientSocket = socket;
        this.nombreCliente = nombreCliente;
        try {
            this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    

    public void run() {
        String mensaje;
        try {
            while ((mensaje = reader.readLine()) != null) {
                System.out.println("Mensaje recibido: " + mensaje);
                TCPServer.transmisionMensaje(mensaje, this);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje, ControladorCliente cliente) {
        writer.println(cliente.getNombreCliente() + " : " + mensaje);
    }
}
