package com.playground.springboot.common.tracing;

import brave.Tracing;
import brave.baggage.BaggageField;
import brave.baggage.BaggagePropagation;
import brave.baggage.BaggagePropagationConfig;
import brave.baggage.CorrelationScopeConfig;
import brave.context.slf4j.MDCScopeDecorator;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.CurrentTraceContext;
import brave.propagation.Propagation;
import brave.propagation.ThreadLocalCurrentTraceContext;
import brave.sampler.Sampler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

@Configuration
@EnableConfigurationProperties(ZipkinConfigurationProperties.class)
public class TracingConfiguration {

    static final BaggageField TRACE_NAME = BaggageField.create("translation");

    private final ZipkinConfigurationProperties zipkinProperties;

    private final Sender sender;

    public TracingConfiguration(ZipkinConfigurationProperties zipkinProperties) {
        this.zipkinProperties = zipkinProperties;
        this.sender = OkHttpSender.create(zipkinProperties.getZipkinSenderHttpUrl());
    }

    @Bean
    Tracing tracing() {
        return Tracing
                .newBuilder()
                .sampler(Sampler.ALWAYS_SAMPLE)
                .localServiceName(zipkinProperties.getServiceName())
                .propagationFactory(propagationFactory())
                .currentTraceContext(ThreadLocalCurrentTraceContext.newBuilder()
                        .addScopeDecorator(correlationScopeDecorator())
                        .build())
                .spanReporter(spanReporter(sender))
                .build();
    }

    @Bean
    HttpTracing httpTracing(Tracing tracing) {
        return HttpTracing.create(tracing);
    }

    private Propagation.Factory propagationFactory() {
        return BaggagePropagation.newFactoryBuilder(B3Propagation.FACTORY)
                .add(BaggagePropagationConfig.SingleBaggageField.newBuilder(TRACE_NAME).addKeyName("translation").build())
                .build();
    }


    private CurrentTraceContext.ScopeDecorator correlationScopeDecorator() {
        return MDCScopeDecorator.newBuilder()
                .add(CorrelationScopeConfig.SingleCorrelationField.create(TRACE_NAME)).build();
    }

    private Sender sender() {
        return sender;
    }

    private Reporter<Span> spanReporter(Sender sender) {
        return AsyncReporter.create(sender);
    }

}
