package com.bisa.cam.utils.spring.io;


import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicInteger;

public class CachedRequestWrapper extends HttpServletRequestWrapper {
    //    private final byte[] cachedRequestPayload;
    private final FastByteArrayOutputStream cachedInputStreamContent;

    public CachedRequestWrapper(HttpServletRequest request) throws IOException {
        //So that other request method behave just like before
        super(request);

        this.cachedInputStreamContent = new FastByteArrayOutputStream();

        request.getInputStream().transferTo(cachedInputStreamContent);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        return new RequestServletInputStream(cachedInputStreamContent.toByteArray());

        // ye old versions
//        ServletInputStream servletInputStream = new ServletInputStream() {
//            public int read() throws IOException {
//                return byteArrayInputStream.read();
//            }
//        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    /**
     * @return if the contents are empty, an empty byte array is returned
     */
    public byte[] getContentAsByteArray() {
        return cachedInputStreamContent.toByteArray();
    }

    /**
     * @return
     */
    public InputStream getContentAsInputStream() {
        return cachedInputStreamContent.getInputStream();
    }

    private class RequestServletInputStream extends ServletInputStream {

        final byte[] cachedRequestPayload;

        private final AtomicInteger lastIndexRetrieved = new AtomicInteger(0);

        private ReadListener readListener = null;

        private RequestServletInputStream(byte[] cachedRequestPayload) {
//            this.is = is;
            this.cachedRequestPayload = cachedRequestPayload;
        }

        @Override
        public boolean isFinished() {
            boolean finished = lastIndexRetrieved.get() > cachedRequestPayload.length - 1;

            if (finished && readListener != null) {
                try {
                    readListener.onAllDataRead();
                } catch (IOException ex) {
                    readListener.onError(ex);
                } finally {
                    readListener = null;
                }
            }

            return finished;
        }

        @Override
        public boolean isReady() {
            // This implementation will never block
            // We also never need to call the readListener from this method, as this method will never return false
            return isFinished();
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            this.readListener = readListener;

            if (!isFinished() && readListener != null) {
                try {
                    readListener.onDataAvailable();
                } catch (IOException e) {
                    readListener.onError(e);
                }
            }
        }

        @Override
        public int read() throws IOException {
            if (!isFinished()) {
                return cachedRequestPayload[lastIndexRetrieved.getAndIncrement()];
            } else {
                //this means that the read has reached the end
                return -1;
            }
        }
    }
}