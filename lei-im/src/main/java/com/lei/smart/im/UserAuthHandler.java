package com.lei.smart.im;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author dingjianlei
 */
@Component
public class UserAuthHandler extends SimpleChannelInboundHandler<Object> {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthHandler.class);

    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
//            BusinessExcutorsGroup.COLLECT_LOG_EXECUTOR.submit(new Runnable() {
//                @Override
//                public void run() {
//                    try {
                        handleHttpRequest(ctx, (FullHttpRequest) msg);
//                    } catch (Exception e) {
//                        logger.error("************handleHttpRequest happen error***********");
//                        e.printStackTrace();
//                    }
//                }
//            });
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocket(ctx, (WebSocketFrame) msg);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent evnet = (IdleStateEvent) evt;
            // 判断Channel是否读空闲, 读空闲时移除Channel
            if (evnet.state().equals(IdleState.READER_IDLE)) {
                final String remoteAddress = NettyUtil.parseChannelRemoteAddr(ctx.channel());
                logger.warn("NETTY SERVER PIPELINE: IDLE exception [{}]", remoteAddress);
                UserInfoManager.removeChannel(ctx.channel());
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        if (!request.decoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))) {
            logger.warn("protobuf don't support websocket");
            ctx.channel().close();
            return;
        }
        WebSocketServerHandshakerFactory handshakerFactory = new WebSocketServerHandshakerFactory(
                Constants.WEBSOCKET_URL, null, true);
        handshaker = handshakerFactory.newHandshaker(request);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {

            // 动态加入websocket的编解码处理
            handshaker.handshake(ctx.channel(), request);
            Map<String, String> parse = RequestParser.parse(request);
            ChatUser chatUser = checkUserInfo(parse, ctx.channel());
                // 存储已经连接的Channel
            if(chatUser!=null)
            UserInfoManager.addChannel(ctx.channel(), chatUser);


        }
    }

    private static AtomicLong count=new AtomicLong(1);
    private ChatUser checkUserInfo(Map<String, String> parse, Channel channel) {
        ChatUser chatUser=null;
        String token = parse.get("token");
        String userId = parse.get("userId");
        String version = parse.get("version");
        String terminal = parse.get("terminal");
//        try {
////            Thread.sleep(8000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        ChatUserService chatUserService = SpringApplicationContextUtil.getBean(ChatUserService.class);
//        chatUser = chatUserService.getChatUserById(Long.parseLong(userId));
        chatUser=new ChatUser();
//        chatUser=new ChatUser();
//        chatUser.setId(Long.parseLong(userId));

        chatUser.setId(Long.parseLong(userId));

        if(chatUser==null){
            channel.close();
        }

//        try {
//            if(!StringUtils.equals(md5(userId),token)){
//                channel.close();
//                return null;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        //加密延签
        return chatUser;
    }

    public  String md5(String text) throws Exception {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text);
        return encodeStr;
    }

    private void handleWebSocket(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路命令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            UserInfoManager.removeChannel(ctx.channel());
            return;
        }
        // 判断是否Ping消息
        if (frame instanceof PingWebSocketFrame) {
            logger.info("ping message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 判断是否Pong消息
        if (frame instanceof PongWebSocketFrame) {
            logger.info("pong message:{}", frame.content().retain());
            ctx.writeAndFlush(new PongWebSocketFrame(frame.content().retain()));
            return;
        }

        // 本程序目前只支持文本消息
        if (!(frame instanceof TextWebSocketFrame)) {
            throw new UnsupportedOperationException(frame.getClass().getName() + " frame type not supported");
        }
        String message = ((TextWebSocketFrame) frame).text();
        JSONObject json = JSONObject.parseObject(message);
        int code = json.getInteger("chatCode");

        String messageInfo = json.getString("message");
//        int code = 10086;

        Channel channel = ctx.channel();
        switch (code) {
            case ChatCode.PING_CODE:
            case ChatCode.PONG_CODE:
                logger.info("receive pong message, address: {}", NettyUtil.parseChannelRemoteAddr(channel));
                return;
            case ChatCode.AUTH_CODE:
//                ChatMessage chatMessage =new ChatMessage();
//                UserInfoManager.sendInfo(channel, ChatCode.SYS_AUTH_STATE, chatMessage);
                return;
            case ChatCode.SUPER_ADMIN:
                Set<String> allUserId = UserInfoManager.getAllUserId();
                List<String> result = new ArrayList<>(allUserId);
                ChatMessage chatMessage  = new ChatMessage();
                UserInfoManager.baseCastMessage(chatMessage,result);
                return;
            case ChatCode.MESS_CODE: //普通的消息留给MessageHandler处理
                break;
            default:
                logger.warn("The code [{}] can't be auth!!!", code);
                return;
        }
        //后续消息交给MessageHandler处理
        ctx.fireChannelRead(frame.retain());
    }
}
