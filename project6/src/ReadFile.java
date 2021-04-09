import java.io.IOException;
import java.io.*;
 

public class ReadFile {
	
	FileInputStream randFile;
	ObjectInputStream os;
	
	Accounts[] items = new Accounts[8];
	
	public void openFile() throws IOException, ClassNotFoundException
	{
		randFile = new FileInputStream("Accounts.dat");
		os = new ObjectInputStream(randFile);
				
		System.out.println("File is open for processing");
	}
	
	public void closeFile() throws IOException
	{
		randFile.close();
		os.close();
	}
	
	public void readFile() throws IOException, ClassNotFoundException
	{
		//Read one record
		for(int i = 0; i < items.length; i++)
		{
			items[i] = (Accounts) os.readObject();
		}
			
	}
	
	public void display() throws AccountException
	{
		for (int i = 0; i < 8; i++)
		{
			System.out.println("AcctID : " + items[i].getAcctID());
			System.out.println("Password : " + items[i].getPassword());
			System.out.println("SecurityQ: " + items[i].getSecurityQ());
			System.out.println("SecurityA: " + items[i].getSecurityA());
			System.out.println("Balance: " + items[i].getBalance());
			System.out.println("Status: " + items[i].getStatus());
			System.out.println("Limit: " + items[i].getLimit());
		}
	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, AccountException {
		
		ReadFile randRead = new ReadFile();
		
		randRead.openFile();
		randRead.readFile();
		randRead.closeFile();
		randRead.display();
		

	}

}

