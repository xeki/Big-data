package BigDataSecondAssignment.SpamDetector;

public class EmailDistance implements Comparable<EmailDistance>{
	Double distance;
	int index;
	String emailType;

	public EmailDistance() {
		// TODO Auto-generated constructor stub
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public int compareTo(EmailDistance eDist) {
		return distance < eDist.distance ? -1 : distance > eDist.distance? 1 :0;
		
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

}
