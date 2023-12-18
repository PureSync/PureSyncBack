package com.fcc.PureSync.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:/application-secret.properties")
@Getter
public class NaverApi {
    @Value("${apiid}")
    String apiKeyID;

    @Value("${apikey}")
    String apiKey;
}
