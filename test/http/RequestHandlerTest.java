package http;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;

@RunWith(JUnit4.class)
  public class RequestHandlerTest {

  private WebServerSocket theServerSocket;
  private RequestHandler requestHandler;
  private String NEWLINE = "\r\n";

  @After
  public void tearDown() throws IOException {
    theServerSocket.closeConnection();
  }

  @Test
  public void testSingleLineGet() throws IOException {
    String request = "GET /donaldduck.html HTTP/1.1\r\n";
    theServerSocket = new MockServerSocket(request + NEWLINE);
    requestHandler = new RequestHandler(theServerSocket);
    theServerSocket.connect();
    assertEquals(request + NEWLINE, requestHandler.receive());
  }


//  @Test
//  public void testSingleLineGetWithNullBufferedReader() throws IOException {
//    String request = "GET /donaldduck.html HTTP/1.1\r\n";
//    StringReader reader = new StringReader(request);
//
//    theServerSocket = new MockServerSocket(request + NEWLINE);
//    ((MockServerSocket)theServerSocket).in = new NullBufferedReader(new BufferedReader(reader));
//    requestHandler = new RequestHandler(theServerSocket);
//
//    assertEquals("", requestHandler.receive());
//  }

  @Test
  public void testMultiLineGet() throws IOException {
    String request =   "GET /color_picker.html HTTP/1.1\r\n"
                     + "Host: localhost:5000\r\n"
                     + "Connection: keep-alive\r\n"
                     + "Cache-Control: max-age=0\r\n"
                     + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
                     + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
                     + "Accept-Encoding: gzip,deflate,sdch\r\n"
                     + "Accept-Language: en-US,en;q=0.8\r\n"
                     + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    theServerSocket = new MockServerSocket(request + NEWLINE);
    requestHandler = new RequestHandler(theServerSocket);
    theServerSocket.connect();
    assertEquals(request + NEWLINE, requestHandler.receive());
  }

  @Test
  public void testPost() throws IOException {
    String requestHeader =   "POST /color_picker_post.html HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n"
        + "Connection: keep-alive\r\n"
        + "Content-Length: 15\r\n"
        + "Cache-Control: max-age=0\r\n"
        + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
        + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
        + "Accept-Encoding: gzip,deflate,sdch\r\n"
        + "Accept-Language: en-US,en;q=0.8\r\n"
        + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody     = "text_color=blue\r\n";
    theServerSocket = new MockServerSocket(requestHeader + NEWLINE + requestBody);
    requestHandler = new RequestHandler(theServerSocket);
    theServerSocket.connect();
    requestHandler.receive();
    assertEquals(requestHeader + NEWLINE + requestBody, requestHandler.receivedRequest);
  }

  @Test
  public void testPostWithLessLinesInHeader() throws IOException {
    String requestHeader =   "POST /color_picker_post.html HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n"
        + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n";
    String requestBody     = "text_color=blue\r\n";
    theServerSocket = new MockServerSocket(requestHeader + NEWLINE + requestBody);
    requestHandler = new RequestHandler(theServerSocket);
    theServerSocket.connect();
    requestHandler.receive();
    assertEquals(requestHeader + NEWLINE + requestBody, requestHandler.receivedRequest);
  }

  @Test
  public void testPut() throws IOException {
    String requestHeader =   "PUT /color_picker_post.html HTTP/1.1\r\n"
        + "Host: localhost:5000\r\n"
        + "Connection: keep-alive\r\n"
        + "Content-Length: 15\r\n"
        + "Cache-Control: max-age=0\r\n"
        + "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n"
        + "User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/27.0.1453.116 Safari/537.36\r\n"
        + "Accept-Encoding: gzip,deflate,sdch\r\n"
        + "Accept-Language: en-US,en;q=0.8\r\n"
        + "Cookie: textwrapon=false; wysiwyg=textarea\r\n";
    String requestBody     = "text_color=blue\r\n";
    theServerSocket = new MockServerSocket(requestHeader + NEWLINE + requestBody);
    requestHandler = new RequestHandler(theServerSocket);
    theServerSocket.connect();
    requestHandler.receive();
    assertEquals(requestHeader + NEWLINE + requestBody, requestHandler.receivedRequest);
  }

  @Test
  public void testSendResponse() throws IOException {
    theServerSocket = new MockServerSocket("This is the header.\r\n\r\n");
    requestHandler = new RequestHandler(theServerSocket);
    theServerSocket.connect();

    String expectedResponse = "This is the response.\r\n";
    byte[] expectedResponseInBytes = expectedResponse.getBytes();
    requestHandler.sendResponse(expectedResponseInBytes);
    String actualResponse = theServerSocket.out().toString();
    assertEquals(expectedResponse, actualResponse);
  }

//  @Test
//  public void test() {
//    int var = 3;
//    while ((var += 1) < 7 && var < 6) {
//      System.out.println("Hi");
//    }
//  }
}