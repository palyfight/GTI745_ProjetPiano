package PianoRoll;

// import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;



/*
   The octave:
      pitch class     English name     French name
           0             C             do
           1             C#/Db         do diese / re bemol
           2             D             re
           3             D#/Eb         re diese / mi bemol
           4             E             mi
           5             F             fa
           6             F#/Gb         fa diese / sol bemol
           7             G             sol
           8             G#/Ab         sol diese / la bemol
           9             A             la
          10             A#/Bb         la diese / si bemol
          11             B             si
           0             C             do

   A grand piano keyboard has 88 keys:
                              Note Name     MIDI note number     Pitch class
      lowest key (1st key):       A0            21                     9
      middle C:                   C4            60                     0
      highest key (88th key):     C8           108                     0
 */


class Score implements Serializable{
	public static final int midiNoteNumberOfMiddleC = 60;

	public int numPitches = 88;
	public static final int pitchClassOfLowestPitch = 9; // 9==A==la
	public static final int midiNoteNumberOfLowestPitch = 21;
	public int numBeats = 128;
	public boolean [][] grid;
	public int tempo = 150;

	public static final int numPitchesInOctave = 12;
	public String [] namesOfPitchClasses;
	public boolean [] pitchClassesInMajorScale;
	public boolean [] pitchClassesToEmphasizeInMajorScale;
	public boolean [] pitchClassesInOrientalScale;
	public boolean [] pitchClassesInMajorPentatonicScale;
	public boolean [] pitchClassesCustomScale;
	public boolean [] pitchClassesGMinorPentatonicScale;
	public boolean isReading;
	public int readingPositionX;
	public ArrayList <Integer> readingPositionY = new ArrayList <Integer>();
	public ArrayList <ColoredPoint> ColoredPoints = new ArrayList <ColoredPoint>();

	public Score() {
		grid = new boolean[ numBeats ][ numPitches ];

		namesOfPitchClasses = new String[ numPitchesInOctave ];
		namesOfPitchClasses[ 0] = "C";
		namesOfPitchClasses[ 1] = "C#";
		namesOfPitchClasses[ 2] = "D";
		namesOfPitchClasses[ 3] = "D#";
		namesOfPitchClasses[ 4] = "E";
		namesOfPitchClasses[ 5] = "F";
		namesOfPitchClasses[ 6] = "F#";
		namesOfPitchClasses[ 7] = "G";
		namesOfPitchClasses[ 8] = "G#";
		namesOfPitchClasses[ 9] = "A";
		namesOfPitchClasses[10] = "A#";
		namesOfPitchClasses[11] = "B";
		
		//Oriental scale : C#, D#, F#, G#, A#
		pitchClassesInOrientalScale = new boolean[ numPitchesInOctave ];
		pitchClassesInOrientalScale[ 0] = false;
		pitchClassesInOrientalScale[ 1] = true;
		pitchClassesInOrientalScale[ 2] = false;
		pitchClassesInOrientalScale[ 3] = true;
		pitchClassesInOrientalScale[ 4] = false;
		pitchClassesInOrientalScale[ 5] = false;
		pitchClassesInOrientalScale[ 6] = true;
		pitchClassesInOrientalScale[ 7] = false;
		pitchClassesInOrientalScale[ 8] = true;
		pitchClassesInOrientalScale[ 9] = false;
		pitchClassesInOrientalScale[10] = true;
		pitchClassesInOrientalScale[11] = false;
		
		//Pentatonic Major scale : C, D, E, G, A
		pitchClassesInMajorPentatonicScale = new boolean[ numPitchesInOctave ];
		pitchClassesInMajorPentatonicScale[ 0] = true;
		pitchClassesInMajorPentatonicScale[ 1] = false;
		pitchClassesInMajorPentatonicScale[ 2] = true;
		pitchClassesInMajorPentatonicScale[ 3] = false;
		pitchClassesInMajorPentatonicScale[ 4] = true;
		pitchClassesInMajorPentatonicScale[ 5] = false;
		pitchClassesInMajorPentatonicScale[ 6] = false;
		pitchClassesInMajorPentatonicScale[ 7] = true;
		pitchClassesInMajorPentatonicScale[ 8] = false;
		pitchClassesInMajorPentatonicScale[ 9] = true;
		pitchClassesInMajorPentatonicScale[10] = false;
		pitchClassesInMajorPentatonicScale[11] = false;
		
		//Pentatonic G Minor scale : C, D, F, G, B
		pitchClassesGMinorPentatonicScale = new boolean[ numPitchesInOctave ];
		pitchClassesGMinorPentatonicScale[ 0] = true;
		pitchClassesGMinorPentatonicScale[ 1] = false;
		pitchClassesGMinorPentatonicScale[ 2] = true;
		pitchClassesGMinorPentatonicScale[ 3] = false;
		pitchClassesGMinorPentatonicScale[ 4] = false;
		pitchClassesGMinorPentatonicScale[ 5] = true;
		pitchClassesGMinorPentatonicScale[ 6] = false;
		pitchClassesGMinorPentatonicScale[ 7] = true;
		pitchClassesGMinorPentatonicScale[ 8] = false;
		pitchClassesGMinorPentatonicScale[ 9] = false;
		pitchClassesGMinorPentatonicScale[10] = false;
		pitchClassesGMinorPentatonicScale[11] = true;

		//Custom harmonious scale : C, D, E, F#, G, A, B
		pitchClassesCustomScale = new boolean[ numPitchesInOctave ];
		pitchClassesCustomScale[ 0] = true;
		pitchClassesCustomScale[ 1] = false;
		pitchClassesCustomScale[ 2] = true;
		pitchClassesCustomScale[ 3] = false;
		pitchClassesCustomScale[ 4] = true;
		pitchClassesCustomScale[ 5] = false;
		pitchClassesCustomScale[ 6] = true;
		pitchClassesCustomScale[ 7] = true;
		pitchClassesCustomScale[ 8] = false;
		pitchClassesCustomScale[ 9] = true;
		pitchClassesCustomScale[10] = false;
		pitchClassesCustomScale[11] = true;

		pitchClassesInMajorScale = new boolean[ numPitchesInOctave ];
		pitchClassesInMajorScale[ 0] = true;
		pitchClassesInMajorScale[ 1] = false;
		pitchClassesInMajorScale[ 2] = true;
		pitchClassesInMajorScale[ 3] = false;
		pitchClassesInMajorScale[ 4] = true;
		pitchClassesInMajorScale[ 5] = true;
		pitchClassesInMajorScale[ 6] = false;
		pitchClassesInMajorScale[ 7] = true;
		pitchClassesInMajorScale[ 8] = false;
		pitchClassesInMajorScale[ 9] = true;
		pitchClassesInMajorScale[10] = false;
		pitchClassesInMajorScale[11] = true;

		pitchClassesToEmphasizeInMajorScale = new boolean[ numPitchesInOctave ];
		pitchClassesToEmphasizeInMajorScale[ 0] = true;
		pitchClassesToEmphasizeInMajorScale[ 1] = false;
		pitchClassesToEmphasizeInMajorScale[ 2] = false;
		pitchClassesToEmphasizeInMajorScale[ 3] = false;
		pitchClassesToEmphasizeInMajorScale[ 4] = true;
		pitchClassesToEmphasizeInMajorScale[ 5] = true;
		pitchClassesToEmphasizeInMajorScale[ 6] = false;
		pitchClassesToEmphasizeInMajorScale[ 7] = true;
		pitchClassesToEmphasizeInMajorScale[ 8] = false;
		pitchClassesToEmphasizeInMajorScale[ 9] = false;
		pitchClassesToEmphasizeInMajorScale[10] = false;
		pitchClassesToEmphasizeInMajorScale[11] = false;
	}

	// returns -1 if out of bounds
	public int getMidiNoteNumberForMouseY( GraphicsWrapper gw, int mouse_y ) {
		float y = gw.convertPixelsToWorldSpaceUnitsY( mouse_y );
		int indexOfPitch = (int)(-y);
		if ( 0 <= indexOfPitch && indexOfPitch < numPitches )
			return indexOfPitch + midiNoteNumberOfLowestPitch;
		return -1;
	}

