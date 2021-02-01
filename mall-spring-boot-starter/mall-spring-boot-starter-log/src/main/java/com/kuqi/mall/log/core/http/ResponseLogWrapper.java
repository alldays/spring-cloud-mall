package com.kuqi.mall.log.core.http;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 15:34
 **/
@Slf4j
public class ResponseLogWrapper extends HttpServletResponseWrapper {

    private final ByteArrayOutputStream buffer;

    public ResponseLogWrapper(HttpServletResponse response) throws IOException {
        super(response);
        this.buffer = new ByteArrayOutputStream();
    }

    /** 重载父类获取outputstream的方法 */
    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        return new ByteArrayOutputStreamServletOutputStream(buffer);
    }

    /** 重载父类获取writer的方法 */
    @Override
    public PrintWriter getWriter() throws UnsupportedEncodingException {
        return new PrintWriter(new OutputStreamWriter(buffer, this.getCharacterEncoding()));
    }

    @Override
    public void reset() {
        buffer.reset();
    }

    /** 将out、writer中的数据强制输出到WapperedResponse的buffer里面，否则取不到数据 */
    public byte[] getResponseData() {
        try {
            this.flushBuffer();
            return buffer.toByteArray();
        } catch (IOException e) {
            log.error("response log parse exception ",e);
            throw new RuntimeException(e);
        }
    }


    private static class ByteArrayOutputStreamServletOutputStream extends ServletOutputStream {
        private final ByteArrayOutputStream bos;

        public ByteArrayOutputStreamServletOutputStream(ByteArrayOutputStream stream) throws IOException {
            this.bos = stream;
        }

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            bos.write(b, 0, b.length);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }
    }
}

