package http;

import java.io.IOException;

public class HTTPBrowser {

  public String publicDirectory;
  public WebSocketStreams streams;

  public HTTPBrowser(WebSocketStreams streams, String publicDirectory) throws IOException {
    this.streams = streams;
  }

  public String receiveRequest() throws IOException {
    StringBuffer buffer = new StringBuffer(100);

    while (true) {
      int chr = streams.in().read();

      if (chr == -1)
        break;

      buffer.append(chr);
    }

    return buffer.toString();
  }

  public void sendResponse(byte[] content) throws IOException {
    streams.out().write(content);
    streams.out().flush();
  }

}