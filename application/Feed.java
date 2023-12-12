package application;

import java.util.ArrayList;
import java.util.List;

public class Feed extends Observable{
	private List<Tweet> tweets = new ArrayList<>(); 
	private long lastUpdatedTime = -1; 
	
	public List<Tweet> getTweets(){
		return tweets; 
	}
	
	public void setTweets(List<Tweet> tweets) {
		this.tweets.clear();
		this.tweets.addAll(tweets); 
	}
	
	public void setLastUpdatedTime(long lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime; 
	}
	
	public void addTweets(List<Tweet> tweets) {
		this.tweets.addAll(tweets);
	}
	
	public long getLastUpdatedTime() {
		return lastUpdatedTime; 
	}
	
	public void addTweets(Tweet tweet) {
		this.tweets.add(tweet);
		notifyObservers(); 
	}
}
