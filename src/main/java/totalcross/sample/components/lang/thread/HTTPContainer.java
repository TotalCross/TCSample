package totalcross.sample.components.lang.thread;

import totalcross.io.IOException;
import totalcross.net.Socket;
import totalcross.sample.util.Colors;
import totalcross.sys.Vm;
import totalcross.ui.Container;
import totalcross.ui.Label;
import totalcross.ui.MultiEdit;
import totalcross.ui.gfx.Color;

public class HTTPContainer extends Container implements Runnable, ThreadSample.SetX {
  MultiEdit me;
  Label label;

  @Override
  public void initUI() {
    setBackForeColors(Colors.S_500, Colors.ON_S_500);
    setBorderStyle(BORDER_RAISED);

    add(label = new Label(), LEFT, TOP, FILL, PREFERRED);
    add(me = new MultiEdit(0, 0), LEFT, AFTER, FILL, FILL);
    me.setEditable(false);
    MultiEdit.hasCursorWhenNotEditable = false;
    me.setBackForeColors(Colors.S_500, Colors.ON_S_500);

    Thread t = new Thread(this);
    t.setPriority(3); // priority must be set BEFORE the thread is started! - palm os never puts working threads with priority 1 if there's another one with priority 5
    t.start();
  }

  @Override
  public void setX(int x) {
    this.x = x;
  }

  @Override
  public void incX(int x) {
    this.x += x;
  }

  @Override
  public void run() {
    Socket socket = null;
    while (true) {
      try {
        me.setText("Content is Loading...");
        if (ThreadSample.paused || ThreadSample.paused0) {
          me.repaintNow();
        }
        socket = new Socket("www.google.com", 80, 25000);
        socket.readTimeout = 5000;
        String requestString = "GET /index.html HTTP/1.0\n\n";
        byte[] get = requestString.getBytes();
        socket.writeBytes(get);

        int totalCount = 0;
        int count = 0;

        byte[] buff = new byte[100];
        boolean done = false;
        StringBuffer responseBuffer = new StringBuffer(64);
        while (!done) {
          count = socket.readBytes(buff, 0, buff.length);
          String text = new String(buff, 0, count);
          responseBuffer.append(text);
          totalCount += count;
          label.setText("Read " + totalCount + " from www.google.com");
          if (ThreadSample.paused || ThreadSample.paused0) {
            label.repaintNow();
          }
          if (count < buff.length) {
            done = true;
          }
          Vm.sleep(50);
        }
        socket.close();

        String googleHTML = responseBuffer.toString();
        int index = googleHTML.indexOf('<');
        if (index >= 0) {
          googleHTML = googleHTML.substring(index);
        }

        me.setText(googleHTML);
        if (ThreadSample.paused || ThreadSample.paused0) {
          me.repaintNow();
        }
      } catch (IOException ioE) {
        me.setText("IOException - " + ioE.getMessage());
        me.repaintNow();
      }
      Vm.sleep(5000);
    }
  }
}
