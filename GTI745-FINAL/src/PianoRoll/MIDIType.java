package PianoRoll;

import java.io.File;
import java.util.ArrayList;

import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

public class MIDIType implements ArchiveDataType{
	
	private class MidiData{
		public final long beat;
		public final int pitch;
		
		public MidiData(long beat, int pitch){
			this.beat = beat;
			this.pitch = pitch;
		}

	}

	@Override
	public Score load(String filename) {
		ArrayList<MidiData> data = new ArrayList<MidiData>();
		
		try{
			Sequence seq = MidiSystem.getSequence(new File(filename));
			for(Track track : seq.getTracks()){
				long commonTick = findCommonTick(track);
				for(int i =0; i < track.size(); i++){
					MidiEvent evt = track.get(i);
					MidiMessage msg = evt.getMessage();
					if(msg instanceof ShortMessage){
						ShortMessage smsg = (ShortMessage) msg;
						if(smsg.getCommand() == ShortMessage.NOTE_ON && smsg.getData2() > 0){
							data.add(new MidiData(evt.getTick()/commonTick,(smsg.getData1()-21)));
						}
					}

				}	
			}
			
			//Create score
			Score score = new Score();
			score.increaseNumBeat((int) Math.floor( (data.get(data.size()-1).beat+49) /50 ) *50);
			for(MidiData md : data){
				score.grid[(int) md.beat][md.pitch] = true;
			}
			return score;
		}catch(Exception e){System.out.println(e);}
		return null;
	}

	private long findCommonTick(Track track){
		ArrayList<Long> originalTick = new ArrayList<Long>();
		ArrayList<Long> modifiedTick = new ArrayList<Long>();

		for(int i = 0; i < track.size(); i++){
			MidiEvent evt = track.get(i);
			MidiMessage msg = evt.getMessage();
			if(msg instanceof ShortMessage){
				ShortMessage smsg = (ShortMessage) msg;
				if(smsg.getCommand() == ShortMessage.NOTE_ON ){
					if(smsg.getData2() == 0)
						originalTick.add(evt.getTick());
					else
						modifiedTick.add(evt.getTick());
				}
			}
		}
		if(originalTick.size() > 1){
			return modifiedTick.get(1)/originalTick.get(1);
		}
		return 0;
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
						if ( score.grid[currentBeat][i] ){
							{
								track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON,0,i+score.midiNoteNumberOfLowestPitch,0), currentBeat));
								track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_ON,0,i+score.midiNoteNumberOfLowestPitch,127), tick));
							}
						}
					}
				}

				currentBeat += 1;
				if ( Constant.USE_SOUND ) {
					for ( int i = 0; i < score.numPitches; ++i ) {
						if ( score.grid[currentBeat ][i] )
							track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF,0,i+score.midiNoteNumberOfLowestPitch ,0), tick + (score.tempo/24)));
					}
				}
				tick += Math.ceil((score.tempo+(score.tempo*0.16))/24);

			}while(currentBeat < (score.numBeats-1));

			track.add(new MidiEvent(new ShortMessage(ShortMessage.NOTE_OFF,0,0 ,0), tick ));

			File midiFile = new File(filename);
			MidiSystem.write(seq, 0, midiFile);
		}
		catch( Exception e){System.out.println(e);}
	}
}
