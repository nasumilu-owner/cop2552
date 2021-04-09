import java.util.*;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.*;

public class WriteFile implements Serializable {
	private int acctID;
	private String password;
	private String securityQ;
	private String securityA;
	private double balance;
	private char status;
	private double limit;
	
	private String inputValue;
	FileOutputStream randFile;
	ObjectOutputStream os; 
	Accounts[] items = new Accounts[8];
	
	
	public void enter() throws IOException {
		for (int i = 0; i < 8; i++)
		{
			inputValue = JOptionPane.showInputDialog("Account ID: ");
			acctID = Integer.parseInt(inputValue);
			password = JOptionPane.showInputDialog("Password: ");
			securityQ = JOptionPane.showInputDialog("Security Question: ");
			securityA = JOptionPane.showInputDialog("Security Answer: ");
			inputValue = JOptionPane.showInputDialog("Balance = ");
			balance = Double.parseDouble(inputValue);
			inputValue = JOptionPane.showInputDialog("Status");
			status = inputValue.charAt(0);
			inputValue = JOptionPane.showInputDialog("Limit");
			limit = Double.parseDouble(inputValue);
			
			
			items[i] = new Accounts(acctID, password, securityQ, securityA, balance, status, limit);
			System.out.println(acctID + ' ' + password + ' ' + securityQ + ' ' + securityA
					 + ' ' + balance + ' ' + status + ' ' + limit);
			os.writeObject(items[i]);
		}
	}
	
	public void openFile() throws IOException {
		randFile = new FileOutputStream("Accounts.txt");
		os = new ObjectOutputStream(randFile);
	}
	
	public void writeFile() throws IOException {
		
		System.out.println(acctID + ' ' + password + ' ' + securityQ + ' ' + securityA
				 + ' ' + balance + ' ' + status + ' ' + limit);
		
		for (int i = 0; i < 8; i++)
			os.writeObject(items[i]);
	}
	
	public void closeFile() throws IOException {
		randFile.close();
		System.out.println("Processing complete!");
	}

	public static void main(String[] args) throws IOException {
		
		WriteFile wf = new WriteFile();
		
		wf.openFile();
		
		
		wf.enter();	
		
		//writeFile();
		wf.closeFile();
	}
}






 