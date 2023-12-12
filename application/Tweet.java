package application;

public class Tweet implements AcceptVisitor{
	private String tweet;
	private User user; 
	
	public Tweet(String tweet, User user) {
		this.tweet = tweet;
		this.user = user; 
	}
	
	public String getTweet() {
		return tweet; 
	}

	public void setTweet(String tweet) {
		this.tweet = tweet; 
	}

	public User getUser() {
		return user; 
	}

	public void setUser(User user) {
		this.user = user; 
	}

	@Override
	public boolean accept(Visitor visitor) {
		return visitor.Visit(this);
	}

}
