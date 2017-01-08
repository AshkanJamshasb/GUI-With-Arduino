import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.fazecast.jSerialComm.SerialPort;

public class Motors {

//	private static JButton on;
//	private static JButton off;
//	private static int onORoff;
	private static int redVal;
	private static int greenVal;
	private static int blueVal;
	private static int strobeVal;
	
	static SerialPort chosenPort;

	public static void main(String[] args) {
		

		// create and configure the window
		JFrame window = new JFrame();
		window.setTitle("Light Show");
		window.setSize(400, 178);
		window.setLayout(new BorderLayout());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// create a drop-down box and connect button, then place them at the top of the window
		JComboBox<String> portList = new JComboBox<String>();
		JButton connectButton = new JButton("Connect");
		
//		on = new JButton("On");
//		off = new JButton("Off");
		
		JPanel topPanel = new JPanel();
		JPanel colors = new JPanel();
		JPanel redLine = new JPanel(true);
		JPanel greenLine = new JPanel(true);
		JPanel blueLine = new JPanel(true);
		JPanel strobeLine = new JPanel(true);

		
		
		//Labels for colors
		JLabel red = new JLabel();
		JLabel green = new JLabel();
		JLabel blue = new JLabel();
		JLabel strobe = new JLabel();
		red.setText("Red  ");
		green.setText("Green  ");
		blue.setText("Blue  ");
		strobe.setText("Strobe ");
		
		//Organize them
		colors.add(redLine);
		colors.add(greenLine);
		colors.add(blueLine);
		colors.add(strobeLine);
		
		//Add colors
		topPanel.setBackground(Color.CYAN);
		colors.setBackground(Color.CYAN);
		redLine.setBackground(Color.RED);
		greenLine.setBackground(Color.GREEN);
		blueLine.setBackground(Color.BLUE);
		
		//Add them
		redLine.add(red);
		greenLine.add(green);
		blueLine.add(blue);
		strobeLine.add(strobe);

		
		JSlider strobeSlider = new JSlider();
		strobeSlider.setMaximum(100);
		strobeSlider.setSize(20, 10);
		strobeSlider.setValue(0);
		strobeLine.add(strobeSlider);
		
		JSlider redSlider = new JSlider();
		redSlider.setMaximum(100);
		redSlider.setSize(20, 10);
		redSlider.setValue(0);
		redLine.add(redSlider);
		
		JSlider greenSlider = new JSlider();
		greenSlider.setMaximum(100);
		greenSlider.setSize(20, 10);
		greenSlider.setValue(0);
		greenLine.add(greenSlider);
		
		JSlider blueSlider = new JSlider();
		blueSlider.setMaximum(100);
		blueSlider.setSize(20, 10);
		blueSlider.setValue(0);
		blueLine.add(blueSlider);
		
		JTextField redValue = new JTextField("0", 10);
		redValue.setEditable(false);
		redValue.setText("null");
		redLine.add(redValue);
		
		JTextField greenValue = new JTextField("0", 10);
		greenValue.setEditable(false);
		greenValue.setText("null");
		greenLine.add(greenValue);
		
		JTextField blueValue = new JTextField("0", 10);
		blueValue.setEditable(false);
		blueValue.setText("null");
		blueLine.add(blueValue);
		
		
		topPanel.add(portList);
		topPanel.add(connectButton);
		
		colors.setLayout(new BoxLayout(colors, BoxLayout.Y_AXIS));
		
		window.add(topPanel, BorderLayout.NORTH);
		window.add(colors, BorderLayout.CENTER);
		
		// populate the drop-down box
		SerialPort[] portNames = SerialPort.getCommPorts();
		for(int i = 0; i < portNames.length; i++)
			portList.addItem(portNames[i].getSystemPortName());
		
//		OnOrOff choice = new OnOrOff();
		
//		on.addActionListener(choice);
//		on.setBounds(50, 50, 29, 29);
//		centerPanel.add(on);
		
		
//		off.addActionListener(choice);
//		off.setBounds(90, 50, 29, 29);
//		centerPanel.add(off);
		
		// configure the connect button and use another thread to send data
		connectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				if(connectButton.getText().equals("Connect")) {
					// attempt to connect to the serial port
					chosenPort = SerialPort.getCommPort(portList.getSelectedItem().toString());
					chosenPort.setComPortTimeouts(SerialPort.TIMEOUT_SCANNER, 0, 0);
					if(chosenPort.openPort()) {
						connectButton.setText("Disconnect");
						portList.setEnabled(false);
						
						// create a new thread for sending data to the arduino
						Thread thread = new Thread(){
							public void run() {
								// wait after connecting, so the bootloader can finish
								try {Thread.sleep(100); } catch(Exception e) {}

								// enter an infinite loop that sends text to the arduino
								PrintWriter output = new PrintWriter(chosenPort.getOutputStream());
								while(true) {
//									output.print(onORoff+"");
									
									//Get values
									redVal = redSlider.getValue();
									greenVal = greenSlider.getValue();
									blueVal = blueSlider.getValue();
									strobeVal = strobeSlider.getValue();
									
									//Send the value
									output.print(redVal+"."+greenVal+"."+blueVal+"."+strobeVal+".");
									
									//Change the text box
									redValue.setText(redVal+"");
									greenValue.setText(greenVal+"");
									blueValue.setText(blueVal+"");
									
									output.flush();
									try {Thread.sleep(100); } catch(Exception e) {}
								}
							}
						};
						thread.start();
					}
				} else {
					// disconnect from the serial port
					chosenPort.closePort();
					portList.setEnabled(true);
					connectButton.setText("Connect");
				}
			}
		});
		
		// show the window
		window.setVisible(true);
		
	}
	
//	static void UpdateSlider(int val, JTextField value) {
//		
//		value.setText(val+"");
//	}
	
//	class SetBrightness implements ActionListener {
//		public void actionPerformed(ActionEvent event) {
//			JSlider src = (JSlider) event.getSource();
//			brightness = src.getValue();
//		}
//	}
//	private static class OnOrOff implements ActionListener{
//		public void actionPerformed(ActionEvent event){
//			JButton src = (JButton) event.getSource();
//			
//			if(src.equals(on)){
//				onORoff = 1;
//			}
//			if(src.equals(off)){
//				onORoff = 2;
//			}
//			
//		}
//	}

}