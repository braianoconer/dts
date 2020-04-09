package com.playground.springboot.common.tracing;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="zipkin")
public class ZipkinConfigurationProperties {

    @Value("${zipkin.sender.http.url}")
    private String zipkinSenderHttpUrl;

    @Value("${zipkin.service.name}")
    private String serviceName;

    public String getZipkinSenderHttpUrl() {
        return zipkinSenderHttpUrl;
    }

    public void setZipkinSenderHttpUrl(String zipkinSenderHttpUrl) {
        this.zipkinSenderHttpUrl = zipkinSenderHttpUrl;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
