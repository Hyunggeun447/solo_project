package solo_project.solo_project.domain.user.security;

import static com.amazonaws.util.IOUtils.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class RequestServletWrapper extends HttpServletRequestWrapper {

  private final String requestBody;

  public RequestServletWrapper(HttpServletRequest request) throws IOException {
    super(request);

    String requestData = requestDataByte(request);
    this.requestBody = requestData;
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
        requestBody.getBytes(StandardCharsets.UTF_8));
    return new ServletInputStream() {
      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      public void setReadListener(ReadListener listener) {

      }

      @Override
      public int read() throws IOException {
        return byteArrayInputStream.read();
      }
    };
  }

  @Override
  public BufferedReader getReader() throws IOException {
    return new BufferedReader(new InputStreamReader(this.getInputStream()));
  }

  private String requestDataByte(HttpServletRequest request) throws IOException {
    byte[] rawData = new byte[128];
    InputStream inputStream = request.getInputStream();

    rawData = toByteArray(inputStream);
    return new String(rawData);
  }
}
