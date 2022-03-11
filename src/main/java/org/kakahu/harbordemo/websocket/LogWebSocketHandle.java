package org.kakahu.harbordemo.websocket;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;


@ServerEndpoint("/log")
@Component
public class LogWebSocketHandle {

    //@Value("${wslog_commond}")千万不可打开这个注解
    private static String wslog_commond;

    @Value("${wslog_commond}")
    public void setWslog_commond(String wslog_commond) {

        LogWebSocketHandle.wslog_commond = wslog_commond;
    }

    private Process process;
    private InputStream inputStream;

    /**
     * 新的WebSocket请求开启
     */
    @OnOpen
    public void onOpen(Session session) {
        try {
            System.out.println("wslog_commond:===<>"+wslog_commond);
            process = Runtime.getRuntime().exec(wslog_commond);
            inputStream = process.getInputStream();

            // 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
            TailLogThread thread = new TailLogThread(inputStream, session);
            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * WebSocket请求关闭
     */
    @OnClose
    public void onClose() {
        try {
            if(inputStream != null)
                inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(process != null)
            process.destroy();
    }

    @OnError
    public void onError(Throwable thr) {
        thr.printStackTrace();
    }

}