	// returns -1 if out of bounds
	public int getBeatForMouseX( GraphicsWrapper gw, int mouse_x ) {
		float x = gw.convertPixelsToWorldSpaceUnitsX( mouse_x );
		int indexOfBeat = (int)x;
		if ( 0 <= indexOfBeat && indexOfBeat < numBeats )
			return indexOfBeat;
		return -1;
	}

	public void draw(
			GraphicsWrapper gw,
			boolean highlightMajorCScale,
			int midiNoteNumber1ToHilite,
			int beat1ToHilite,
			int beat2ToHilite,
			boolean randomSelected,
			boolean orientalSelected,
			boolean pentatonicSelected,
			boolean customSeletecd,
			boolean gMinorSelected
			) {
		for ( int y = 0; y < numPitches; y++ ) {
			int pitchClass = ( y + pitchClassOfLowestPitch ) % numPitchesInOctave;
			int midiNoteNumber = y + midiNoteNumberOfLowestPitch;
			if ( midiNoteNumber == midiNoteNumber1ToHilite ) { // mouse cursor
				gw.setColor( 0, 1, 1 );
				gw.fillRect( 0, -y-0.8f, numBeats, 0.6f );
			}

			if ( midiNoteNumber == midiNoteNumberOfMiddleC ) {
				gw.setColor( 1, 1, 1 );
				gw.fillRect( 0, -y-0.7f, numBeats, 0.4f );
			}
			else if ( pitchClass == 0 && highlightMajorCScale ) {
				gw.setColor( 1, 1, 1 );
				gw.fillRect( 0, -y-0.6f, numBeats, 0.2f );
			}
			else if (randomSelected){
				if ( pitchClassesToEmphasizeInMajorScale[ pitchClass ] && highlightMajorCScale ) {
					gw.setColor( 0.6f, 0.6f, 0.6f );
					gw.fillRect( 0, -y-0.6f, numBeats, 0.2f );
				}
				else if ( pitchClassesInMajorScale[ pitchClass ] || ! highlightMajorCScale ) {
					gw.setColor( 0.6f, 0.6f, 0.6f );
					gw.fillRect( 0, -y-0.55f, numBeats, 0.1f );
				}
			}
			else if (orientalSelected){
				if ( pitchClassesInOrientalScale[ pitchClass ] ) {
					gw.setColor( 0.6f, 0.6f, 0.6f );
					gw.fillRect( 0, -y-0.6f, numBeats, 0.2f );
				}
			}
			else if (pentatonicSelected){
				if ( pitchClassesInMajorPentatonicScale[ pitchClass ] ) {
					gw.setColor( 0.6f, 0.6f, 0.6f );
					gw.fillRect( 0, -y-0.6f, numBeats, 0.2f );
				}
			}
			else if (customSeletecd){
				if ( pitchClassesCustomScale[ pitchClass ] ) {
					gw.setColor( 0.6f, 0.6f, 0.6f );
					gw.fillRect( 0, -y-0.6f, numBeats, 0.2f );
				}
			}
			else if (gMinorSelected){
				if ( pitchClassesGMinorPentatonicScale[ pitchClass ] ) {
					gw.setColor( 0.6f, 0.6f, 0.6f );
					gw.fillRect( 0, -y-0.6f, numBeats, 0.2f );
				}
			}
		}
		for ( int x = 0; x < numBeats; x++ ) {
			if ( x == beat1ToHilite ) { // mouse cursor
				gw.setColor( 0, 1, 1 );
				gw.fillRect( x+0.2f, -numPitches, 0.6f, numPitches );
			}

			if ( x == beat2ToHilite ) { // time cursor
				gw.setColor( 1, 0, 0 );
				gw.fillRect( x+0.45f, -numPitches, 0.1f, numPitches );
			}
			else if ( x % 4 == 0 ) {
				gw.setColor( 0.6f, 0.6f, 0.6f );
				gw.fillRect( x+0.45f, -numPitches, 0.1f, numPitches );
			}
		}
		gw.setColor( 0, 0, 0 );
		for ( int y = 0; y < numPitches; ++y ) {
			for ( int x = 0; x < numBeats; ++x ) {
				if ( grid[x][y] ){
					gw.fillRect( x+0.3f, -y-0.7f, 0.4f, 0.4f );
				}

			}
		}
		if(isReading){
			for(int i = 0; i < ColoredPoints.size(); i++){
				gw.setColor(ColoredPoints.get(i).getCurrentColor(),ColoredPoints.get(i).getOpacity()) ;
				ColoredPoints.get(i).changeColor();
				gw.drawRect(ColoredPoints.get(i).getxPosition()+0.3f, -ColoredPoints.get(i).getyPosition()-0.7f, 0.1f, 0.1f );		

				if(ColoredPoints.get(i).getCurrentColor() == Color.RED) {
					gw.setFontHeight(4);
					gw.drawRect((ColoredPoints.get(i).getxPosition()+0.3f)-3f, -ColoredPoints.get(i).getyPosition()-10f, 7f, 5f );
					String note = "";
					if (grid[ColoredPoints.get(i).getxPosition()][ColoredPoints.get(i).getyPosition()] ){
						note = namesOfPitchClasses[( (ColoredPoints.get(i).getyPosition()+21) - midiNoteNumberOfLowestPitch + pitchClassOfLowestPitch )
						                           % numPitchesInOctave
						                           ];
					}
					gw.drawString((ColoredPoints.get(i).getxPosition()+0.3f)-2f, -ColoredPoints.get(i).getyPosition()-6f, note);
				}
				if(ColoredPoints.get(i).getCurrentIndex() == ColoredPoints.get(i).getPointColors().size()-1){
					ColoredPoints.remove(i);
				}
			}
			gw.setFontHeight(14);
		}
	}

	public AlignedRectangle2D getBoundingRectangle() {
		return new AlignedRectangle2D(
				new Point2D(0,-numPitches),
				new Point2D(numBeats,0)
				);
	}

	public void increaseNumBeat(int numBeats){
		this.grid = cloneGrid(this.grid, numBeats);
	}

	public boolean[][] cloneGrid(boolean[][] oldGrid, int numBeats){
		boolean [][] newGrid = new boolean [numBeats][ this.numPitches ]; 
		for ( int y = 0; y < numPitches; ++y ) {
			for ( int x = 0; x <  Math.min(this.numBeats, numBeats) ; ++x ) {
				newGrid[x][y] = oldGrid[x][y];
			}
		}		
		this.numBeats = numBeats;
		return newGrid;
	}

}

class MyCanvas extends JPanel implements KeyListener, MouseListener, MouseMotionListener, Runnable {

	SimplePianoRoll simplePianoRoll;
	GraphicsWrapper gw = new GraphicsWrapper();
	Metronone metronone = new Metronone();
	Rectangle selection = null;
	ArrayList<Point2D> altGrid = new ArrayList<Point2D>();

	Score score = new Score();

	Thread thread = null;
	boolean threadSuspended;
	
	ArrayList<Point2D> original = new ArrayList<Point2D>();

	int currentBeat = 0;

	public static final int RADIAL_MENU_PLAY = 0;
	public static final int RADIAL_MENU_STOP = 1;
	public static final int RADIAL_MENU_DRAW = 2;
	public static final int RADIAL_MENU_ERASE = 3;

	public static final int CONTROL_MENU_ZOOM = 0;
	public static final int CONTROL_MENU_PAN = 1;
	public static final int CONTROL_MENU_TEMPO = 2;
	public static final int CONTROL_MENU_TOTAL_DURATION = 3;
	public static final int CONTROL_MENU_TRANSPOSE = 4;

	public static final int CONTROL_MENU_T_TRANSLATE = 0;
	public static final int CONTROL_MENU_T_COPY = 1;

	RadialMenuWidget radialMenu = new RadialMenuWidget();
	ControlMenuWidget controlMenu = new ControlMenuWidget();
	ControlMenuWidget controlTranslate = new ControlMenuWidget();

	int mouse_x, mouse_y, old_mouse_x, old_mouse_y;
	Point2D rectAnchor = new Point2D();

	boolean isControlKeyDown = false;
	//Added
	boolean isAltKeyDown = false;

