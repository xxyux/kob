package com.kob.backend.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kob.backend.consumer.utils.Game;
import com.kob.backend.consumer.utils.JwtAuthentication;
import com.kob.backend.mapper.UserMapper;
import com.kob.backend.pojo.User;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{token}")  // 注意不要以'/'结尾
public class WebSocketServer {
    //每个实例都可见，公有的
    private static ConcurrentHashMap<Integer, WebSocketServer> users = new ConcurrentHashMap<>();
    private static UserMapper userMapper;
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        WebSocketServer.userMapper=userMapper;
    }
    private static final CopyOnWriteArraySet<User> matchpool = new CopyOnWriteArraySet<>();
    //每个实例不同
    private User user;
    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("token") String token) throws IOException {
        // 前端向后端建立连接
        this.session=session;
        System.out.println("Connected!");
        Integer userId= JwtAuthentication.getUserId(token);
        this.user=userMapper.selectById(userId);

        if(this.user != null) {
            users.put(userId, this);
        }
        else {
            this.session.close();
        }
        //System.out.println(users);
    }

    @OnClose
    public void onClose() {
        // 前端向后端关闭连接
        System.out.println("Connect closed!");
        if(user != null) {
            users.remove(this.user.getId());
            matchpool.remove(this.user);
        }
    }

    private void startMatching() {
        System.out.println("start matching!");
        matchpool.add(this.user);

        while (matchpool.size()>=2) {
            Iterator<User> iterator = matchpool.iterator();
            User a=iterator.next(),b=iterator.next();
            matchpool.remove(a);
            matchpool.remove(b);

            Game game = new Game(13,14,20);
            game.createMap();

            JSONObject respA = new JSONObject();
            respA.put("event","start matching");
            respA.put("opponent_username",b.getUsername());
            respA.put("opponent_photo",b.getPhoto());
            respA.put("gamemap",game.getG());
            users.get(a.getId()).sendMessage(respA.toJSONString());

            JSONObject respB = new JSONObject();
            respB.put("event","start matching");
            respB.put("opponent_username",a.getUsername());
            respB.put("opponent_photo",a.getPhoto());
            respB.put("gamemap",game.getG());
            users.get(b.getId()).sendMessage(respB.toJSONString());

        }
    }
    private void stopMatching() {
        System.out.println("stop matching!");
        matchpool.remove(this.user);
    }
    @OnMessage
    public void onMessage(String message, Session session) {
        // 后端从Client接收消息，路由
        System.out.println("received!");
        JSONObject jsonObject = JSONObject.parseObject(message);
        String event = jsonObject.getString("event");
        if("start-matching".equals(event)) {
            startMatching();
        }
        else if("stop-matching".equals(event)){
            stopMatching();
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public void sendMessage(String message) {
        //后端向前端发送信息
        synchronized (this.session) {
            try{
                this.session.getBasicRemote().sendText(message);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
