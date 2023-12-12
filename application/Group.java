package application;

import java.util.ArrayList;
import java.util.List;

public class Group extends ID {
	private final List<User> users = new ArrayList<>(); 
	private final List<Group> groups = new ArrayList<>(); 
	
	public Group(String id) {
        super(id);
    }

    public Group(String id, List<User> users) {
        super(id);
        setUsers(users);
    }

    public Group(String id, List<User> users, List<Group> groups) {
        super(id);
        setUsers(users);
        setGroups(groups);
    }
	
	public void setUsers(List<User> user) {
		users.clear();
		user.addAll(user);
	}
	
	public void setGroups(List<Group> group) {
		groups.clear();
		groups.addAll(group);
	}
	
	public void addGroup(Group group) {
		groups.add(group);
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public List<User> getUsers(){
		return users; 
	}
	
	public List<Group> getGroups(){
		return groups; 
	}
}
