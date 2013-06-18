package http;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SystemSocketStreams implements WebSocketStreams {
  public ServerSocket theServerSocket;
  public Socket theConnection;
  private InputStream in;
  private OutputStream out;

  public SystemSocketStreams(ServerSocket theServerSocket) throws IOException {
    this.theServerSocket = theServerSocket;
    in = new BufferedInputStream(theConnection.getInputStream());
    out = new BufferedOutputStream(theConnection.getOutputStream());
  }

  public Socket listen() throws IOException {
    return theServerSocket.accept();
  }

  @Override
  public InputStream in() throws IOException {
    return in;
  }

  @Override
  public OutputStream out() throws IOException {
    return out;
  }
}
