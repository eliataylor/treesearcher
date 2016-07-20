
/*
 *       a
 *      / \
 *     b   c
 *    /|\   \
 *   d e f   g
 *           |
 *           h
 *
 * Consider a tree data structure implemented by the following Java class:
 *
 * public class Tree {
 *     private List<Tree> leaves = new LinkedList<Tree>();
 *     private Tree parent = null;
 *     private String data;
 * 
 *     public Tree(String data, Tree parent) {
 *         this.data = data;
 *         this.parent = parent;
 *     }
 * }
 * 
 * Implement search functionality using a method with the footprint: Tree firstMatch(Tree, Regex). This method takes a tree, searches that tree for leaves/branches in which the data  matches the given regex expression and returns the first branch/leaf (another Tree object) that matches. You should implement two different search algorithms: a Depth-First Search (DFS), and another search algorithm of your choice.
 * 
 * EXAMPLE TESTS:
 * java TreeTraversal "(808)" dfs 2
 * java TreeTraversal "([09])" dfs 2
 * java TreeTraversal "(f)" bfs 2 
 * java TreeTraversal "(-$test)" dfs 2
 * 
 */

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class TreeTraversal {

	private Tree foundNode = null;
	private static int logLevel = 0;

	public void setFoundNode(Tree foundNode) {
		this.foundNode = foundNode;
	}

	public Tree getFoundNode() {
		return foundNode;
	}

	public void bfsish(Tree tree, String regex) {
		if (tree == null || isEmpty(tree.getData()) || isEmpty(regex)) {
			return;
		}

		Pattern pattern;
		try {
			pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		} catch (PatternSyntaxException exception) {
			System.err.println(exception.getDescription());
			return;
		}

		Map<String, Tree> order = tree.flattenTreeBySize(tree);
		for (Map.Entry<String, Tree> child : order.entrySet()) {
			Tree node = child.getValue();
			if (logLevel > 0)
				System.out.println("-" + node.getData() + " has " + child.getKey() + " children");

			Matcher matcher = pattern.matcher(node.getData());
			while (matcher.find()) {
				if (logLevel > 1)
					System.out.println("--regex match: " + matcher.group() + " of " + matcher.groupCount());
				if (matcher.groupCount() > 0) {
					this.setFoundNode(node);
					return;
				}
			}			
		}

	}

	public void dfs(Tree node, String regex) {
		if (node == null || isEmpty(node.getData()) || isEmpty(regex)) {
			return;
		}

		if (logLevel > 0)
			System.err.println("-testing ".concat(node.getData()));


		Pattern pattern;
		try {
			pattern = Pattern.compile(regex, Pattern.UNICODE_CASE);
		} catch (PatternSyntaxException exception) {
			System.err.println(exception.getDescription());
			return;
		}
		Matcher matcher = pattern.matcher(node.getData());
		while (matcher.find()) {
			if (matcher.groupCount() > 0) {
				if (logLevel > 1)
					System.out.println("--regex match: " + matcher.group() + " of " + matcher.groupCount());
				this.setFoundNode(node);
				return;
			}
		}

		if (node.getLeaves().size() > 0) {
			List<Tree> children = node.getLeaves();
			if (logLevel > 0)
				System.err.println(node.getData() + " has " + children.size() + " leaves");
			for (int i = 0; i < children.size(); i++) {
				dfs(children.get(i), regex); // recursion
			}
		}
	}

	public static boolean isEmpty(List<String> list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		if (list.size() == 1 && isEmpty(list.get(0))) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {
		if (str != null && str.trim().length() > 0) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		TreeTraversal treeTraversal = new TreeTraversal();
		String regex = "^.";
		String algo = "dfs";
		if (args.length > 0)
			regex = args[0].trim();
		if (args.length > 1)
			algo = args[1];
		if (args.length > 2)
			logLevel = Integer.parseInt(args[2]);
		treeTraversal.firstMatch(regex, algo); // testing
	}

	public Tree buildTree() {

		// Tree tree = new Tree("root", nodeA);
		Tree nodeA = new Tree("root");
		Tree nodeB = new Tree("b-eli@taylormadetraffic.com");
		Tree nodeC = new Tree("8088555665");
		Tree nodeD = new Tree("d-425 1st Street");
		Tree nodeE = new Tree("e-San Francisco, CA");
		Tree nodeF = new Tree("f");
		Tree nodeG = new Tree("g-$test%");
		Tree nodeH = new Tree("h-c1_+#x33");

		nodeB.addLeaf(nodeD);
		nodeB.addLeaf(nodeE);
		nodeB.addLeaf(nodeF);

		nodeC.addLeaf(nodeG);
		nodeG.addLeaf(nodeH);

		nodeA.addLeaf(nodeB);
		nodeA.addLeaf(nodeC);
		nodeA.addLeaf(nodeG);

		return nodeA;
	}

	public void firstMatch(String regex, String algo) {
		Tree tree = buildTree();
		if (algo.equalsIgnoreCase("dfs"))
			dfs(tree, regex);
		else
			bfsish(tree, regex);
		Tree foundNode = getFoundNode();
		if (foundNode != null) {
			System.out.println("Found " + foundNode.getData() + " using dfs with " + regex); // printing
		} else {
			System.out.println("No node found with '" + regex + "'");
		}
	}
}