	int beatOfMouseCursor = -1; // -1 for none
	int midiNoteNumberOfMouseCurser = -1; // -1 for none

	public MyCanvas( SimplePianoRoll sp ) {
		simplePianoRoll = sp;
		setBorder( BorderFactory.createLineBorder( Color.black ) );
		setBackground( Color.white );
		addKeyListener( this );
		addMouseListener( this );
		addMouseMotionListener( this );

		radialMenu.setItemLabelAndID( RadialMenuWidget.CENTRAL_ITEM, "",            RADIAL_MENU_STOP );
		radialMenu.setItemLabelAndID( 1,                             "Stop Music",  RADIAL_MENU_STOP );
		radialMenu.setItemLabelAndID( 3,                             "Draw Notes",  RADIAL_MENU_DRAW );
		radialMenu.setItemLabelAndID( 5,                             "Play Music",  RADIAL_MENU_PLAY );
		radialMenu.setItemLabelAndID( 7,                             "Erase Notes", RADIAL_MENU_ERASE );

		controlMenu.setItemLabelAndID( ControlMenuWidget.CENTRAL_ITEM, "", -1 );
		controlMenu.setItemLabelAndID( 1, "Tempo", CONTROL_MENU_TEMPO );
		controlMenu.setItemLabelAndID( 2, "Pan", CONTROL_MENU_PAN );
		controlMenu.setItemLabelAndID( 3, "Zoom", CONTROL_MENU_ZOOM );
		controlMenu.setItemLabelAndID( 5, "Total Duration", CONTROL_MENU_TOTAL_DURATION );
		controlMenu.setItemLabelAndID( 7, "Transpose", CONTROL_MENU_TRANSPOSE );

		controlTranslate.setItemLabelAndID(ControlMenuWidget.CENTRAL_ITEM, "", -1);
		controlTranslate.setItemLabelAndID( 3, "Translate", CONTROL_MENU_T_TRANSLATE );
		controlTranslate.setItemLabelAndID( 7, "Copy", CONTROL_MENU_T_COPY );

		gw.frame( score.getBoundingRectangle(), false );
	}
	public Dimension getPreferredSize() {
		return new Dimension( Constant.INITIAL_WINDOW_WIDTH, Constant.INITIAL_WINDOW_HEIGHT );
	}
	public void clear() {
		for ( int y = 0; y < score.numPitches; ++y )
			for ( int x = 0; x < score.numBeats; ++x )
				score.grid[x][y] = false;
		repaint();
	}

