package steganographie;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Imag {
	BufferedImage myImage=null;
	BufferedImage myImageNew=null;
	String cle=null; // Pour le message à dissimuler 
	
public Imag(String imgFile, String cle) throws IOException{
	File fichier=new File(imgFile);
	this.cle=cle; // Le message à dissimuler
	myImage=ImageIO.read(fichier);
	myImageNew=ImageIO.read(fichier);
}
// Méthode pour charger tous les pixels de l'image dans un tableau à 2 dimensions
public int [][] chargerPixelsImage(){
	int w = myImage.getWidth();
	System.out.println(w);
   	int h = myImage.getHeight();
   	System.out.println(h);
   	int [][]pixels=new int[w][h];
   	for(int i=0;i<w;i++) {
   		for(int j=0;j<h;j++) {
   		 int rgb=myImage.getRGB(i,j);
   		 pixels[i][j] = rgb;
   		}
   		}
   	return pixels;
}

//Méthode pour charger tous les pixels de l'image dans un tableau à 2 dimensions
public int [][] chargerPixelsImage(BufferedImage img){
	int w = img.getWidth();
	//System.out.println(w);
   	int h = img.getHeight();
   	//System.out.println(h);
   	int [][]pixels=new int[w][h];
   	for(int i=0;i<w;i++) {
   		for(int j=0;j<h;j++) {
   		 int rgb=img.getRGB(i,j);
   		 pixels[i][j] = rgb;
   		}
   		}
   	return pixels;
}


//Convertir une chaîne de caractères en binaire
static char []  ChaineToBinaire(String chaine){
    String res="";
    char[] arrayChaine=chaine.toCharArray();
    for(int i=0;i<arrayChaine.length;i++){
        int a=(int)arrayChaine[i];
        res=res+Integer.toBinaryString(a);
    }
    return res.toCharArray();
}
// Méthode pour créer la nouvelle image dans laquelle est cachée le message
public void createNewImg(BufferedImage image) throws IOException{
File img=new File("image2.bmp");
ImageIO.write(image, "bmp", img);
}

public void steganographie() throws IOException{
	char[] cleBinaire=ChaineToBinaire(cle);
	System.out.println("Message initial    : "+new String(cleBinaire));
	int tailleCle=cleBinaire.length;
	System.out.println("Taille: "+tailleCle);
	int pixels[][]=chargerPixelsImage(myImage);
	int w =pixels.length;
	int h=pixels[0].length;
	int pos=0;
	char []pixel2=new char[tailleCle];
	//System.out.println("L\'ancien: "+Integer.toBinaryString(pixels[0][0]));
	for (int i=0;i<w;i++){
		if (pos==tailleCle) break;
	for(int j=0;j<h;j++){	
		
		String pixel1=Integer.toBinaryString(pixels[i][j]);
		pixel2=pixel1.toCharArray();
		if (pos==tailleCle) {
			pixels[i][j]=(int) Long.parseLong(new String (pixel2),2);
			myImageNew.setRGB(i, j, pixels[i][j]);
			break;
		}
		pixel2[15]=cleBinaire[pos];
		pos++;
		if (pos==tailleCle) {
			pixels[i][j]=(int) Long.parseLong(new String (pixel2),2);
			myImageNew.setRGB(i, j, pixels[i][j]);
			break;
		}
		pixel2[23]=cleBinaire[pos];
		pos++;
		if (pos==tailleCle) {
			pixels[i][j]=(int) Long.parseLong(new String (pixel2),2);
			myImageNew.setRGB(i, j, pixels[i][j]);
			break;
		}
		pixel2[31]=cleBinaire[pos];
		pixels[i][j]=(int) Long.parseLong(new String (pixel2),2);
		//System.out.println("VERIF 1: "+Integer.toBinaryString(pixels[i][j]));
		myImageNew.setRGB(i, j, pixels[i][j]);
		//System.out.println("POS: "+pos++);
	}
	}
	// Création de la nouvelle image
	createNewImg(myImageNew);
	
}

public void recupMessage() throws IOException{
	
	File fichier=new File("image2.bmp");
	BufferedImage myImage2=ImageIO.read(fichier);
    int pixels[][]=chargerPixelsImage(myImage2);
    int w =pixels.length;
	int h=pixels[0].length;
	int pos=0;
	int tailleCle=ChaineToBinaire(cle.toString()).length;
	//System.out.println("Taille 2: "+tailleCle);
	char []pixel2=new char[tailleCle];
	char []bits=new char[tailleCle];
	for (int i=0;i<w;i++){
		if (pos==tailleCle) break;
	for(int j=0;j<h;j++){	
		
		String pixel1=Integer.toBinaryString(pixels[i][j]);
		pixel2=pixel1.toCharArray();
		if (pos==tailleCle) break;
		bits[pos]=pixel2[15];
		pos++;
		if (pos==tailleCle) break;
		bits[pos]=pixel2[23];
		pos++;
		if (pos==tailleCle) break;
		bits[pos]=pixel2[31];			
	}
	}
       System.out.println("Message reconstitué: "+String.valueOf(bits));      
}
}
