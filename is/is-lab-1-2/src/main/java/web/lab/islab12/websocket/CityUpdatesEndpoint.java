package web.lab.islab12.websocket;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/city-updates")
public class CityUpdatesEndpoint {
    private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();

    @OnOpen
    public void onOpen(Session s) {
        sessions.add(s);
    }

    @OnClose
    public void onClose(Session s) {
        sessions.remove(s);
    }

    public static void broadcast() {
        sessions.forEach(s -> {
            try {
                s.getBasicRemote().sendText("update");
            } catch (IOException ignored) {
            }
        });
    }
}

