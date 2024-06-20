package com.xin.main;

import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WechatMpConfig {

    @Value("${weixin.mp.app-id}")
    private String appId;

    @Value("${weixin.mp.secret}")
    private String secret;

    @Value("${weixin.mp.token}")
    private String token;

    @Value("${weixin.mp.aes-key}")
    private String aesKey;

    @Bean
    public WxMpService wxMpService() {
        WxMpInMemoryConfigStorage configStorage = new WxMpInMemoryConfigStorage();
        configStorage.setAppId(appId);
        configStorage.setSecret(secret);
        configStorage.setToken(token);
        configStorage.setAesKey(aesKey);

        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        return wxMpService;
    }
}
