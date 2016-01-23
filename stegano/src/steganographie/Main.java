package steganographie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Saisir le message à dissimuler");
		String cle=bf.readLine(); // C'est en faite le message
		Imag imag=new Imag("image.bmp",cle);
		imag.steganographie();
		imag.recupMessage();
	}

}
