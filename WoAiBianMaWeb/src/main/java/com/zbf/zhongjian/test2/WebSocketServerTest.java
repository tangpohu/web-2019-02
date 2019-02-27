package com.zbf.zhongjian.test2;

import com.zbf.utils.SpringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
@ServerEndpoint("/webSocketTest/sendMessage/{userid}")
public class WebSocketServerTest {

    private static RedisTemplate redisTemplate= SpringUtil.getBean("redisTemplate",RedisTemplate.class);

    public static Map<String,Session> mapsession= new HashMap<String,Session>();

    private static Log logger=LogFactory.getLog(WebSocketServerTest.class);

    //建立连接
    @OnOpen
    public void onOpen(Session session, @PathParam("userid")String userid){
        logger.info("———建立连接———");
        //存储session
        mapsession.put ( userid, session);
        //检测离线消息
        List<String> range = redisTemplate.opsForList ().range ( "userid_" + userid, 0, -1 );
        range.forEach ( (msg)->{
            WebSocketServerTest.sendMessageFromServer ( session,msg,userid);
            //删除消息
            redisTemplate.opsForList ().remove ( "userid_" + userid,1, msg);
        } );

    }

    @OnMessage
    public void OnMessage  (Session session,String message,@PathParam("userid") String userid){
        logger.info("———onMessage 服务端接受的的客户端的消息———"+message);
    }

    //当通道连接关闭时触发该方法
    @OnClose
    public void OnClose (Session session,String message,@PathParam("userid")String userid){
        logger.info("———onClose 连接通道关闭———");
        //清除session
        mapsession.remove(userid);
    }

    //通道发生错误时
    @OnError
    public void  onError(Session session,Throwable throwable,@PathParam("userid")String uuserid){

    }

    //服务端推送消息
    public  static void sendMessageFromServer(Session session,String message,String userid){
        try {
            session.getAsyncRemote().setSendTimeout(2000);
            session.getAsyncRemote().sendText(message);
        }catch (Exception e){
            e.printStackTrace();
            redisTemplate.expire("userid"+userid,100, TimeUnit.SECONDS);
            redisTemplate.opsForList().leftPush("userid"+userid,message);
        }
    }

}
