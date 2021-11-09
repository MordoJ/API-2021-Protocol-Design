package ch.heigvd.api.calc;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator client implementation
 */
public class Client {

    private static final String END_LINE = "XOXO";
    private static final String END_COMMUNICATION = "DONE";
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int PORT = 256;

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    /**
     * Main function to run client
     *
     * @param args no args required
     */
    public static void main(String[] args) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        BufferedReader stdin = null;

        /* TODO: Implement the client here, according to your specification
         *   The client has to do the following:
         *   - connect to the server
         *   - initialize the dialog with the server according to your specification
         *   - In a loop:
         *     - read the command from the user on stdin (already created)
         *     - send the command to the server
         *     - read the response line from the server (using BufferedReader.readLine)
         */

        stdin = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = null;
        BufferedWriter out = null;
        BufferedReader in = null;

        try {
            clientSocket = new Socket(SERVER_ADDRESS, PORT);
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // First thing the serve does is sending the available commands, we have to
            // display them right after the connection occured.
            System.out.println(in.read());
            String request;

            do {

                System.out.println("Enter your request : ");
                request = stdin.readLine() + " " + END_LINE;
                LOG.log(Level.INFO, "Sending \"" + request + "\" to the server.");
                out.write(request);
                out.flush();

                LOG.log(Level.INFO, "*** Response sent by the server: ***");
                String line;
                while ((line = in.readLine()) != null) {
                    LOG.log(Level.INFO, line);
                    System.out.println(line);
                }
            } while(!request.equals(END_COMMUNICATION + " " + END_LINE));

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
            try {
                if (in != null) in.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
            try {
                if (clientSocket != null && ! clientSocket.isClosed()) clientSocket.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
        }
    }
}
