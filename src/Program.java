import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.management.InstanceAlreadyExistsException;
import javax.swing.*;

public class Program extends JFrame {

	private int width = 400;
	private int height = 400;
	private int swaps = 0;
	private File file = new File("content.txt");;
	private double timeInSeconds = 0;
	private Random random = new Random();
	private ArrayList<Integer> randomInts = new ArrayList<Integer>();
	private ArrayList<String> content = new ArrayList<String>();
	private JList results = new JList();
	private JScrollPane scrollPane = new JScrollPane();
	private JButton sort = new JButton("Sort");

	public Program(){
		super("H&M - Results");
		setLayout(new BorderLayout());
		setSize(width, height);
		setResizable(false);
		setVisible(true);

		//northPanel
		JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		northPanel.add(sort);
		add(northPanel, BorderLayout.NORTH); 

		//southPanel
		JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		add(southPanel, BorderLayout.SOUTH);

		//westPanel
		JPanel westPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		add(westPanel, BorderLayout.WEST);

		//northPanel
		JPanel eastPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); 
		add(eastPanel, BorderLayout.EAST);

		//centerPanel
		scrollPane.setViewportView(results);
		add(scrollPane, BorderLayout.CENTER);

		try {
			readFromFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		sort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateNumbers();
				sortNumbers();
			}
		});		

	}

	public void generateNumbers(){
		for(int x = 0; x < 100; x++){
			int randomInt = random.nextInt(10000) + 1;
			randomInts.add(randomInt);
		}
	}

	public void sortNumbers(){
		swaps = 0;
		long startCount = System.currentTimeMillis();
		for (int x = 0; x < randomInts.size(); x++) {
			for (int z = randomInts.size() - 1; z > x; z--) {
				if (randomInts.get(x) > randomInts.get(z)) {
					swaps++;
					int tmp = randomInts.get(x);
					randomInts.set(x,randomInts.get(z)) ;
					randomInts.set(z,tmp);
				}
			}
		}
		long endCount = System.currentTimeMillis();
		long td = endCount - startCount;
		timeInSeconds = td / 1000.0;
		for(int i : randomInts){
			String iToS = String.valueOf(i);
			content.add(iToS);
		}
		String newContent = "Number of swaps: " + swaps + ", elapsed time: " + timeInSeconds;
		content.add(newContent);
		content.add("\n");
		readToFile();
		results.removeAll();
		results.setListData(content.toArray());
		randomInts.clear();
	}

	public void readFromFile() throws IOException{
		content.clear();
		randomInts.clear();
		if(file.exists()){
			FileReader in = new FileReader(file); 
			BufferedReader br = new BufferedReader(in); 
			String line;  

			while ((line=br.readLine()) != null){
				content.add(line);
			}
			content.add("\n");
			results.removeAll();
			results.setListData(content.toArray());
		}

	}

	public void readToFile(){
		if(file.exists() == false){
			try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		try { 
			FileWriter fileOut = new FileWriter(file); 
			PrintWriter out = new PrintWriter(fileOut); 
			for(String s : content){  
				out.println(s); 
			} 
			out.close(); 
		} 
		catch (IOException e1) {
			e1.printStackTrace();
		}  
	}

	public static void main(String[] args) {
		new Program();
	}

}
