package com.knossys.rnd.data.db;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class ByteBufferBackedOutputStream extends OutputStream {
  ByteBuffer buf;

  public ByteBufferBackedOutputStream(ByteBuffer buf) {
      this.buf = buf;
  }
  
  public ByteBufferBackedOutputStream(byte[] byteBuffer) {
    this.buf = ByteBuffer.wrap(byteBuffer);
 }

  public void write(int b) throws IOException {
      buf.put((byte) b);
  }

  public void write(byte[] bytes, int off, int len)
          throws IOException {
      buf.put(bytes, off, len);
  }
}
