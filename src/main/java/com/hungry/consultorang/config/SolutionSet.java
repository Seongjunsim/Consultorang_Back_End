package com.hungry.consultorang.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="solution")
@Data
public class SolutionSet {
    String goldTotal;
    String goldNo;
    String silverTotal;
    String silverNo;
    String bronzeTotal;
    String bronzeNo;
}
