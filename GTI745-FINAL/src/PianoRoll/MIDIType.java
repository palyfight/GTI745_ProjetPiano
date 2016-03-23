package PianoRoll;

import java.io.File;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MIDIType implements ArchieveDataType{

	@Override
	public Score load(String filename) {
		try{
			
		}catch(Exception e){System.out.println(e);}
		return null;
	}

	@Override
	public void save(Score score, String filename) {
		try{
			Sequence seq = new Sequence(Sequence.PPQ,24);
			Track track = seq.createTrack();
			
			int currentBeat = 0;
			long tick = 0;
			
			do{
				if ( Constant.USE_SOUND ) {
					for ( int i = 0; i < score.numPitches; ++i ) {
						if ( score.grid[currentBeat][i] )
							track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON,0,i+score.midiNoteNumberOfLowestPitch,127), tick));
					}
				}

				currentBeat += 1;
				if ( Constant.USE_SOUND ) {
					for ( int i = 0; i < score.numPitches; ++i ) {
						if ( score.grid[currentBeat ][i] )
							track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF,0,i+score.midiNoteNumberOfLowestPitch ,0), tick + (score.tempo/24)));
					}
				}
				tick += (score.tempo+(score.tempo*0.16))/24;

			}while(currentBeat < (score.numBeats-1));
			
			
			File midiFile = new File(filename);
			MidiSystem.write(seq, 0, midiFile);
		}
		catch( Exception e){System.out.println(e);}
	}
}
