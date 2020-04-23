package com.playground.observability.common.translator;

import com.playground.observability.translator.TargetLanguage;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="translator")
public class TranslatorClientProperties {

    private String url;

    private String target;

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public TargetLanguage getTarget() {
        return TargetLanguage.valueOf(target);
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
