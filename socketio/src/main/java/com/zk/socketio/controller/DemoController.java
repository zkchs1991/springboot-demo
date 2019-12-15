package com.zk.socketio.controller;

import com.corundumstudio.socketio.SocketIOClient;
import com.zk.socketio.handler.MessageEventHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class DemoController {

//    /**
//     * 指定某个websocket发送从服务器发送到浏览器，服务器主动推送
//     */
//    @GetMapping(value = "/sendMsg/{mac}")
//    private String testSendMsg(@PathVariable("mac") String mac ) {
//        String msg = "指定给" + mac + "用户发消息";
//        PayWebSocket payWebSocket = PayWebSocket.payWebSocketMap.get(mac);
//        if(payWebSocket == null)
//        {
//            return new DomainResponse(0,"没有该用户",0);
//        }
//        payWebSocket.sendMessage(msg);
//        return msg;
//    }

    /**
     * web socketio方式指定用户发送消息
     * @param mac
     */
    @GetMapping(value = "/socketio/send/{mac}")
    private String socketIoTest (@PathVariable("mac") String mac){
        SocketIOClient client = MessageEventHandler.webSocketMap.get(mac);
        String msg = "指定给" + mac + "用户发消息";
        client.sendEvent("messageevent", "haha======++");
        return msg;
    }


}
