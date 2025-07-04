package com.foresys.app2.fulltext.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.*;

@Slf4j
@Component
public class SocketComponent {

    public Socket connect(String ip, int port, String proxyIp, int proxyPort, int timeout) throws IOException {
        SocketAddress proxyAddress = null;
        if(proxyIp != null) {
            proxyAddress = new InetSocketAddress(proxyIp, proxyPort);
            log.info("proxy ip :: {} / proxy port :: {}", proxyIp, proxyPort);
        }

        Socket socket = new Socket();
        if(proxyAddress != null) {
            Proxy proxy = new Proxy(Proxy.Type.SOCKS, proxyAddress);
            socket = new Socket(proxy);
        }

        InetAddress address = InetAddress.getByName(ip);
        SocketAddress socketAddress = new InetSocketAddress(address, port);

        log.info("address ip :: {} / address port :: {}", address, port);

        socket.setSoTimeout(timeout);
        socket.connect(socketAddress);

        if(socket.isConnected())
            log.info("Connected![{}:{}]", ip, port);
        else
            log.info("Connection refused!");

        return socket;
    }

    public byte[] sendReceive(Socket socket, byte[] message, int prefixFieldLen, int totLenFieldLen, int exceptLen, String charSet) throws IOException {
        try(
                socket;
                OutputStream os = socket.getOutputStream();
                DataInputStream dis = new DataInputStream(socket.getInputStream())
        ) {
            this.send(os, message);
            return this.receive(dis, prefixFieldLen, totLenFieldLen, exceptLen, charSet);
        }
    }

    public void send(OutputStream os, byte[] message) throws IOException {
        int len = message == null ? 0 : message.length;
        if (len > 0) {
            os.write(message);
            os.flush();
        } else {
            throw new IllegalArgumentException("0 byte");
        }
    }

    public byte[] receive(DataInputStream dis, int prefixFieldLen, int totLenFieldLen, int exceptLen, String charset) throws IOException {
        byte[] sizeBytes = new byte[totLenFieldLen];
        byte[] initialBytes = new byte[prefixFieldLen + totLenFieldLen];
        dis.readFully(initialBytes);

        System.arraycopy(initialBytes, prefixFieldLen, sizeBytes, 0, sizeBytes.length);

        String totalStrSize = new String(sizeBytes, charset).replaceAll("[^\\d]", "");
        int total = Integer.parseInt(totalStrSize) + exceptLen;
        int remSize = total - initialBytes.length;

        log.debug("[Socket]first readed length= {}", initialBytes.length);
        log.debug("[Socket]totalStrSize= {}", totalStrSize);
        log.debug("[Socket]total size= {}", total);
        log.debug("[Socket]remSize= {}", remSize);

        byte[] res = new byte[total];
        byte[] buf = new byte[remSize];
        dis.readFully(buf);
        System.arraycopy(initialBytes, 0, res, 0, initialBytes.length);
        System.arraycopy(buf, 0, res, initialBytes.length, buf.length);

        return res;
    }

}
