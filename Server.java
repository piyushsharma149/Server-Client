// A Java program for a Server
import java.net.*;
import java.io.*;

public class Server
{
	//initialize socket and input stream

	private ServerSocket server = null;
	private static Socket socket = null;

	// constructor with port
	public Server(int port)
	{
		// starts server and waits for a connection
		try
		{
			server = new ServerSocket(port);
			System.out.println("Server started");

			System.out.println("Waiting for a client ...");

			while(true){
				socket = server.accept();
				

				ClientHandler newClient = new ClientHandler (socket);

				Thread t = new Thread (newClient);
				newClient.start();

				System.out.println("Client accepted");
			}
			

		}
		catch(IOException i)
		{
			System.out.println(i);
		}
	}

	public static class ClientHandler extends Thread{
		

		private Socket clientSocket = null;
		private static DataInputStream in	 = null;

		public ClientHandler(Socket socket)
		{
			this.clientSocket = socket;
		}

		public void run() {
					

			// takes input from the client socket
					try
					{
					in = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
			
					String line = "";
			
						// reads message from client until "Over" is sent
						while (!line.equals("Over"))
						{
							try
							{
								line = in.readUTF();
								System.out.println(line);
			
							}
							catch(IOException i)
							{
								System.out.println(i);
							}
						}
						System.out.println("Closing connection");
			
						// close connection
						clientSocket.close();
						in.close();
					}catch(IOException i){
						System.out.println(i);
					}
		}
	}





	public static void main(String args[])
	{
		Server server = new Server(5020);
	}
}
