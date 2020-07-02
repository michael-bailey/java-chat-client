package io.github.michael_bailey.client.managers.NetworkModules;

import io.github.michael_bailey.client.Delegates.Interfaces.IPTPModuleDelegate;
import io.github.michael_bailey.client.models.Account;
import io.github.michael_bailey.java_server.Protocol.Command;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.UUID;

import static io.github.michael_bailey.java_server.Protocol.Command.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PTPModuleTest implements IPTPModuleDelegate {

    public static PTPModule ptpModule;

    @BeforeClass
    public static void setup() {

        var account = new Account();
        account.displayName = "hello world";
        account.uuid = UUID.randomUUID();
        account.privateKey = null;
        account.publicKey = null;

        ptpModule = new PTPModule(account);
        assertTrue(ptpModule.startIncoming());
    }

    @Test
    public void receivingTestTest() throws IOException {

        var a = new Socket("127.0.0.1", 6001);
        var in = new DataInputStream(a.getInputStream());
        var out = new DataOutputStream(a.getOutputStream());

        assertEquals(REQUEST, Command.valueOf(in.readUTF()).toString());
        out.writeUTF(new Command(Command.TEST).toString());
        assertEquals(Command.SUCCESS, Command.valueOf(in.readUTF()).toString());
    }

    @Test
    public void receivingMessage() throws IOException {

        var a = new Socket("127.0.0.1", 6001);
        var in = new DataInputStream(a.getInputStream());
        var out = new DataOutputStream(a.getOutputStream());

        assertEquals(REQUEST, Command.valueOf(in.readUTF()).toString());

        var params = new HashMap<String, String>();
        params.put("uuid", UUID.randomUUID().toString());
        params.put("message", "hello world");
        out.writeUTF(new Command(MESSAGE, params).toString());
        assertEquals(Command.SUCCESS, Command.valueOf(in.readUTF()).toString());
    }


    @AfterClass
    public static void teardown() {
        ptpModule.stopIncoming();
    }

    @Override
    public void willReceiveTest() {
        System.out.println("receiving test message");
    }

    @Override
    public void didReceiveTest() {
        System.out.println("received test message");
    }
}
