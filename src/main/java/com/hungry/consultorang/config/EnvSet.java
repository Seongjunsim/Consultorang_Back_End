package com.hungry.consultorang.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="env")
@Getter
@Setter
public class EnvSet {
    String secretKey;
}
