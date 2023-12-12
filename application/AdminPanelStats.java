package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class AdminPanelStats {
	//singleton pattern
	private static AdminPanelStats instance;
	private final Group root = new Group("root"); 
	
	private AdminPanelStats () {
	}
	
	public static AdminPanelStats getInstance(){
		if(instance == null) {
			instance = new AdminPanelStats(); 
		}
		return instance;
	}

	public List<User> totalUsers(Group group) {
        List<User> allUsers = new ArrayList<>();
        Stack<Group> stack = new Stack<>(); 
        stack.push(group);

        while (!stack.isEmpty()) {
            Group curr = stack.pop();
            allUsers.addAll(curr.getUsers());
            for (Group subGroup : curr.getGroups()) {
                stack.push(subGroup);
            }
        }

        return allUsers;
    }
	
	public boolean valid(Group group) {
        List<User> allUsers = allUsers(); 
        List<Group> allGroups = allGroups(); 
        Set<User> uniqueUsers = new HashSet<>();
        Set<Group> uniqueGroups = new HashSet<>(); 
        
        for(User u : allUsers) {
        	if(uniqueUsers.contains(u)) {
        		return false;
        	} else {
        		uniqueUsers.add(u);
        	}
        }
        for(Group g : allGroups) {
        	if(uniqueGroups.contains(g)) {
        		return false;
        	} else {
        		uniqueGroups.add(g);
        	}
        }

        return true;
    }
	
	public boolean isValid() {
		return valid(root);
	}
	
	public List<User> allUsers(){
		return totalUsers(root);
	}
	
	public Group getRoot() {
		return root; 
	}

	public List<Group> totalGroups(Group group) {
        List<Group> allGroups = new ArrayList<>();
        Stack<Group> stack = new Stack<>();
        stack.push(group);
        while (!stack.isEmpty()) {
            Group curr = stack.pop();
            allGroups.add(curr);
            List<Group> subGroups = curr.getGroups();
            for (Group subGroup : subGroups) {
            	
                stack.push(subGroup);
            }
        }
        return allGroups;
    }
	
	public List<Group> allGroups(){
		return totalGroups(root);
	}

	public List<Tweet> totalTweets() {
		List<Tweet> tweets = new ArrayList<>(); 
		List<User> allUsers = totalUsers(root);
		for(User user : allUsers) {
			tweets.addAll(user.getFeed().getTweets());
		}
		return tweets; 
	}
	
	public Group findGroup(String id) {
		List<Group> allGroups = totalGroups(root);
		Group group = null; 
		for(Group g : allGroups) {
			if(id.equals(g.getID())) {
				group = g; 
			}
		}
		return group; 
	}
	
	public User getMostRecentUpdate() {
		List<User> allUsers = allUsers(); 
		if(allUsers.isEmpty()) {
			return null; 
		}
		Collections.sort(allUsers, (user1, user2) -> 
			Long.compare(user2.getFeed().getLastUpdatedTime(), user1.getFeed().getLastUpdatedTime())
		);
		return allUsers.get(0);
	}
}
