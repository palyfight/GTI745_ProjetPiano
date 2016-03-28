package PianoRoll;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HomeType implements ArchiveDataType{

	@Override
	public void save(Score score,String filename) {
		try{
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);	
			oos.writeObject(score);
			oos.close();
			fos.close();
		}
		catch(Exception e){}
	}

	@Override
	public Score load(String filename) {
		try{
			FileInputStream fin = new FileInputStream(filename);
			ObjectInputStream oin = new ObjectInputStream(fin);
			Score s = (Score) oin.readObject();
			oin.close();
			fin.close();
			return s;
		}
		catch(Exception e){}
		return null;
	}

}
