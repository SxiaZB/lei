package com.lei.smart.im;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author dingjianlei
 */
@Component
public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private static final Logger logger = LoggerFactory.getLogger(MessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame frame)
            throws Exception {
        // 广播返回用户发送的消息文本
//        Gson gson = new Gson();
        ChatMessage message = new ChatMessage();
        message.setMessage(frame.text());
        message.setCreateDate(new Date());
        message.setVersion(1);
        UserInfoManager.baseCastMessage(message, message.getUserIdList());
        BusinessExcutorsGroup.COLLECT_LOG_EXECUTOR.submit(new Runnable() {
            @Override
            public void run() {
                try {
                } catch (Exception e) {
                } finally {
//            因为用了对象池  防止内存泄漏 不知道好不好使 SimpleChannelInboundHandler默认实现了  我也加上
//            ReferenceCountUtil.release(frame.text());/**/
                }
            }
        });
    }

    /**
     * code  message from  to
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        UserInfoManager.removeChannel(ctx.channel());
        super.channelUnregistered(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("connection error and close the channel", cause);
        UserInfoManager.removeChannel(ctx.channel());
    }


}
