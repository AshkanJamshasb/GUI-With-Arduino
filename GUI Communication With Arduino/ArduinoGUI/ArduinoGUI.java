import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class ArduinoGUI extends JFrame{

	private int count = 0;
	private JFrame frame;
	private JPanel contentPanel;
	private JTextField textField;
	private JButton add;
	private JButton sub;

	public static void main(String[] args) {
		ArduinoGUI c = new ArduinoGUI();
		c.setSize(300, 250);
		c.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		c.setVisible(true);
		c.setResizable(false);
	}
	
	public ArduinoGUI() {
		super("Counter");
		initialize();
		this.setContentPane(contentPanel);
	}

	private void initialize() {
		frame = new JFrame();
		contentPanel = new JPanel();
		textField = new JTextField("0", 20);
		add = new JButton("+");
		sub = new JButton("-");
		AddOrSubtract choice = new AddOrSubtract();
		
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		contentPanel.setBackground(Color.CYAN);
		
		textField.setEditable(false);
		contentPanel.add(textField, BorderLayout.NORTH);
		
		add.addActionListener(choice);
		add.setBounds(50, 50, 29, 29);
		contentPanel.add(add);
		
		sub.addActionListener(choice);
		sub.setBounds(90, 50, 29, 29);
		contentPanel.add(sub);
	}
	
	private class AddOrSubtract implements ActionListener{
		public void actionPerformed(ActionEvent event){
			JButton src = (JButton) event.getSource();
			
			if(src.equals(add)){
				count+=1;
				textField.setText(count+"");
			}
			if(src.equals(sub)){
				count-=1;
				textField.setText(count+"");
			}
		}
	}
}