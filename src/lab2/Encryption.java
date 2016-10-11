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
	private static final byte packageBrace = 0x7e;
	/**
	 * byte for mask bytes that are not start/stop bytes
	 */
	private static final byte packageEsk = 0x7d;


	/**
	 * encription of the message string
	 *
	 * @param msg unencrypted message
	 * @return array of transcripted packages
	 */
	public static String encryptString(String msg) {
		ArrayList<Byte> byteList = new ArrayList<>();
		byteList.add(packageBrace);
		for (byte character : msg.getBytes()) {
			if (character == packageBrace || character == packageEsk) {
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


	public static ArrayList<String> transcriptString(String msg) {

		ArrayList packs = new ArrayList<String>();

		byte[] resource = msg.getBytes();
		int i = 0;
		byte[] currentPack = new byte[msg.length()];
		int j = 0;

		boolean anyPackFound = false;
		boolean inPack = false;

		for (; i < resource.length; i++) {
			if (inPack) {
				switch (resource[i]) {
					case packageEsk:
						currentPack[j++] = resource[++i];    //next i, assignment, next j
						break;
					case packageBrace:
						inPack = false;
						System.out.println(new String(currentPack));   // output
						packs.add(new String(currentPack));            // add to array list
						break;
					default:
						currentPack[j++] = resource[i];
						break;
				}
			} else {
				if (resource[i] == packageBrace) {
					if (!anyPackFound) {
						anyPackFound = true;
					} else {
						zeroByteArray(currentPack);
						j = 0;
					}
					inPack = true;
				}
			}
		}
		return packs;
	}

	private static void zeroByteArray(byte[] array) {
		for (byte element : array) {
			element = 0;
		}
	}
}
