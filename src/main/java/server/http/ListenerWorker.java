package server.http;

import static java.util.logging.Level.SEVERE;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Logger;

class ListenerWorker {
    private static final Logger LOGGER = Logger.getLogger(ListenerWorker.class.getName());

    private static final int SOCKET_TIMEOUT = 1000;

    private final ServerSocket serverSocket;

    private final RequestHandlerFactory requestHandlerFactory;

    ListenerWorker(ServerSocket serverSocket, RequestHandlerFactory requestHandlerFactory) {
        this.serverSocket = serverSocket;
        this.requestHandlerFactory = requestHandlerFactory;
    }

    void listen() {
        try {
            serverSocket.setSoTimeout(SOCKET_TIMEOUT);

            while (true) {
                final Socket socket;

                try {
                    socket = serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    if (Thread.currentThread().isInterrupted()) {
                        throw new InterruptedException();
                    } else {
                        continue;
                    }
                }

                LOGGER.info("Received a new connection.");

                final RequestHandler handler = requestHandlerFactory.handling(socket);

                handler.handle();
            }
        } catch (Exception e) {
            LOGGER.log(SEVERE, "Exception encountered in listener loop!", e);
        }
    }
}
