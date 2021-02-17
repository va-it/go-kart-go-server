import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import go_kart_go.Kart;

public class Server {

    public Server()
    {
        // Declare a server socket and a client socket for the server
        ServerSocket service = null;
        Socket server = null;

        // Declare an input stream and String to store message from client
        BufferedReader inputStream;
        String line;

        // Declare an output stream to client
        DataOutputStream outputStream;


        ObjectInput input = null;

        // Try to open a server socket on port 5000
        try
        {
            service = new ServerSocket(5000);
        }
        catch (IOException e)
        {
            System.out.println(e);
        }

        // Create a socket object from the ServerSocket to listen and accept
        // connections. Open input and output streams
        try
        {
            server = service.accept();

            inputStream = new BufferedReader(
                    new InputStreamReader(
                            server.getInputStream()
                    )
            );

            outputStream = new DataOutputStream(
                    server.getOutputStream()
            );

            input = new ObjectInputStream(
                    server.getInputStream()
            );

            try {
                Kart kart = (Kart)input.readObject();

                System.out.println("Kart: " + kart.getPlayer());

            } catch (ClassNotFoundException e) {
                // do something
            }

            // do {
//             if((line = inputStream.readLine()) != null)
//    			{
//    				outputStream.writeBytes(line + "\n");
//    			}
//
//             if (line.equals("CLOSE")); {
//                break;
//             }
//         } while(true);



            // Comment out/remove the outputStream and server close statements if server should remain live
            outputStream.close();
            inputStream.close();
            server.close();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }
}
