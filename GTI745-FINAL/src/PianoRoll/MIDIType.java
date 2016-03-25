package PianoRoll;

import java.io.File;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MIDIType implements ArchieveDataType{

	@Override
	public Score load(String filename) {
		try{
			Sequence seq = MidiSystem.getSequence(new File(filename));
			for(Track track : seq.getTracks()){
				System.out.println("===========================" + track.size() + "===========================");
				for(int i =0; i < track.size(); i++){
					MidiEvent evt = track.get(i);
	                MidiMessage msg = evt.getMessage();
	                if(msg instanceof ShortMessage){
	                	ShortMessage smsg = (ShortMessage) msg;
	                	if(smsg.getCommand() == ShortMessage.NOTE_ON){
	                		
		                	System.out.println("Note on : " + (smsg.getData1()-21) + " "+ evt.getTick());
	                	}
	                }
	                
				}
			}
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
			
			track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF,0,0 ,0), tick ));
			
			File midiFile = new File(filename);
			MidiSystem.write(seq, 0, midiFile);
		}
		catch( Exception e){System.out.println(e);}
	}
}