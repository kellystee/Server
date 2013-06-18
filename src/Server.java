import http.*;

import java.net.ServerSocket;
import java.util.Arrays;

public class Server {
  private int port;
  private String publicDirectory;
  private String environment;
  private WebSocketStreams streams;

  private Request request = new Request();
  private Body body = new Body();
  private Header header = new Header();

  public Server(int port, String publicdirectory) throws Exception {
    this.port = port;
    this.publicDirectory = publicDirectory;
    this.environment = "Production";

    ServerSocket theServerSocket = new ServerSocket(port);
    System.out.println("Server is running on port " + port + ".");

    if (environment == "Production")
      streams = new SystemSocketStreams(theServerSocket);
    else
      streams = new MockSocketStreams("This is some text.");

    HTTPBrowser2 theHTTPBrowser = new HTTPBrowser2(streams, publicDirectory);

    String receivedRequest = theHTTPBrowser.receiveRequest();
    request.parse(receivedRequest, publicDirectory);
    byte[] responseBody = body.get(request.baseURL, request.queryString);
    byte[] responseHeader = header.get(request.baseURL, request.httpMethod, boolean URLExists, int contentLengthOfURL)
    //send Header + Body to HTTPBrowser out command
    //get remaining parameters
  }

  public static void main(String[] args) throws Exception {
      int portIndex = Arrays.asList(args).indexOf("-p");
      Integer port = Integer.parseInt(args[portIndex+1]);

      int publicDirectoryIndex = Arrays.asList(args).indexOf("-d");
      String publicDirectory = args[publicDirectoryIndex+1];

      Server theServer = new Server(port, publicDirectory);
    }
}