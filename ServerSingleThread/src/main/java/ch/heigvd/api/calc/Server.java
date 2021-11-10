package ch.heigvd.api.calc;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator server implementation - single threaded
 */
public class Server {

    private static final String END_LINE = "XOXO";
    private static final int PORT_LISTEN = 256;

    private final static Logger LOG = Logger.getLogger(Server.class.getName());

    /**
     * Main function to start the server
     */
    public static void main(String[] args) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        (new Server()).start();
    }

    /**
     * Start the server on a listening socket.
     */
    private void start() {
        /* TODO: implement the receptionist server here.
         *  The receptionist just creates a server socket and accepts new client connections.
         *  For a new client connection, the actual work is done by the handleClient method below.
         */

        // Creation of "receptionist" socket and client socket

        ServerSocket serverSocket;
        Socket       clientSocket;

        // Binding of receptionist socket with port 256 (from which the serveur will listen)
        try {
            serverSocket = new ServerSocket(PORT_LISTEN);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error while trying to bind socket with port.", e);
            return;
        }

        // Blocks until a client arrives
        try {
            // Receives a new socket when a client has arrived
            LOG.log(Level.INFO, "Single-threaded: Waiting for a new client on port {0}", PORT_LISTEN);
            clientSocket = serverSocket.accept();
            handleClient(clientSocket);

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR : cannot accept connexion, wrong port or timeout", e);
        }
    }

    /**
     * Handle a single client connection: receive commands and send back the result.
     *
     * @param clientSocket with the connection with the individual client.
     */
    private void handleClient(Socket clientSocket) {

        /* TODO: implement the handling of a client connection according to the specification.
         *   The server has to do the following:
         *   - initialize the dialog according to the specification (for example send the list
         *     of possible commands)
         *   - In a loop:
         *     - Read a message from the input stream (using BufferedReader.readLine)
         *     - Handle the message
         *     - Send to result to the client
         */

        // Creation of buffers (read/write)
        BufferedReader in  = null;
        BufferedWriter out = null;

        // Initialization of buffers (read/write)
        try {
            in  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), "UTF-8"));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), "UTF-8"));

            // Reads client message, handles it and sends the result to the client
            String line;
            String[] message;
            int result;

            // Dialog initialization
            // -> Sends the list of possible commands
            out.write("BJR " + END_LINE + "\n" +
                    "- AVAILABLE OPERATIONS " + END_LINE + "\n" +
                    "- ADD " + END_LINE + "\n" +
                    "- MULT " + END_LINE + "\n" +
                    "- END OPERATIONS " + END_LINE + "\n");
            out.flush();

            LOG.info("Reading until client sends DONE or closes the connection...");
            while(!((line = in.readLine()).equals("DONE " + END_LINE))) {
                message = line.split(" ");


                if(message.length != 4) {
                    System.err.println("Error : client must give 3 components.\n");
                    return;
                }

                int x = Integer.parseInt(message[1]);
                int y = Integer.parseInt(message[2]);

                if (message[0].equals("ADD")) {
                    int mdr = x + y;
                    out.write("RESULT " + mdr + " " + END_LINE + "\n");
                } else if(message[0].equals("MULT")){
                    out.write("RESULT " + x*y + " " + END_LINE + "\n");
                } else {
                    System.err.println("Error : operation is not computable.\n");
                    return;
                }
                out.flush();
            }
            LOG.info("Cleaning up resources...");
            clientSocket.close();
            in.close();
            out.close();

        } catch (Exception e) {

            if (in != null) {
                try {
                    in.close();
                } catch (IOException io) {
                    LOG.log(Level.SEVERE, io.getMessage(), io);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException io) {
                    LOG.log(Level.SEVERE, io.getMessage(), io);
                }
            }
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException io) {
                    LOG.log(Level.SEVERE, io.getMessage(), io);
                }
            }
            LOG.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}