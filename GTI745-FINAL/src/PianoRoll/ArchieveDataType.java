package PianoRoll;

public interface ArchieveDataType {
	public Score load(String filename);
	public void save(Score score, String filename);
}
