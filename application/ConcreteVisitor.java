package application;

public class ConcreteVisitor implements Visitor{
	static final String[] good = {"good", "great", "excellent", "awesome", "amazing"};
	int userCount = 0; 
	int groupCount = 0; 
	
	@Override
	public boolean Visit(Tweet tweet) {
		String msg = tweet.getTweet();
		for(String word : msg.split(" ")) {
	        for (String goodWord : good) { 
	            if (goodWord.equalsIgnoreCase(word)) {
	                return true;
	            }
	        }
	    }
	    return false;
	}	
}
