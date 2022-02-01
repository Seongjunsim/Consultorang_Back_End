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
    long expireLength;
    int excelSheetNum;
    String category;
    int cnt;
    int cntPercent;
    int sale;
    int salePercent;
    int menuCost;
    int menuNm;
    String excelPath;
}
