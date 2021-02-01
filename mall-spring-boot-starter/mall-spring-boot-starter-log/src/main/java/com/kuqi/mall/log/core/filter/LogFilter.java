package com.kuqi.mall.log.core.filter;

import com.google.common.collect.Sets;
import com.kuqi.mall.log.core.entity.HttpLog;
import com.kuqi.mall.log.core.http.RequestLogWrapper;
import com.kuqi.mall.log.core.http.ResponseLogWrapper;
import com.kuqi.mall.log.core.utils.DateUtils;
import com.kuqi.mall.log.core.utils.HttpLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * @Author iloveoverfly
 * @Date 2021/1/27 15:39
 **/
@Slf4j
public class LogFilter implements Filter {

    private static final Set<String> LOCAL_HOST_SET = Sets.newHashSet("127.0.0.1", "localhost");
    public final static String FILE_CONTENT_TYPE = "multipart/form-data";
    public static final String TRACE_ID = "traceId";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (this.isLocalhostRequest(request.getLocalAddr())
                || this.isFile(request.getContentType())) {

            chain.doFilter(request, response);
            return;
        }

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        RequestLogWrapper requestLogWrapper = new RequestLogWrapper(httpServletRequest);
        ResponseLogWrapper responseLogWrapper = new ResponseLogWrapper(httpServletResponse);
        // 在header中定义全局的请求id
        String requestId;
        if (StringUtils.isBlank(requestId = requestLogWrapper.getHeader(TRACE_ID))) {

            requestId = UUID.randomUUID().toString();
            // 重新设置requestId
            requestLogWrapper.setHeader(TRACE_ID, requestId);
        }

        // 定义服务执行开始结束时间
        LocalDateTime startAt = LocalDateTime.now();
        chain.doFilter(requestLogWrapper, responseLogWrapper);
        LocalDateTime finishAt = LocalDateTime.now();

        HttpLog httpLog = HttpLogUtil.obtainResponseLog(requestLogWrapper, responseLogWrapper, startAt, finishAt, requestId);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(responseLogWrapper.getResponseData());
        outputStream.flush();
        outputStream.close();

        // 打印response
        log.info("----------------------http log -------------------------");
        String requestParams;
        String headers;
        log.info("requestId: [{}],methodName: [{}],url: [{}],startAt: [{}],finishAt: [{}],duration: [{}ms],status: [{}],headers:[{}],request: [{}],response: [{}]",
                httpLog.getTraceId(),
                httpLog.getMethodName(),
                httpLog.getUrl(),
                DateUtils.formatDateTime(httpLog.getStartAt()),
                DateUtils.formatDateTime(httpLog.getFinishAt()),
                httpLog.getDuration(),
                httpLog.getStatus(),
                StringUtils.isNotBlank(headers = httpLog.getHeaders()) ? headers : StringUtils.EMPTY,
                StringUtils.isNotBlank(requestParams = httpLog.getRequest()) ? requestParams : StringUtils.EMPTY,
                HttpLogUtil.subStringIfRequired(httpLog.getResponse()));
    }

    private boolean isLocalhostRequest(String localAddr) {

        for (String localHost : LOCAL_HOST_SET) {
            if (localHost.equalsIgnoreCase(localAddr)) {
                return true;
            }
        }
        return false;
    }

    private boolean isFile(String contentType) {
        if (StringUtils.isNotEmpty(contentType) && contentType.contains(FILE_CONTENT_TYPE)) {
            return true;
        }
        return false;
    }

    @Override
    public void destroy() {
    }
}

