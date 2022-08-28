package com.sbosoft.scrap.configurations;

import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public static final String MAGIC_URL = "https://gatherer.wizards.com";
    public static final String MAGIC_DETAILS_URL = MAGIC_URL + "/pages/card/Details.aspx";
}
