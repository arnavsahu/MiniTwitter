package gui;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import application.AdminPanelStats;
import application.ConcreteVisitor;
import application.Group;
import application.ID;
import application.Tweet;
import application.User;

import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.JTextField;

class TreeRenderer extends DefaultTreeCellRenderer {
	
	public TreeRenderer() {
        setLeafIcon(null); 
    }
	
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object userObject = node.getUserObject();
		if (userObject instanceof ID) {
			setText(((ID) userObject).getID()); 
		} 
		if(userObject instanceof Group) {
			setIcon(UIManager.getIcon("FileView.directoryIcon"));
		}
		return this;
	}
}

public class MainPanel extends JFrame {
	private JPanel contentPane;
	private JTree tree;
	private DefaultTreeModel model; 
	private DefaultMutableTreeNode rootNode; 
	private final AdminPanelStats aps = AdminPanelStats.getInstance(); 
	
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Create an instance of MainPanel and display it
                MainPanel mainFrame = new MainPanel();
                mainFrame.setVisible(true);
            }
        });
    }

	private void buildTree() {
		rootNode.removeAllChildren(); 
		List<Group> rootChildGroups = aps.getRoot().getGroups(); 
		for(Group group : rootChildGroups) {
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(group);
			rootNode.add(node);
			addSubs(node, group); 
		}
		model.reload(); 
	}
	
	private void addSubs(DefaultMutableTreeNode parent, Group group) {
		for(User user : group.getUsers()) {
			DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(user);
			parent.add(userNode);
		}
		for(Group sub : group.getGroups()) {
			DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(sub);
			parent.add(groupNode);
			addSubs(groupNode, sub);
		}
	}

	/**
	 * Create the frame.
	 */
	public MainPanel() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 452, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		rootNode = new DefaultMutableTreeNode(aps.getRoot());
		model = new DefaultTreeModel(rootNode); 
		tree = new JTree(model);
		tree.setBounds(10, 10, 162, 243);
		contentPane.add(tree);
		tree.setCellRenderer(new TreeRenderer());
		buildTree();
		
		JButton addUser = new JButton("Add User");
		addUser.setBounds(182, 63, 244, 43);
		contentPane.add(addUser);
		addUser.addActionListener(actionEvent -> {
			String userID = JOptionPane.showInputDialog(this, "Enter User ID:");
	        String groupID = JOptionPane.showInputDialog(this, "Enter Group ID:");
	        if(userID.isEmpty()) {
	        	JOptionPane.showMessageDialog(this, "Group not found", "Error", JOptionPane.ERROR_MESSAGE);
	        	return; 
	        }
	        User user = new User(userID);
	        Group group = aps.findGroup(groupID);
	        if(group != null) {
	        	group.addUser(user);
	        	buildTree();
	        	validate();
		        repaint();
	        } else {
	        	 JOptionPane.showMessageDialog(this, "Group not found", "Error", JOptionPane.ERROR_MESSAGE);
	        }
		});
		
		JButton addGroup = new JButton("Add Group");
		addGroup.setBounds(182, 10, 81, 43);
		contentPane.add(addGroup);
		addGroup.addActionListener(actionEvent -> {
			String groupID = JOptionPane.showInputDialog(this, "Enter group ID:");
	        String parentID = JOptionPane.showInputDialog(this, "Enter parent ID:");
	        if(groupID.isEmpty()) {
	        	return; 
	        }
	        Group group = new Group(groupID);
	        Group p;
	        if(parentID.isEmpty()) {
	        	p = aps.getRoot(); 
	        } else {
	        	p = aps.findGroup(parentID);
	        }
	        if(p != null) {
	        	p.addGroup(group);
	        	buildTree();
	        	validate();
		        repaint();
	        } else {
	        	 JOptionPane.showMessageDialog(this, "Group not found", "Error", JOptionPane.ERROR_MESSAGE);
	        }
		});
		
		
		JButton userWindow = new JButton("Open User View");
		userWindow.setBounds(182, 116, 244, 43);
		contentPane.add(userWindow);
		userWindow.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	String id = JOptionPane.showInputDialog("Enter User ID");
		        List<User> allUsers = aps.allUsers();
		        User valid = null; 
		        for(User u : allUsers) {
		        	if(u.getID().equals(id)) {
		        		valid = u; 
		        	}
		        }
		        if(valid != null) {
		        	UserPanel userPanel = new UserPanel(valid);
			        userPanel.setVisible(true);
		        } else {
		        	JOptionPane.showMessageDialog(MainPanel.this, "User not found", "Error", JOptionPane.ERROR_MESSAGE);
		        }
		    }
		});
		
		//percent positive messages
		JButton goodness = new JButton("Show Positive Percentages");
		goodness.setBounds(182, 167, 111, 43);
		contentPane.add(goodness);
		goodness.addActionListener(actionEvent -> {
			List<Tweet> tweets = aps.totalTweets();
			if(tweets.size() > 0) {
				double good = 0; 
				double percentage = 0; 
				
				ConcreteVisitor visitor = new ConcreteVisitor(); 
				
				for(Tweet tweet : tweets) {
					if(tweet.accept(visitor)) {
						good++; 
					}
				}
				percentage = (good/(double) tweets.size()) * 100; 
				JOptionPane.showMessageDialog(null, "Positive Percentage: " + Math.round(percentage) + "%");
			} else {
				JOptionPane.showMessageDialog(null, "No tweets yet");
			}
		});
		
		
		//group total

		JButton group = new JButton("Show Group Total");
		group.setBounds(315, 167, 111, 43);
		contentPane.add(group);
		group.addActionListener(actionEvent -> {
			List<Group> grs = aps.allGroups();
			int num = grs.size();
			JOptionPane.showMessageDialog(null, "Total Groups: " + num);
		});
		
		JButton totalTweets = new JButton("Show Tweets Total");
		totalTweets.setBounds(182, 220, 111, 43);
		contentPane.add(totalTweets);
		totalTweets.addActionListener(actionEvent -> {
			List<Tweet> twts = aps.totalTweets();
			int num = twts.size();
			JOptionPane.showMessageDialog(null, "Total Tweets: " + num);
		});
		
		JButton usrs = new JButton("Show Users");
		usrs.setBounds(315, 220, 111, 43);
		contentPane.add(usrs);
		usrs.addActionListener(actionEvent -> {
			List<User> u = aps.allUsers();
			int num = u.size(); 
			JOptionPane.showMessageDialog(null, "Total Users: " + num);
		});
		
		JButton valid = new JButton("Valid");
		valid.setBounds(353, 10, 73, 43);
		contentPane.add(valid);
		valid.addActionListener(actionEvent -> {
			if(aps.isValid()) {
				JOptionPane.showMessageDialog(null, "All users/groups are valid");
			} else {
				JOptionPane.showMessageDialog(null, "One or more user(s)/group(s) is invalid");
			}
		});
		
		JButton latestUser = new JButton("Most updated");
		latestUser.setBounds(273, 10, 73, 43);
		contentPane.add(latestUser);
		latestUser.addActionListener(actionEvent -> {
            User u = aps.getMostRecentUpdate(); 
            if (u == null) {
                JOptionPane.showMessageDialog(null, "No users!");
            } else {
                JOptionPane.showMessageDialog(null, "User ID: " + u.getID() + "\nUpdate time: " + u.getFeed().getLastUpdatedTime());
            }
        });
	}
}
