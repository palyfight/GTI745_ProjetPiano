package PianoRoll;

public interface ArchiveDataType {
	public Score load(String filename);
	public void save(Score score, String filename);
}
