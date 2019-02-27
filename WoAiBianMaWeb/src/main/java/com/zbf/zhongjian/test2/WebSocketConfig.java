package com.zbf.zhongjian.test2;

import org.springframework.web.socket.server.standard.ServerEndpointExporter;

public class WebSocketConfig {
    public ServerEndpointExporter getServerEndpointExporter(){
        return  new ServerEndpointExporter();
    }
}
