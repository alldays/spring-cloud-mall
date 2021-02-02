package com.kuqi.mall.gateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;

/**
 * @Author iloveoverfly
 * @Date 2021/2/2 14:27
 **/
@Configuration
public class GatewayConfig implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

    @Value("${server.max-initial-line-length:10485760}")
    private int maxInitialLingLength;

    @Override
    public void customize(NettyReactiveWebServerFactory container) {
        container.addServerCustomizers(
                httpServer -> httpServer.httpRequestDecoder(
                        httpRequestDecoderSpec -> {

                            httpRequestDecoderSpec.maxHeaderSize(maxInitialLingLength);
                            httpRequestDecoderSpec.maxInitialLineLength(maxInitialLingLength);
                            httpRequestDecoderSpec.maxChunkSize(maxInitialLingLength);
                            return httpRequestDecoderSpec;
                        }
                )
        );
    }
}

