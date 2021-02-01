package com.kuqi.mall.log.core.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 15:30
 **/
@Slf4j
public class RequestLogWrapper extends HttpServletRequestWrapper {

    /**
     * 所有header参数集合,在HttpServletRequest内部，header名称已经全部给转换小写
     */
    private final Map<String, String> headerMap;
    /**
     * 请求body
     */
    private final byte[] body;

    public RequestLogWrapper(HttpServletRequest request) {
        super(request);
        this.body = this.initBody(request);
        this.headerMap = this.initHeaderMap(request);
    }

    private byte[] initBody(HttpServletRequest request) {

        String bodyString;
        return StringUtils.isNotBlank(bodyString = this.getRequestBodyString(request))
                ? bodyString.getBytes(Charset.defaultCharset()) : new byte[]{};
    }

    private Map<String, String> initHeaderMap(HttpServletRequest request) {

        Map<String, String> headerMap = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headerMap.put(headerName, request.getHeader(headerName));
        }
        return headerMap;
    }

    public void setHeader(String header, String value) {

        if (StringUtils.isBlank(header) || StringUtils.isBlank(value)) {
            return;
        }
        this.headerMap.put(header, value);
    }

    @Override
    public String getHeader(String name) {
        return StringUtils.isNotBlank(name) ? this.getHeaderIgnoreCase(name) : null;
    }

    /**
     * header名称已经全部给转换小写，现根据源格式获取，如果获取不到再转换为小写格式
     */
    private String getHeaderIgnoreCase(String name) {
        String value;
        if (StringUtils.isNotBlank(value = this.headerMap.get(name))) {
            return value;
        }
        return this.headerMap.get(name.toLowerCase());
    }

    /**
     * 封装request id 的header
     */
    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(this.headerMap.keySet());
    }

    private String getRequestBodyString(HttpServletRequest request) {

        StringBuffer sb = new StringBuffer();
        BufferedReader bufferedReader = null;
        try {

            ServletInputStream inputStream = request.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
            String lineStr;
            while (StringUtils.isNotBlank(lineStr = bufferedReader.readLine())) {
                sb.append(lineStr);
            }
        } catch (IOException e) {

            log.error("log filter  parse request exception!", e);
            throw new RuntimeException(e);
        } finally {
            if (Objects.nonNull(bufferedReader)) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {

                    log.error("log filter  close inputStream exception!", e);
                    throw new RuntimeException(e);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取request 的json的请求参数
     */
    public String getBodyJson() {
        return ArrayUtils.isNotEmpty(body) ? new String(body, Charset.defaultCharset()) : StringUtils.EMPTY;
    }

    @Override
    public ServletInputStream getInputStream() {

        final ByteArrayInputStream inputStream = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}

