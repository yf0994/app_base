/*
 * Copyright (C) 2014 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package okio;

import java.io.IOException;
import java.io.OutputStream;

final class RealBufferedSink implements BufferedSink {
  public final OkBuffer buffer;
  public final Sink sink;
  private boolean closed;

  public RealBufferedSink(Sink sink, OkBuffer buffer) {
    if (sink == null) throw new IllegalArgumentException("sink == null");
    this.buffer = buffer;
    this.sink = sink;
  }

  public RealBufferedSink(Sink sink) {
    this(sink, new OkBuffer());
  }

  @Override public OkBuffer buffer() {
    return buffer;
  }

  @Override public void write(OkBuffer source, long byteCount)
      throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.write(source, byteCount);
    emitCompleteSegments();
  }

  @Override public BufferedSink write(ByteString byteString) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.write(byteString);
    return emitCompleteSegments();
  }

  @Override public BufferedSink writeUtf8(String string) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.writeUtf8(string);
    return emitCompleteSegments();
  }

  @Override public BufferedSink write(byte[] source) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.write(source);
    return emitCompleteSegments();
  }

  @Override public BufferedSink write(byte[] source, int offset, int byteCount) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.write(source, offset, byteCount);
    return emitCompleteSegments();
  }

  @Override public BufferedSink writeByte(int b) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.writeByte(b);
    return emitCompleteSegments();
  }

  @Override public BufferedSink writeShort(int s) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.writeShort(s);
    return emitCompleteSegments();
  }

  @Override public BufferedSink writeShortLe(int s) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.writeShortLe(s);
    return emitCompleteSegments();
  }

  @Override public BufferedSink writeInt(int i) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.writeInt(i);
    return emitCompleteSegments();
  }

  @Override public BufferedSink writeIntLe(int i) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.writeIntLe(i);
    return emitCompleteSegments();
  }

  @Override public BufferedSink writeLong(long v) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.writeLong(v);
    return emitCompleteSegments();
  }

  @Override public BufferedSink writeLongLe(long v) throws IOException {
    if (closed) throw new IllegalStateException("closed");
    buffer.writeLongLe(v);
    return emitCompleteSegments();
  }

  @Override public BufferedSink emitCompleteSegments() throws IOException {
    if (closed) throw new IllegalStateException("closed");
    long byteCount = buffer.completeSegmentByteCount();
    if (byteCount > 0) sink.write(buffer, byteCount);
    return this;
  }

  @Override public OutputStream outputStream() {
    return new OutputStream() {
      @Override public void write(int b) throws IOException {
        if (closed) throw new IOException("closed");
        buffer.writeByte((byte) b);
        emitCompleteSegments();
      }

      @Override public void write(byte[] data, int offset, int byteCount) throws IOException {
        if (closed) throw new IOException("closed");
        buffer.write(data, offset, byteCount);
        emitCompleteSegments();
      }

      @Override public void flush() throws IOException {
        // For backwards compatibility, a flush() on a closed stream is a no-op.
        if (!closed) {
          RealBufferedSink.this.flush();
        }
      }

      @Override public void close() throws IOException {
        RealBufferedSink.this.close();
      }

      @Override public String toString() {
        return RealBufferedSink.this + ".outputStream()";
      }
    };
  }

  @Override public void flush() throws IOException {
    if (closed) throw new IllegalStateException("closed");
    if (buffer.size > 0) {
      sink.write(buffer, buffer.size);
    }
    sink.flush();
  }

  @Override public void close() throws IOException {
    if (closed) return;

    // Emit buffered data to the underlying sink. If this fails, we still need
    // to close the sink; otherwise we risk leaking resources.
    Throwable thrown = null;
    try {
      if (buffer.size > 0) {
        sink.write(buffer, buffer.size);
      }
    } catch (Throwable e) {
      thrown = e;
    }

    try {
      sink.close();
    } catch (Throwable e) {
      if (thrown == null) thrown = e;
    }
    closed = true;

    if (thrown != null) Util.sneakyRethrow(thrown);
  }

  @Override public Sink deadline(Deadline deadline) {
    sink.deadline(deadline);
    return this;
  }

  @Override public String toString() {
    return "buffer(" + sink + ")";
  }
}
