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

/**
 * A sink that keeps a buffer internally so that callers can do small writes
 * without a performance penalty.
 */
public interface BufferedSink extends Sink {
  /** Returns this sink's internal buffer. */
  OkBuffer buffer();

  BufferedSink write(ByteString byteString) throws IOException;

  /**
   * Like {@link OutputStream#write}, this writes a complete byte array to this
   * sink.
   */
  BufferedSink write(byte[] source) throws IOException;

  /**
   * Like {@link OutputStream#write}, this writes {@code byteCount} bytes
   * of {@code source}, starting at {@code offset}.
   */
  BufferedSink write(byte[] source, int offset, int byteCount) throws IOException;

  /** Encodes {@code string} in UTF-8 and writes it to this sink. */
  BufferedSink writeUtf8(String string) throws IOException;

  /** Writes a byte to this sink. */
  BufferedSink writeByte(int b) throws IOException;

  /** Writes a big-endian short to this sink using two bytes. */
  BufferedSink writeShort(int s) throws IOException;

  /** Writes a little-endian short to this sink using two bytes. */
  BufferedSink writeShortLe(int s) throws IOException;

  /** Writes a big-endian int to this sink using four bytes. */
  BufferedSink writeInt(int i) throws IOException;

  /** Writes a little-endian int to this sink using four bytes. */
  BufferedSink writeIntLe(int i) throws IOException;

  /** Writes a big-endian long to this sink using eight bytes. */
  BufferedSink writeLong(long v) throws IOException;

  /** Writes a little-endian long to this sink using eight bytes. */
  BufferedSink writeLongLe(long v) throws IOException;

  /** Writes complete segments to this sink. Like {@link #flush}, but weaker. */
  BufferedSink emitCompleteSegments() throws IOException;

  /** Returns an output stream that writes to this sink. */
  OutputStream outputStream();
}
