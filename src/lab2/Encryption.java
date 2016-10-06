package lab2;

import java.util.ArrayList;

/**
 * Class for encryption and transcryption packages
 *
 * Package has special byte, that indicates when it begins and ends.
 * If the same byte is replaced inside the package, the marker is placed
 * before this byte to indicate that it's not the ent of the package.
 *
 * Created by Jack on 06.10.2016.
 */
public class Encryption {

    /**
     * Start and stop byte
     */
    public static byte packageBrace = 0x7e;
    /**
     *
     */
    public static byte packageEsk = 0x7d;

    /**
     * encription of the message string
     * @param msg unencrypted message
     * @return encrypted message
     */
    public static String encryptString(String msg) {
        ArrayList<Byte> byteList = new ArrayList<Byte>();
        byteList.add(packageBrace);
        for (byte character: msg.getBytes()) {
            if(character == packageBrace || character == packageEsk) {
                byteList.add(packageEsk);
            }
            byteList.add(character);
        }
        byteList.add(packageBrace);
        Byte[] byteArray = new Byte[byteList.size()];
        byteArray = byteList.toArray(byteArray);
        byte[] simpleByteArray = new byte[byteList.size()];
        for (int i = 0; i < byteArray.length; i++) {
            simpleByteArray[i] = byteArray[i];
        }
        return new String(simpleByteArray);
    }

    public static String transcript(String msg) throws PackageBoundsException {
        byte[] result = new byte[msg.length()];
        byte[] resource = msg.getBytes();
        if (resource[0] != packageBrace &&
                resource[resource.length - 1] != packageBrace) {
            throw new PackageBoundsException(msg);
        }
        /*
        * i = 1 because of we do not need first and last byte
         */
        int i = 1, j = 0;
        for (; i < resource.length - 1; i++) {
            if(resource[i] == packageEsk) {
                i++;
            }
            result[j] = resource[i];
            j++;
        }
        return new String(result);
    }
}
