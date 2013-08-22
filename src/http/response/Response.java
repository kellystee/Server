package http.response;

import http.response.routeType.RouteType;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Response {
  public void send(OutputStream out, HashMap request, ArrayList routeInfo) throws IOException {
    byte[] response = build(request, routeInfo);
    out.write(response);
    out.flush();
  }

  public byte[] build(HashMap request, ArrayList routeInfo) throws IOException {
    File routeFile = (File)routeInfo.get(0);
    RouteType routeType = (RouteType)routeInfo.get(1);
    return routeType.get(routeFile, request);
  }
}
