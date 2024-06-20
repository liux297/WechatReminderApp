package com.xin.main;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/wechat/mp")
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 微信公众平台接入验证.
     */
    @GetMapping(value = "/config")
    public String config(@RequestParam(name = "signature", required = false) String signature,
                         @RequestParam(name = "timestamp", required = false) String timestamp,
                         @RequestParam(name = "nonce", required = false) String nonce,
                         @RequestParam(name = "echostr", required = false) String echostr) {
        if (wxMpService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }
        return "非法请求";
    }

    /**
     * 处理微信消息推送.
     */
    @PostMapping(value = "/message")
    public void message(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
//        WxMpXmlOutMessage outMessage = wxMpService.execute(inMessage);
//        if (outMessage != null) {
//            response.setContentType("text/xml;charset=utf-8");
//            response.getWriter().write(outMessage.toXml());
//        }
    }
}