	public void frameAll() {
		gw.frame( score.getBoundingRectangle(), false );
		repaint();
	}
	public void paintComponent( Graphics g ) {
		super.paintComponent( g );
		gw.set( g );
		if ( getWidth() != gw.getWidth() || getHeight() != gw.getHeight() )
			gw.resize( getWidth(), getHeight() );
		gw.clear(0.4f,0.4f,0.4f);
		gw.setupForDrawing();
		gw.setCoordinateSystemToWorldSpaceUnits();
		gw.enableAlphaBlending();

		score.draw(
				gw,
				simplePianoRoll.highlightMajorScale,
				midiNoteNumberOfMouseCurser,
				beatOfMouseCursor,
				currentBeat,
				simplePianoRoll.randomSelected,
				simplePianoRoll.orientalSelected,
				simplePianoRoll.pentatonicSelected,
				simplePianoRoll.customSelected,
				simplePianoRoll.gMinorSelected
				);

		gw.setCoordinateSystemToPixels();

		if ( radialMenu.isVisible() )
			radialMenu.draw( gw );
		if ( controlMenu.isVisible() )
			controlMenu.draw( gw );
		if (controlTranslate.isVisible())
			controlTranslate.draw(gw);

		if ( ! radialMenu.isVisible() && ! controlMenu.isVisible() ) {
			// draw datatip
			if ( midiNoteNumberOfMouseCurser >= 0 && beatOfMouseCursor >= 0 ) {
				final int margin = 5;
				final int x_offset = 15;

				String s="";
				int width = 0;
				int height = 0;

				if(simplePianoRoll.orientalSelected){
					if(score.pitchClassesInOrientalScale[
					                                     ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )
					                                     % score.numPitchesInOctave]){
						s = score.namesOfPitchClasses[
						                              ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )
						                              % score.numPitchesInOctave
						                              ];
						width = Math.round( gw.stringWidth( s ) + 2*margin );
						height = RadialMenuWidget.textHeight + 2*margin;
					}
				}
				else if(simplePianoRoll.pentatonicSelected){
					if(score.pitchClassesInMajorPentatonicScale[
					                                            ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )
					                                            % score.numPitchesInOctave]){
						s = score.namesOfPitchClasses[
						                              ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )
						                              % score.numPitchesInOctave
						                              ];
						width = Math.round( gw.stringWidth( s ) + 2*margin );
						height = RadialMenuWidget.textHeight + 2*margin;
					}
				}
				else if(simplePianoRoll.customSelected){
					if(score.pitchClassesCustomScale[
					                                 ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )
					                                 % score.numPitchesInOctave]){
						s = score.namesOfPitchClasses[
						                              ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )
						                              % score.numPitchesInOctave
						                              ];
						width = Math.round( gw.stringWidth( s ) + 2*margin );
						height = RadialMenuWidget.textHeight + 2*margin;
					}
				}
				else if(simplePianoRoll.gMinorSelected){
					if(score.pitchClassesGMinorPentatonicScale[
					                                           ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )
					                                           % score.numPitchesInOctave]){
						s = score.namesOfPitchClasses[
						                              ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )
						                              % score.numPitchesInOctave
						                              ];
						width = Math.round( gw.stringWidth( s ) + 2*margin );
						height = RadialMenuWidget.textHeight + 2*margin;
					}
				}
				else{
					s = score.namesOfPitchClasses[
					                              ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )
					                              % score.numPitchesInOctave
					                              ];
					width = Math.round( gw.stringWidth( s ) + 2*margin );
					height = RadialMenuWidget.textHeight + 2*margin;
				}

				int x0 = mouse_x + x_offset;
				int y0 = mouse_y - RadialMenuWidget.textHeight - 2*margin;
				gw.setColor( 0, 0, 0, 0.6f );
				gw.fillRect( x0, y0, width, height );
				gw.setColor( 1, 1, 1 );
				gw.drawRect( x0, y0, width, height );
				gw.drawString( mouse_x + x_offset + margin, mouse_y - margin, s );
			}

		}

		//Tempo
		g.setColor(Color.white);
		g.setFont(new Font("default", Font.BOLD, 16));
		g.drawString("Tempo : " + score.tempo + " millisecondes", 50,50 );

		//Beats
		g.setColor(Color.white);
		g.setFont(new Font("default", Font.BOLD, 16));
		g.drawString("Total Beats : " + score.numBeats , 300,50 );

		//selection
		if(selection != null){
			g.setColor(new Color(255,255,255, 95));
			g.fillRect(selection.x, selection.y, selection.width, selection.height);
		}
	}

	public void keyPressed( KeyEvent e ) {
		if ( e.getKeyCode() == KeyEvent.VK_CONTROL ) {
			isControlKeyDown = true;
			if (
					beatOfMouseCursor>=0
					&& simplePianoRoll.rolloverMode == SimplePianoRoll.RM_PLAY_NOTE_UPON_ROLLOVER_IF_SPECIAL_KEY_HELD_DOWN
					)
				playNote( midiNoteNumberOfMouseCurser );
		}
	}
	public void keyReleased( KeyEvent e ) {
		if ( e.getKeyCode() == KeyEvent.VK_CONTROL ) {
			isControlKeyDown = false;
			stopPlayingNote( midiNoteNumberOfMouseCurser );
		}

	}
	public void keyTyped( KeyEvent e ) {
	}


	public void mouseClicked( MouseEvent e ) { }
	public void mouseEntered( MouseEvent e ) { }
	public void mouseExited( MouseEvent e ) {}

	private void paint( int mouse_x, int mouse_y ) {
		int newBeatOfMouseCursor = score.getBeatForMouseX( gw, mouse_x );
		int newMidiNoteNumberOfMouseCurser = score.getMidiNoteNumberForMouseY( gw, mouse_y );
		if (newBeatOfMouseCursor != beatOfMouseCursor || newMidiNoteNumberOfMouseCurser != midiNoteNumberOfMouseCurser) {
			beatOfMouseCursor = newBeatOfMouseCursor;
			midiNoteNumberOfMouseCurser = newMidiNoteNumberOfMouseCurser;
			repaint();
		}


		if ( beatOfMouseCursor >= 0 && midiNoteNumberOfMouseCurser >= 0 ) {
			if ( simplePianoRoll.dragMode == SimplePianoRoll.DM_DRAW_NOTES ) {
				if ( score.grid[beatOfMouseCursor][midiNoteNumberOfMouseCurser-score.midiNoteNumberOfLowestPitch] != true ) {
					score.grid[beatOfMouseCursor][midiNoteNumberOfMouseCurser-score.midiNoteNumberOfLowestPitch] = true;
					repaint();
				}
			}
			else if ( simplePianoRoll.dragMode == SimplePianoRoll.DM_ERASE_NOTES ) {
				if ( score.grid[beatOfMouseCursor][midiNoteNumberOfMouseCurser-score.midiNoteNumberOfLowestPitch] != false ) {
					score.grid[beatOfMouseCursor][midiNoteNumberOfMouseCurser-score.midiNoteNumberOfLowestPitch] = false;
					repaint();
				}
			}
		}


	}

	public void mousePressed( MouseEvent e ) {
		old_mouse_x = mouse_x;
		old_mouse_y = mouse_y;
		mouse_x = e.getX();
		mouse_y = e.getY();
		rectAnchor = new Point2D(mouse_x,mouse_y);


		isControlKeyDown = e.isControlDown();
		//Added
		isAltKeyDown = e.isAltDown();

		if ( radialMenu.isVisible() || (SwingUtilities.isLeftMouseButton(e) && e.isControlDown()) ) {
			int returnValue = radialMenu.pressEvent( mouse_x, mouse_y );
			if ( returnValue == CustomWidget.S_REDRAW )
				repaint();
			if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
				return;
		}
		if ( controlMenu.isVisible() || (SwingUtilities.isLeftMouseButton(e) && e.isShiftDown()) ) {
			int returnValue = controlMenu.pressEvent( mouse_x, mouse_y );
			if ( returnValue == CustomWidget.S_REDRAW )
				repaint();
			if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
				return;
		}
		if ( controlTranslate.isVisible() || (SwingUtilities.isRightMouseButton(e) && e.isAltDown()) && selection != null) {
			int returnValue = controlTranslate.pressEvent( mouse_x, mouse_y );
			if ( returnValue == CustomWidget.S_REDRAW )
				repaint();
			if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
				return;
		}
		if ( SwingUtilities.isLeftMouseButton(e) ) {
			int midiNoteNumberOfMouseCurser = score.getMidiNoteNumberForMouseY(gw, mouse_y);
			int pitchClass = ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )% score.numPitchesInOctave;

			if(simplePianoRoll.customSelected){
				if(score.pitchClassesCustomScale[pitchClass]){
					paint( mouse_x, mouse_y );
				}
			}
			else if(simplePianoRoll.orientalSelected){
				if(score.pitchClassesInOrientalScale[pitchClass]){
					paint( mouse_x, mouse_y );
				}
			}
			else if(simplePianoRoll.pentatonicSelected){
				if(score.pitchClassesInMajorPentatonicScale[pitchClass]){
					paint( mouse_x, mouse_y );
				}
			}
			else if(simplePianoRoll.gMinorSelected){
				if(score.pitchClassesGMinorPentatonicScale[pitchClass]){
					paint( mouse_x, mouse_y );
				}
			}
			else {
				if(isAltKeyDown){
					int newBeatOfMouseCursor = score.getBeatForMouseX( gw, mouse_x );
					int newMidiNoteNumberOfMouseCurser = score.getMidiNoteNumberForMouseY( gw, mouse_y );
					if (
							newBeatOfMouseCursor != beatOfMouseCursor
							|| newMidiNoteNumberOfMouseCurser != midiNoteNumberOfMouseCurser
							) {
						beatOfMouseCursor = newBeatOfMouseCursor;
						midiNoteNumberOfMouseCurser = newMidiNoteNumberOfMouseCurser;
						repaint();
					}

					if ( beatOfMouseCursor >= 0 && midiNoteNumberOfMouseCurser >= 0 ) {
						if ( score.grid[beatOfMouseCursor][midiNoteNumberOfMouseCurser-score.midiNoteNumberOfLowestPitch] != false ) {
							score.grid[beatOfMouseCursor][midiNoteNumberOfMouseCurser-score.midiNoteNumberOfLowestPitch] = false;
							repaint();
						}
					}

				}
				else{
					paint( mouse_x, mouse_y );
				}
			}
		}
	}

	public void mouseReleased( MouseEvent e ) {
		old_mouse_x = mouse_x;
		old_mouse_y = mouse_y;
		mouse_x = e.getX();
		mouse_y = e.getY();

		isControlKeyDown = e.isControlDown();
		isAltKeyDown = e.isAltDown();

		if ( radialMenu.isVisible() ) {
			int returnValue = radialMenu.releaseEvent( mouse_x, mouse_y );

			int itemID = radialMenu.getIDOfSelection();
			if ( 0 <= itemID ) {
				switch ( itemID ) {
				case RADIAL_MENU_PLAY:
					simplePianoRoll.setMusicPlaying( true );
					break;
				case RADIAL_MENU_STOP:
					simplePianoRoll.setMusicPlaying( false );
					break;
				case RADIAL_MENU_DRAW:
					simplePianoRoll.setDragMode( SimplePianoRoll.DM_DRAW_NOTES );
					break;
				case RADIAL_MENU_ERASE:
					simplePianoRoll.setDragMode( SimplePianoRoll.DM_ERASE_NOTES );
					break;
				}
			}

			if ( returnValue == CustomWidget.S_REDRAW )
				repaint();
			if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
				return;
		}
		if ( controlMenu.isVisible() ) {
			int returnValue = controlMenu.releaseEvent( mouse_x, mouse_y );

			if( !metronone.metrononeThreadSuspended )
				metronone.stopBackgroundWork();
			if ( returnValue == CustomWidget.S_REDRAW )
				repaint();
			if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
				return;

		}
		if(controlTranslate.isVisible()){
			selection = null;
			altGrid.clear();
			original.clear();
			int returnValue = controlTranslate.releaseEvent( mouse_x, mouse_y );
			if ( returnValue == CustomWidget.S_REDRAW )
				repaint();
			if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
				return;

		}
		//Added
		if (isAltKeyDown){
			if(selection != null){
				Point2D rectStart = gw.convertPixelsToWorldSpaceUnits(new Point2D(selection.x,selection.y));
				Point2D rectEnd   = gw.convertPixelsToWorldSpaceUnits(new Point2D(selection.x + selection.width, selection.y + selection.height));

				altGrid.clear();

				for ( int y = 0; y < score.numPitches; ++y ) {
					for ( int x = 0; x <  score.numBeats ; ++x ) {
						if(score.grid[x][y]){
							if((x >= Math.min(rectStart.x(),rectEnd.x()) && x <= Math.max(rectStart.x(),rectEnd.x())) && 
									(y >= Math.min(Math.abs(rectStart.y()-1),Math.abs(rectEnd.y()-1)) && y <= Math.max(Math.abs(rectStart.y()-1),Math.abs(rectEnd.y()-1)) )){
								altGrid.add(new Point2D(x,y));
							}
						}
					}
				}		

				if(altGrid.isEmpty()){
					selection = null;
				}
				else{
					original = (ArrayList<Point2D>) altGrid.clone();
				}
			}
		}
		


	}

	private void playNote( int midiNoteNumber ) {
		if ( Constant.USE_SOUND && midiNoteNumber >= 0 ) {
			simplePianoRoll.midiChannels[0].noteOn(midiNoteNumber,Constant.midiVolume);
		}
	}
	private void stopPlayingNote( int midiNoteNumber ) {
		if ( Constant.USE_SOUND && midiNoteNumber >= 0 ) {
			simplePianoRoll.midiChannels[0].noteOff(midiNoteNumber);
		}
	}

	public void mouseMoved( MouseEvent e ) {
		old_mouse_x = mouse_x;
		old_mouse_y = mouse_y;
		mouse_x = e.getX();
		mouse_y = e.getY();

		requestFocusInWindow();

		isControlKeyDown = e.isControlDown();

		if ( radialMenu.isVisible() ) {
			int returnValue = radialMenu.moveEvent( mouse_x, mouse_y );
			if ( returnValue == CustomWidget.S_REDRAW )
				repaint();
			if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
				return;
		}
		if ( controlMenu.isVisible() ) {
			int returnValue = controlMenu.moveEvent( mouse_x, mouse_y );
			if ( returnValue == CustomWidget.S_REDRAW )
				repaint();
			if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
				return;
		}
		if ( controlTranslate.isVisible() ) {
			int returnValue = controlTranslate.moveEvent( mouse_x, mouse_y );
			if ( returnValue == CustomWidget.S_REDRAW )
				repaint();
			if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
				return;
		}
		else {
			int newBeatOfMouseCursor = score.getBeatForMouseX( gw, mouse_x );
			int newMidiNoteNumberOfMouseCurser = score.getMidiNoteNumberForMouseY( gw, mouse_y );
			if ( newBeatOfMouseCursor != beatOfMouseCursor ) {
				beatOfMouseCursor = newBeatOfMouseCursor;
				repaint();
			}
			if ( newMidiNoteNumberOfMouseCurser != midiNoteNumberOfMouseCurser ) {
				stopPlayingNote( midiNoteNumberOfMouseCurser );
				midiNoteNumberOfMouseCurser = newMidiNoteNumberOfMouseCurser;
				if (
						beatOfMouseCursor>=0
						&& (
								simplePianoRoll.rolloverMode == SimplePianoRoll.RM_PLAY_NOTE_UPON_ROLLOVER
								|| (
										simplePianoRoll.rolloverMode == SimplePianoRoll.RM_PLAY_NOTE_UPON_ROLLOVER_IF_SPECIAL_KEY_HELD_DOWN
										&& isControlKeyDown
										)
								)
						)
					playNote( midiNoteNumberOfMouseCurser );
				repaint();
			}
		}

	}

	public void mouseDragged( MouseEvent e ) {
		old_mouse_x = mouse_x;
		old_mouse_y = mouse_y;
		mouse_x = e.getX();
		mouse_y = e.getY();
		int delta_x = mouse_x - old_mouse_x;
		int delta_y = mouse_y - old_mouse_y;

		isControlKeyDown = e.isControlDown();
		isAltKeyDown = e.isAltDown();

		if ( radialMenu.isVisible() ) {
			int returnValue = radialMenu.dragEvent( mouse_x, mouse_y );
			if ( returnValue == CustomWidget.S_REDRAW )
				repaint();
			if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
				return;
		}
		if ( controlMenu.isVisible() ) {
			if ( controlMenu.isInMenuingMode() ) {
				int returnValue = controlMenu.dragEvent( mouse_x, mouse_y );
				if ( returnValue == CustomWidget.S_REDRAW )
					repaint();
				if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
					return;
			}
			else {
				// use the drag event to change the appropriate parameter
				switch ( controlMenu.getIDOfSelection() ) {
				case CONTROL_MENU_PAN:
					gw.pan( delta_x, delta_y );
					break;
				case CONTROL_MENU_ZOOM:
					gw.zoomIn( (float)Math.pow( Constant.zoomFactorPerPixelDragged, delta_x-delta_y ) );
					break;
				case CONTROL_MENU_TOTAL_DURATION:
					float scale = score.numBeats + delta_x;
					if(scale >= 128){
						score.increaseNumBeat((int)scale);
						if(this.simplePianoRoll.isAutoFrameActive){
							gw.frame(score.getBoundingRectangle(), true);
						}
					}
					break;
				case CONTROL_MENU_TEMPO:
					int tempo = score.tempo + delta_x;
					if(tempo > 0){
						score.tempo = tempo;
						if(metronone.metrononeThreadSuspended){
							metronone.startBackgroundWork();
						}
					}
					break;
				default:
					// TODO XXX
					break;
				}
				repaint();
			}
		}
		
		else if(controlTranslate.isVisible()){
			if ( controlTranslate.isInMenuingMode() ) {
				int returnValue = controlTranslate.dragEvent( mouse_x, mouse_y );
				if ( returnValue == CustomWidget.S_REDRAW )
					repaint();
				if ( returnValue != CustomWidget.S_EVENT_NOT_CONSUMED )
					return;
			}
			else{
				switch ( controlTranslate.getIDOfSelection() ) {
					case CONTROL_MENU_T_TRANSLATE:
						translate(delta_x,delta_y);
					break;
					case CONTROL_MENU_T_COPY:
						translate(delta_x,delta_y);
						for(int i = 0; i < original.size();i++){
							score.grid[(int) original.get(i).x()][(int) original.get(i).y()] = true;
						}
					break;
				}
				repaint();
			}
		}
		else if(isAltKeyDown){				
			if(SwingUtilities.isLeftMouseButton(e)){
				int x = (int) Math.min(rectAnchor.x(), mouse_x);
				int y = (int) Math.min(rectAnchor.y(), mouse_y);
				int width = (int) Math.max(rectAnchor.x() - mouse_x, mouse_x - rectAnchor.x());
				int height = (int) Math.max(rectAnchor.y() - mouse_y, mouse_y - rectAnchor.y());

				selection = new Rectangle(x,y,width,height);
				repaint();
			}
		}
		

		else {
			int midiNoteNumberOfMouseCurser = score.getMidiNoteNumberForMouseY(gw, mouse_y);
			int pitchClass = ( midiNoteNumberOfMouseCurser - score.midiNoteNumberOfLowestPitch + score.pitchClassOfLowestPitch )% score.numPitchesInOctave;

			if(simplePianoRoll.customSelected){
				if(score.pitchClassesCustomScale[pitchClass]){
					paint( mouse_x, mouse_y );
				}
			}
			else if(simplePianoRoll.orientalSelected){
				if(score.pitchClassesInOrientalScale[pitchClass]){
					paint( mouse_x, mouse_y );
				}
			}
			else if(simplePianoRoll.pentatonicSelected){
				if(score.pitchClassesInMajorPentatonicScale[pitchClass]){
					paint( mouse_x, mouse_y );
				}
			}
			else if(simplePianoRoll. gMinorSelected){
				if(score.pitchClassesGMinorPentatonicScale[pitchClass]){
					paint( mouse_x, mouse_y );
				}
			}
			//Added

			else {
				paint( mouse_x, mouse_y );
			}
		}
		
		
		
		
	}
	public void translate(int delta_x, int delta_y){
		Point2D originalAnchor = gw.convertPixelsToWorldSpaceUnits(new Point2D(selection.x, selection.y));
		selection.translate(delta_x,delta_y);
		Point2D updatedAnchor = gw.convertPixelsToWorldSpaceUnits(new Point2D(selection.x, selection.y));
		ArrayList<Point2D> temp = new ArrayList<Point2D>();					
		for(int i = 0; i < altGrid.size(); i++){
			Point2D pt = altGrid.get(i);
			
			score.grid[(int)pt.x()][(int)pt.y()] = false;
			

			float distance_x = pt.x() - originalAnchor.x() ;
			float distance_y = (float) (Math.ceil(Math.abs(originalAnchor.y())) - pt.y());
			
			int x = (int) (Math.ceil(updatedAnchor.x()) + Math.floor(distance_x));
			int y = (int) (Math.ceil(Math.abs(updatedAnchor.y())) -Math.floor( distance_y));
			//System.out.println("X : " + " Anchor O : " + originalAnchor.x() + " Anchor U : " + updatedAnchor.x()+ " Distance x : "+ distance_x +" OLD "+pt.x()+" NEW "+x);
			//System.out.println("Y : " + " Anchor O : " + Math.abs(originalAnchor.y()) + " Anchor U : " + Math.abs(updatedAnchor.y())+ " Distance y : "+ distance_y +" OLD "+pt.y()+" NEW "+y);
			//System.out.println("Y : " + Math.abs(updatedAnchor.y())+ " "+ distance_y +" "+y);
			//System.out.println("X : " + originalAnchor.x() + " " + pt.x() + " " + distance_x);
			if((x >= 0 && x < score.numBeats) && (y >=0 && y < score.numPitches)){
				temp.add(new Point2D(x,y));
				score.grid[x][y] = true;
			}
		}
		altGrid.clear();
		altGrid = temp;
	}
	public void startBackgroundWork() {
		currentBeat = 0;
		if ( thread == null ) {
			thread = new Thread( this );
			threadSuspended = false;
			thread.start();
		}
		else {
			if ( threadSuspended ) {
				threadSuspended = false;
				synchronized( this ) {
					notify();
				}
			}
		}
	}
	public void stopBackgroundWork() {
		threadSuspended = true;
	}
	public void run() {
		try {
			while (true) {

				// Here's where the thread does some work
				synchronized( this ) {
					if ( Constant.USE_SOUND ) {
						for ( int i = 0; i < score.numPitches; ++i ) {
							if ( score.grid[currentBeat][i] ){
								simplePianoRoll.midiChannels[0].noteOff( i+score.midiNoteNumberOfLowestPitch );
								//score.isReading = false;
							}	
						}
					}
					currentBeat += 1;
					if ( currentBeat >= score.numBeats )
						currentBeat = 0;
					if ( Constant.USE_SOUND ) {
						score.readingPositionY.clear();
						for ( int i = 0; i < score.numPitches; ++i ) {
							if ( score.grid[currentBeat][i] ){
								simplePianoRoll.midiChannels[0].noteOn( i+score.midiNoteNumberOfLowestPitch, Constant.midiVolume );
								score.isReading = true;

								score.ColoredPoints.add(new ColoredPoint(currentBeat,i,0,1));
								//score.readingPositionX = currentBeat;
								//score.readingPositionY.add(i);
							}
						}
					}

				}
				repaint();

				// Now the thread checks to see if it should suspend itself
				if ( threadSuspended ) {
					synchronized( this ) {
						while ( threadSuspended ) {
							wait();
						}
					}
				}
				thread.sleep( score.tempo );  // interval given in milliseconds
			}
		}
		catch (InterruptedException e) { }
	}


	public class Metronone implements Runnable{
		Thread metrononeThread = null;
		boolean metrononeThreadSuspended = true;

		public void startBackgroundWork() {
			if ( metrononeThread == null ) {
				metrononeThread = new Thread( this );
				metrononeThreadSuspended = false;
				metrononeThread.start();
			}
			else {
				if ( metrononeThreadSuspended ) {
					metrononeThreadSuspended = false;
					synchronized( this ) {
						notify();
					}
				}
			}
		}

		public void stopBackgroundWork() {
			metrononeThreadSuspended = true;
		}

		@Override
		public void run() {
			try {
				while(true){
					synchronized(this){
						simplePianoRoll.midiChannels[0].noteOn( 88+score.midiNoteNumberOfLowestPitch, Constant.midiVolume );
					}
					// Now the thread checks to see if it should suspend itself
					if ( metrononeThreadSuspended ) {
						synchronized( this ) {
							while ( metrononeThreadSuspended ) {
								wait();
							}
						}
					}

					metrononeThread.sleep( score.tempo );  // interval given in milliseconds
				}

			}
			catch (InterruptedException e) { }
		}
	}

	public void generateBeat() {
		Random rand = new Random();

		int [] limits = new int[7];
		limits[ 0] = 15;
		limits[ 1] = 27;
		limits[ 2] = 39;
		limits[ 3] = 51;
		limits[ 4] = 63;
		limits[ 5] = 75;
		limits[ 6] = 87;

		int low = 0;
		int high = 6;

		int random_line = rand.nextInt(high-low)+low;
		int upper_bound = limits[random_line];
		int lower_bound = upper_bound - 12;

		for(int x = 0; x < score.numBeats; x++){
			int random_num_notes = rand.nextInt(5)-1;
			for(int y = 0; y < random_num_notes; y++){
				int random_y = rand.nextInt(upper_bound-lower_bound)+lower_bound;
				int pitchClass = (random_y + score.pitchClassOfLowestPitch)%score.numPitchesInOctave;

				if(simplePianoRoll.customSelected){
					if ( score.pitchClassesCustomScale[ pitchClass ] ) {
						score.grid[x][random_y] = true;
					}
				}
				else if(simplePianoRoll.gMinorSelected){
					if ( score.pitchClassesGMinorPentatonicScale[ pitchClass ] ) {
						score.grid[x][random_y] = true;
					}
				}
				else if(simplePianoRoll.orientalSelected){
					if ( score.pitchClassesInOrientalScale[ pitchClass ] ) {
						score.grid[x][random_y] = true;
					}
				}
				else if(simplePianoRoll.pentatonicSelected){
					if ( score.pitchClassesInMajorPentatonicScale[ pitchClass ] ) {
						score.grid[x][random_y] = true;
					}
				}
				else if(simplePianoRoll.randomSelected){
					score.grid[x][random_y] = true;
				}
			}
		}
	}
}

