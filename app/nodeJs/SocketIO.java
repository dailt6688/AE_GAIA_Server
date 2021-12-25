package nodeJs;

import helper.S;
import io.socket.client.IO;
import io.socket.client.Socket;

import java.net.URISyntaxException;

public class SocketIO {
    private Socket mSocket;

    {//<=> Constructor => note by Hai
        connectServer();
    }

    public void connectServer() {
        try {
            mSocket = IO.socket(S.URL_NODE_JS_HTTP);
            mSocket.connect();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public Socket getSocket() {
        return mSocket;
    }
}
