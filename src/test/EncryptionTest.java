package test;

import lab2.Encryption;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


/**
 * Created by Jack on 11.10.2016.
 */
public class EncryptionTest {
	@Test
	public void encryptString() throws Exception {
		assertEquals(Encryption.encryptString("ti~ts"), "~ti}~ts~");
	}

	@Test
	public void transcriptString() throws Exception {
		String msg = "~first pack~jsgejjjj~second}}}~pack~}}}}~thirdpack~";
		ArrayList<String> answer = Encryption.transcriptString(msg);
		System.out.println(answer.get(0) + "\n" + answer.get(1) + "\n" + answer.get(2));
	}

}