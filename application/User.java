package application;

import java.util.HashSet;
import java.util.Set;

public class User extends ID implements Observer{
	private Set<User> followers = new HashSet<>(); 
	private Set<User> following = new HashSet<>();  
	private final Feed feed = new Feed(); 
	
	public User(String ID) {
		super(ID);
	}
	
	public void setFollowers(HashSet<User> followers) {
		this.followers = followers;
	}
	
	public Set<User> getFollowers(){
		return followers; 
	}
	
	public void setFollowing(HashSet<User> following) {
		this.following = following; 
	}
	
	public Set<User> getFollowing(){
		return following;
	}
	
	public void addFollower(User user) {
		followers.add(user);
	}
	
	public void addFollowing(User user) { 
		following.add(user);
		user.addFollower(this);
		user.getFeed().attach(this);
	}
	
	public Feed getFeed() {
		return feed; 
	}

	@Override
	public void update(Observable o) {
		
	}
}
