package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import application.AdminPanelStats;
import application.Feed;
import application.Observable;
import application.Observer;
import application.Tweet;
import application.User;

import java.util.List;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UserPanel extends JFrame implements Observer{
	private final AdminPanelStats aps = AdminPanelStats.getInstance();
	private final User user; 
	private DefaultListModel<String> listModel;
	private DefaultListModel<String> feedModel; 
	
	private JPanel contentPane;
	private JScrollPane followingView;
	private JScrollPane feedView;
	private JTextField userID;
	private JButton follow;
	private JButton tweetPost;

	/**
	 * Create the frame.
	 */
	public UserPanel(User usr) {
		this.user = usr; 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		userID = new JTextField();
		userID.setBounds(10, 10, 130, 25);
		userID.setText(user.getID());
		userID.setEditable(false);
		contentPane.add(userID);
		userID.setColumns(10);
		
		
		follow = new JButton("Follow User");
		follow.setBounds(10, 125, 416, 47);
		follow.addActionListener(actionEvent -> {
			String id = JOptionPane.showInputDialog("Enter User ID");
			List<User> allUsers = aps.allUsers(); 
			boolean f = false; 
			for(User u : allUsers) {
				if(u.getID().equals(id)) {
					f = true;
					user.addFollowing(u);
					u.addFollower(user);
					updateFollowingList();
					u.getFeed().attach(this);
					break; 
				}
			} 
			if(!f) {
				JOptionPane.showMessageDialog(null, "User does not exist");
			}
		});
		contentPane.add(follow);
		
		
		tweetPost = new JButton("Tweet");
		tweetPost.setBounds(150, 10, 135, 25);
		tweetPost.addActionListener(acitonEvent -> {
			String input = JOptionPane.showInputDialog("Tweet");
			Tweet tweet = new Tweet(input, user);
			user.getFeed().addTweets(tweet);
		});
		contentPane.add(tweetPost);
		
		
		listModel = new DefaultListModel<>();
		updateFollowingList();  
		JList<String> followingList = new JList<>(listModel);  
		followingView = new JScrollPane(followingList);  
		followingView.setBounds(10, 50, 408, 65);  
		contentPane.add(followingView);  

		
		feedModel = new DefaultListModel<>(); 
		feedView = new JScrollPane();
		JList<String> feedList = new JList<>(feedModel); 
		feedView.setViewportView(feedList);
		feedView.setBounds(10, 182, 416, 73);
		contentPane.add(feedView);
		user.getFeed().attach(this);
		
		long creationTime = user.getCreationTime();
		long lastUpdatedTime = user.getFeed().getLastUpdatedTime();
		JTextArea textArea = new JTextArea("Creation Time: " + creationTime + "\nLast Updated Time: " + lastUpdatedTime);
		textArea.setBounds(295, 10, 123, 22);
		contentPane.add(textArea);
	}
	
	private void updateFollowingList() {
		listModel.clear();
        Set<User> currFollowing = user.getFollowing();
        for(User u : currFollowing) {
            listModel.addElement(u.getID());
        }
	}
	
	private void updateFeed() {
		List<Tweet> tweets = user.getFeed().getTweets();
		feedModel.clear(); 
		for(Tweet tweet : tweets) {
			feedModel.addElement(tweet.getTweet());
		}
	}

	@Override
	public void update(Observable o) {
		// TODO Auto-generated method stub
		updateFeed(); 
	}
}