public class SimplePianoRoll implements ActionListener {

	static final String applicationName = "Simple Piano Roll";

	JFrame frame;
	Container toolPanel;
	MyCanvas canvas;

	Synthesizer synthesizer;
	MidiChannel [] midiChannels;

	JMenuItem clearMenuItem;
	JMenuItem loadMidiItem;
	JMenuItem saveMidiItem;
	JMenuItem loadItem;
	JMenuItem saveItem;
	JMenuItem quitMenuItem;
	JCheckBoxMenuItem showToolsMenuItem;
	JCheckBoxMenuItem highlightMajorScaleMenuItem;
	JMenuItem frameAllMenuItem;
	JCheckBoxMenuItem autoFrameMenuItem;
	JMenuItem aboutMenuItem;
	JCheckBoxMenuItem randomNotesMenuItem;
	JCheckBoxMenuItem orientalNotesMenutItem;
	JCheckBoxMenuItem pentatonicNotesMenutItem;
	JCheckBoxMenuItem customNotesMenutItem;
	JCheckBoxMenuItem gMinorNotesMenuItem;
	JMenuItem generateMusicMenuItem;


	JCheckBox playCheckBox;
	JCheckBox loopWhenPlayingCheckBox;

	JRadioButton drawNotesRadioButton;
	JRadioButton eraseNotesRadioButton;

