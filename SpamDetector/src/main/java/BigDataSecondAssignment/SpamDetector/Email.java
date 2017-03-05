package BigDataSecondAssignment.SpamDetector;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

public class Email implements Comparable<EmailDistance>{
	private Multiset<String> emailSet;
	Double norm;
	String emailType;//spam or ham
	int bucketIndex;// dataset name
	int [] hyperPlaneValue;
	Double distance;
	
	public Email() {
		emailSet = HashMultiset.create();
		norm = 0.0;
		hyperPlaneValue = new int[64];
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public int compareTo(EmailDistance eDist) {
		return distance < eDist.distance ? -1 : distance > eDist.distance? 1 :0;
		
	}
	public int getBucketName() {
		return bucketIndex;
	}

	public void setBucketIndex(int bucketIndex) {
		this.bucketIndex = bucketIndex;
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public int getHyperPlaneValue(int index) {
		return hyperPlaneValue[index];
	}
	public void setHyperPlaneValue(int index, int value) {
		hyperPlaneValue[index] = value;
	}
	public void setHyperPlaneArray(int [] hyperPlane)
	{
		this.hyperPlaneValue = hyperPlane;
		
	}
	public Email(Multiset<String> emailSet, Double norm) {
		super();
		this.emailSet = emailSet;
		this.norm = norm;
	}
	public Multiset<String> getEmailSet() {
		return emailSet;
	}
	public void setEmailSet(Multiset<String> emailSet) {
		this.emailSet = emailSet;
	}
	public Double getNorm() {
		return norm;
	}
	public void setNorm(Double norm) {
		this.norm = norm;
	}
	

}
