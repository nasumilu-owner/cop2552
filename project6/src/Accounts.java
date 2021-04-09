
import java.io.Serializable;


public class Accounts implements Serializable {

	private int acctID;
	private String password;
	private String securityQ;
	private String securityA;
	private double balance;
	private char status;
	private double limit;

	Accounts(int a, String p, String sq, String sa, double b, char s, double l) {
		acctID = a;
		password = p;
		securityQ = sq;
		securityA = sa;
		balance = b;
		status = s;
		limit = l;
	}

	public int getAcctID() {
		return acctID;
	}

	public String getPassword() {
		return password;
	}

	public String getSecurityQ() {
		return securityQ;
	}

	public String getSecurityA() {
		return securityA;
	}

	public double getBalance() {
		return balance;
	}

	public char getStatus() {
		return status;
	}

	public double getLimit() {
		return limit;
	}

}