	JRadioButton doNothingUponRolloverRadioButton;
	JRadioButton playNoteUponRolloverRadioButton;
	JRadioButton playNoteUponRolloverIfSpecialKeyHeldDownRadioButton;

	public boolean isMusicPlaying = false;
	public boolean isMusicLoopedWhenPlayed = false;
	public boolean highlightMajorScale = true;
	public boolean isAutoFrameActive = true;
	public boolean orientalSelected = false;
	public boolean pentatonicSelected = false;
	public boolean customSelected = false;
	public boolean gMinorSelected = false;
	public boolean randomSelected = true;

	// The DM_ prefix is for Drag Mode
	public static final int DM_DRAW_NOTES = 0;
	public static final int DM_ERASE_NOTES = 1;
	public int dragMode = DM_DRAW_NOTES;

	// The RM_ prefix is for Rollover Mode
	public static final int RM_DO_NOTHING_UPON_ROLLOVER = 0;
	public static final int RM_PLAY_NOTE_UPON_ROLLOVER = 1;
	public static final int RM_PLAY_NOTE_UPON_ROLLOVER_IF_SPECIAL_KEY_HELD_DOWN = 2;
	public int rolloverMode = RM_DO_NOTHING_UPON_ROLLOVER;

	public void setMusicPlaying( boolean flag ) {
		isMusicPlaying = flag;
		playCheckBox.setSelected( isMusicPlaying );
		if ( isMusicPlaying )
			canvas.startBackgroundWork();
		else
			canvas.stopBackgroundWork();
	}
	public void setDragMode( int newDragMode ) {
		dragMode = newDragMode;
		if ( dragMode == DM_DRAW_NOTES )
			drawNotesRadioButton.setSelected(true);
		else if ( dragMode == DM_ERASE_NOTES )
			eraseNotesRadioButton.setSelected(true);
		else assert false;
	}

