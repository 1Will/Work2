package demo.util;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MjjSocket {
    public static String _ip;
    public static int _port;

    public MjjSocket() {
    }

    public static String MjjOp(String ip, int port, String send_str) {
        String ret = null;

        try {
            Socket e = new Socket(ip, port);
            PrintWriter out = new PrintWriter(new OutputStreamWriter(e.getOutputStream(), "gbk"), true);
            out.println(send_str);
            BufferedReader is = new BufferedReader(new InputStreamReader(e.getInputStream(), "gbk"));
            char[] cbuf = new char[1024];
            int i = is.read(cbuf, 0, 1024);
            ret = String.valueOf(cbuf, 0, i);
            out.close();
            is.close();
            e.close();
            return ret;
        } catch (Exception var9) {
            System.out.println(var9.toString());
            return var9.toString();
        }
    }

    public static String NetSet(String address, int port) {
        String data = String.format("NetSet@%s@%s", new Object[]{address, Integer.valueOf(port)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetConnectOn(int qNumber) {
        String data = String.format("NetConnectOn@%d", new Object[]{Integer.valueOf(qNumber)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetDconnectOn(int qNumber) {
        String data = String.format("NetDconnectOn@%d", new Object[]{Integer.valueOf(qNumber)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetPowerOn(int qNumber) {
        String data = String.format("NetPowerOn@%d", new Object[]{Integer.valueOf(qNumber)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetPowerOff(int qNumber) {
        String data = String.format("NetPowerOff@%d", new Object[]{Integer.valueOf(qNumber)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetFobiddenAllL(int qNumber) {
        String data = String.format("NetFobiddenAllL@%d", new Object[]{Integer.valueOf(qNumber)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetStopAllL(int qNumber) {
        String data = String.format("NetStopAllL@%d", new Object[]{Integer.valueOf(qNumber)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetClearFobidden(int qNumber) {
        String data = String.format("NetClearFobidden@%d", new Object[]{Integer.valueOf(qNumber)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetVentilation(int qNumber) {
        String data = String.format("NetVentilation@%d", new Object[]{Integer.valueOf(qNumber)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetOpenL(int qNumber, int lNumber, int lName) {
        String data = String.format("NetOpenL@%d@%d@%d", new Object[]{Integer.valueOf(qNumber), Integer.valueOf(lNumber), Integer.valueOf(lName)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetCloseL(int qNumber) {
        String data = String.format("NetCloseL@%d", new Object[]{Integer.valueOf(qNumber)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetOpenRecord(int qNumber, int lNumber, int jNumber, int cNumber, int ce, int bNumber, String pName, int lName) {
        String data = String.format("NetOpenRecord@%d@%d@%d@%d@%d@%d@%s@%d", new Object[]{Integer.valueOf(qNumber), Integer.valueOf(lNumber), Integer.valueOf(jNumber), Integer.valueOf(cNumber), Integer.valueOf(ce), Integer.valueOf(bNumber), pName, Integer.valueOf(lName)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }

    public static String NetStatusChk(int qNumber) {
        String data = String.format("NetStatusChk@%d", new Object[]{Integer.valueOf(qNumber)});
        String sret = MjjOp(_ip, _port, data);
        return sret;
    }
}
