/**
 * 
 * NOTE: 
 * normally i would have broken this into Tree and Leaf classes 
 * but the instructions provided this base of this class so just following 
 * with some getter/setters and addChild method 
 * (but still not sure the value of passing in 'Parent') 
 * 
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Tree {
    private List<Tree> leaves = new LinkedList<Tree>();
    private Tree parent = null;
    private String data;
    private Boolean visited = false;
	private Map<String, Tree> map = new TreeMap<String, Tree>();

    public Tree(String data, Tree parent) {
        this.data = data;
        this.parent = parent;
    }
    
    public Tree(String data) {
        this.data = data; // basically a leaf / node
    }    
    
	public Tree getParent() {
		return parent;
	}
	
	public void addLeaf(Tree child) {
		leaves.add(child); // adding new child to children array
	}
	
	public String getData() {
		return data;
	}
	
	public List<Tree> getLeaves() {
		return leaves;
	}

	public void setLeaves(List<Tree> leaves) {
		this.leaves = leaves;
	}
	
	public Boolean getVisited() {
		return visited;
	}

	public void setVisited(Boolean visited) {
		this.visited = visited;
	}

	public Map<String, Tree> flattenTreeBySize(Tree child) {
		//Tree child = node.getLeaves().get(i);
		String key = String.valueOf(child.getLeaves().size()).concat("_");
		while(map.containsKey(key)) {
			key = key.concat("_");
		}
		map.put(key, child); 
		for (int i = 0; i < child.getLeaves().size(); i++) {
			map.putAll(flattenTreeBySize(child.getLeaves().get(i)));
		}		
		return map;
	} 	
	
	
}