	public void setRolloverMode( int newRolloverMode ) {
		rolloverMode = newRolloverMode;
		if ( rolloverMode == RM_DO_NOTHING_UPON_ROLLOVER )
			doNothingUponRolloverRadioButton.setSelected(true);
		else if ( rolloverMode == RM_PLAY_NOTE_UPON_ROLLOVER )
			playNoteUponRolloverRadioButton.setSelected(true);
		else if ( rolloverMode == RM_PLAY_NOTE_UPON_ROLLOVER_IF_SPECIAL_KEY_HELD_DOWN )
			playNoteUponRolloverIfSpecialKeyHeldDownRadioButton.setSelected(true);
		else assert false;
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if ( source == clearMenuItem ) {
			canvas.clear();
		}
		else if(source == loadMidiItem){
			loadFile(0);
		}
		else if(source == saveMidiItem){
			saveFile(0);
		}
		else if(source == loadItem){
			loadFile(1);
		}
		else if(source == saveItem){
			saveFile(1);
		}
		else if ( source == quitMenuItem ) {
			int response = JOptionPane.showConfirmDialog(
					frame,
					"Really quit?",
					"Confirm Quit",
					JOptionPane.YES_NO_OPTION
					);

			if (response == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
		}
		else if ( source == showToolsMenuItem ) {
			Container pane = frame.getContentPane();
			if ( showToolsMenuItem.isSelected() ) {
				pane.removeAll();
				pane.add( toolPanel );
				pane.add( canvas );
			}
			else {
				pane.removeAll();
				pane.add( canvas );
			}
			frame.invalidate();
			frame.validate();
		}
		else if (source == randomNotesMenuItem){
			randomSelected = randomNotesMenuItem.isSelected();
			orientalNotesMenutItem.setSelected(false);
			orientalSelected = false;
			pentatonicNotesMenutItem.setSelected(false);
			pentatonicSelected = false;
			customNotesMenutItem.setSelected(false);
			customSelected = false;
			gMinorNotesMenuItem.setSelected(false);
			gMinorSelected = false;
			canvas.clear();
			canvas.repaint();
		}
		else if (source == orientalNotesMenutItem){
			orientalSelected = orientalNotesMenutItem.isSelected();
			randomNotesMenuItem.setSelected(false);
			randomSelected = false;
			pentatonicNotesMenutItem.setSelected(false);
			pentatonicSelected = false;
			customNotesMenutItem.setSelected(false);
			customSelected = false;
			gMinorNotesMenuItem.setSelected(false);
			gMinorSelected = false;
			canvas.clear();
			canvas.repaint();
		}
		else if (source == pentatonicNotesMenutItem){
			pentatonicSelected = pentatonicNotesMenutItem.isSelected();
			randomNotesMenuItem.setSelected(false);
			randomSelected = false;
			orientalNotesMenutItem.setSelected(false);
			orientalSelected = false;
			customNotesMenutItem.setSelected(false);
			customSelected = false;
			gMinorNotesMenuItem.setSelected(false);
			gMinorSelected = false;
			canvas.clear();
			canvas.repaint();
		}
		else if (source == customNotesMenutItem){
			customSelected = customNotesMenutItem.isSelected();
			randomNotesMenuItem.setSelected(false);
			randomSelected = false;
			orientalNotesMenutItem.setSelected(false);
			orientalSelected = false;
			pentatonicNotesMenutItem.setSelected(false);
			pentatonicSelected = false;
			gMinorNotesMenuItem.setSelected(false);
			gMinorSelected = false;
			canvas.clear();
			canvas.repaint();
		}
		else if (source == gMinorNotesMenuItem){
			customNotesMenutItem.setSelected(false);
			customSelected = false;
			randomNotesMenuItem.setSelected(false);
			randomSelected = false;
			orientalNotesMenutItem.setSelected(false);
			orientalSelected = false;
			pentatonicNotesMenutItem.setSelected(false);
			pentatonicSelected = false;
			gMinorSelected = gMinorNotesMenuItem.isSelected();
			canvas.clear();
			canvas.repaint();
		}
		else if (source == generateMusicMenuItem){//generateMusicMenuItem
			canvas.clear();
			canvas.generateBeat();
			canvas.repaint();
		}
		else if ( source == highlightMajorScaleMenuItem ) {
			highlightMajorScale = highlightMajorScaleMenuItem.isSelected();
			canvas.repaint();
		}
		else if ( source == frameAllMenuItem ) {
			canvas.frameAll();
			canvas.repaint();
		}
		else if ( source == autoFrameMenuItem ) {
			isAutoFrameActive = autoFrameMenuItem.isSelected();
			canvas.repaint();
		}
		else if ( source == aboutMenuItem ) {
			JOptionPane.showMessageDialog(
					frame,
					"'" + applicationName + "' Sample Program\n"
							+ "Original version written April 2011",
							"About",
							JOptionPane.INFORMATION_MESSAGE
					);
		}
		else if ( source == playCheckBox ) {
			isMusicPlaying = playCheckBox.isSelected();
			if ( isMusicPlaying )
				canvas.startBackgroundWork();
			else
				canvas.stopBackgroundWork();
		}
		else if ( source == loopWhenPlayingCheckBox ) {
			isMusicLoopedWhenPlayed = loopWhenPlayingCheckBox.isSelected();
		}
		else if ( source == drawNotesRadioButton ) {
			dragMode = DM_DRAW_NOTES;
		}
		else if ( source == eraseNotesRadioButton ) {
			dragMode = DM_ERASE_NOTES;
		}
		else if ( source == doNothingUponRolloverRadioButton ) {
			rolloverMode = RM_DO_NOTHING_UPON_ROLLOVER;
		}
		else if ( source == playNoteUponRolloverRadioButton ) {
			rolloverMode = RM_PLAY_NOTE_UPON_ROLLOVER;
		}
		else if ( source == playNoteUponRolloverIfSpecialKeyHeldDownRadioButton ) {
			rolloverMode = RM_PLAY_NOTE_UPON_ROLLOVER_IF_SPECIAL_KEY_HELD_DOWN;
		}

	}

	private void saveFile(int type){
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"GG File", "gg");
		if(type == 0){
			filter = new FileNameExtensionFilter(
					"Midi File", "midi");
		}
		chooser.setFileFilter(filter);
		int returnVal = chooser.showSaveDialog(canvas);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String file = chooser.getSelectedFile().getAbsolutePath();

			switch(type){
			case 0:
				if(!file.endsWith(".midi")){
					file += ".midi";
				}
				MIDIType ee = new MIDIType();
				ee.save(canvas.score,file);
				break;
			case 1:
				if(!file.endsWith(".gg")){
					file += ".gg";
				}
				HomeType ht = new HomeType();
				ht.save(canvas.score, file);
				break;
			}
	        JOptionPane.showMessageDialog(null, file + " has been saved.", "Save File", JOptionPane.PLAIN_MESSAGE);

		}
	}

	private void loadFile(int type){
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				"GG File", "gg");
		if(type == 0){
			filter = new FileNameExtensionFilter(
					"Midi File", "midi");
		}
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(canvas);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String file = chooser.getSelectedFile().getAbsolutePath();
			Score s = null;
			switch(type){
			case 0:
				MIDIType ee = new MIDIType();
				s = ee.load(file);
				break;
			case 1:
				HomeType ht = new HomeType();
				s = ht.load(file);
				break;
			}
		
			canvas.score.increaseNumBeat(s.numBeats);
			canvas.score.grid = s.grid;
			canvas.repaint();
			JOptionPane.showMessageDialog(null, file + " has been loaded.", "Load File", JOptionPane.PLAIN_MESSAGE);			
		}
	}


	// For thread safety, this should be invoked
	// from the event-dispatching thread.
	//
	private void createUI() {
		if ( Constant.USE_SOUND ) {
			try {
				synthesizer = MidiSystem.getSynthesizer();
				synthesizer.open();
				midiChannels = synthesizer.getChannels();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		if ( ! SwingUtilities.isEventDispatchThread() ) {
			System.out.println(
					"Warning: UI is not being created in the Event Dispatch Thread!");
			assert false;
		}

		frame = new JFrame( applicationName );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );



		JMenuBar menuBar = new JMenuBar();

		//-------------------FILE-------------------------------//
		JMenu menu = new JMenu("File");

		loadItem = new JMenuItem("Load");
		loadItem.addActionListener(this);
		menu.add(loadItem);

		saveItem = new JMenuItem("Save");
		saveItem.addActionListener(this);
		menu.add(saveItem);

		menu.addSeparator();

		loadMidiItem = new JMenuItem("Load MIDI");
		loadMidiItem.addActionListener(this);
		menu.add(loadMidiItem);

		saveMidiItem = new JMenuItem("Save MIDI");
		saveMidiItem.addActionListener(this);
		menu.add(saveMidiItem);

		menu.addSeparator();
		clearMenuItem = new JMenuItem("Clear");
		clearMenuItem.addActionListener(this);
		menu.add(clearMenuItem);

		menu.addSeparator();

		quitMenuItem = new JMenuItem("Quit");
		quitMenuItem.addActionListener(this);
		menu.add(quitMenuItem);
		menuBar.add(menu);

		//-------------------VIEW-------------------------------//
		menu = new JMenu("View");
		showToolsMenuItem = new JCheckBoxMenuItem("Show Options");
		showToolsMenuItem.setSelected( true );
		showToolsMenuItem.addActionListener(this);
		menu.add(showToolsMenuItem);

		highlightMajorScaleMenuItem = new JCheckBoxMenuItem("Highlight Major C Scale");
		highlightMajorScaleMenuItem.setSelected( highlightMajorScale );
		highlightMajorScaleMenuItem.addActionListener(this);
		menu.add(highlightMajorScaleMenuItem);

		menu.addSeparator();

		frameAllMenuItem = new JMenuItem("Frame All");
		frameAllMenuItem.addActionListener(this);
		menu.add(frameAllMenuItem);

		autoFrameMenuItem = new JCheckBoxMenuItem("Auto Frame");
		autoFrameMenuItem.setSelected( isAutoFrameActive );
		autoFrameMenuItem.addActionListener(this);
		menu.add(autoFrameMenuItem);
		menuBar.add(menu);

		//-------------------OPTIONS-------------------------------//
		menu = new JMenu("Options");
		randomNotesMenuItem = new JCheckBoxMenuItem("Play any type of sound");
		randomNotesMenuItem.setSelected(true);
		randomNotesMenuItem.addActionListener(this);
		menu.add(randomNotesMenuItem);

		orientalNotesMenutItem = new JCheckBoxMenuItem("Play oriental sound");
		orientalNotesMenutItem.addActionListener(this);
		menu.add(orientalNotesMenutItem);

		pentatonicNotesMenutItem = new JCheckBoxMenuItem("Play pentatonic sound");
		pentatonicNotesMenutItem.addActionListener(this);
		menu.add(pentatonicNotesMenutItem);

		customNotesMenutItem = new JCheckBoxMenuItem("Play custom harmonious sound");
		customNotesMenutItem.addActionListener(this);
		menu.add(customNotesMenutItem);

		gMinorNotesMenuItem = new JCheckBoxMenuItem("Play G minor pentatonic sound");
		gMinorNotesMenuItem.addActionListener(this);
		menu.add(gMinorNotesMenuItem);

		menu.addSeparator();

		generateMusicMenuItem = new JMenuItem("Generate random music");
		generateMusicMenuItem.addActionListener(this);
		menu.add(generateMusicMenuItem);

		menuBar.add(menu);

		//-------------------HELP-------------------------------//
		menu = new JMenu("Help");
		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(this);
		menu.add(aboutMenuItem);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);

		toolPanel = new JPanel();
		toolPanel.setLayout( new BoxLayout( toolPanel, BoxLayout.Y_AXIS ) );

		canvas = new MyCanvas(this);

		Container pane = frame.getContentPane();
		pane.setLayout( new BoxLayout( pane, BoxLayout.X_AXIS ) );
		pane.add( toolPanel );
		pane.add( canvas );

		playCheckBox = new JCheckBox("Play", isMusicPlaying );
		playCheckBox.setAlignmentX( Component.LEFT_ALIGNMENT );
		playCheckBox.addActionListener(this);
		toolPanel.add( playCheckBox );
		/*
		loopWhenPlayingCheckBox = new JCheckBox("Loop when playing", isMusicLoopedWhenPlayed );
		loopWhenPlayingCheckBox.setAlignmentX( Component.LEFT_ALIGNMENT );
		loopWhenPlayingCheckBox.addActionListener(this);
		toolPanel.add( loopWhenPlayingCheckBox );
		 */
		toolPanel.add( Box.createRigidArea(new Dimension(1,20)) );
		toolPanel.add( new JLabel("During dragging:") );

		ButtonGroup dragModeButtonGroup = new ButtonGroup();

		drawNotesRadioButton = new JRadioButton( "Draw Notes" );
		drawNotesRadioButton.setAlignmentX( Component.LEFT_ALIGNMENT );
		drawNotesRadioButton.addActionListener(this);
		if ( dragMode == DM_DRAW_NOTES ) drawNotesRadioButton.setSelected(true);
		toolPanel.add( drawNotesRadioButton );
		dragModeButtonGroup.add( drawNotesRadioButton );

		eraseNotesRadioButton = new JRadioButton( "Erase Notes" );
		eraseNotesRadioButton.setAlignmentX( Component.LEFT_ALIGNMENT );
		eraseNotesRadioButton.addActionListener(this);
		if ( dragMode == DM_ERASE_NOTES ) eraseNotesRadioButton.setSelected(true);
		toolPanel.add( eraseNotesRadioButton );
		dragModeButtonGroup.add( eraseNotesRadioButton );

		toolPanel.add( Box.createRigidArea(new Dimension(1,20)) );
		toolPanel.add( new JLabel("Upon cursor rollover:") );

		ButtonGroup rolloverModeButtonGroup = new ButtonGroup();

		doNothingUponRolloverRadioButton = new JRadioButton( "Do Nothing" );
		doNothingUponRolloverRadioButton.setAlignmentX( Component.LEFT_ALIGNMENT );
		doNothingUponRolloverRadioButton.addActionListener(this);
		if ( rolloverMode == RM_DO_NOTHING_UPON_ROLLOVER ) doNothingUponRolloverRadioButton.setSelected(true);
		toolPanel.add( doNothingUponRolloverRadioButton );
		rolloverModeButtonGroup.add( doNothingUponRolloverRadioButton );

		playNoteUponRolloverRadioButton = new JRadioButton( "Play Pitch" );
		playNoteUponRolloverRadioButton.setAlignmentX( Component.LEFT_ALIGNMENT );
		playNoteUponRolloverRadioButton.addActionListener(this);
		if ( rolloverMode == RM_PLAY_NOTE_UPON_ROLLOVER ) playNoteUponRolloverRadioButton.setSelected(true);
		toolPanel.add( playNoteUponRolloverRadioButton );
		rolloverModeButtonGroup.add( playNoteUponRolloverRadioButton );

		playNoteUponRolloverIfSpecialKeyHeldDownRadioButton = new JRadioButton( "Play Pitch if Ctrl down" );
		playNoteUponRolloverIfSpecialKeyHeldDownRadioButton.setAlignmentX( Component.LEFT_ALIGNMENT );
		playNoteUponRolloverIfSpecialKeyHeldDownRadioButton.addActionListener(this);
		if ( rolloverMode == RM_PLAY_NOTE_UPON_ROLLOVER_IF_SPECIAL_KEY_HELD_DOWN )
			playNoteUponRolloverIfSpecialKeyHeldDownRadioButton.setSelected(true);
		toolPanel.add( playNoteUponRolloverIfSpecialKeyHeldDownRadioButton );
		rolloverModeButtonGroup.add( playNoteUponRolloverIfSpecialKeyHeldDownRadioButton );

		frame.pack();
		frame.setVisible( true );

		assert canvas.isFocusable();

	}

	public static void main( String[] args ) {
		// Schedule the creation of the UI for the event-dispatching thread.
		javax.swing.SwingUtilities.invokeLater(
				new Runnable() {
					public void run() {
						SimplePianoRoll sp = new SimplePianoRoll();
						sp.createUI();
					}
				}
				);
	}
}

