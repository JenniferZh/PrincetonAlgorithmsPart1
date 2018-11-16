/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private SearchNode endNode = null;
    private int step = -1;
    private boolean isSolvable = false;

    private class SearchNode implements Comparable<SearchNode> {

        private SearchNode parentNode = null;
        private final Board board;
        private int step = 0;
        private final boolean isTwin;

        SearchNode(Board board, boolean isTwin) {
            this.board = board;
            this.isTwin = isTwin;
        }

        SearchNode(Board board, SearchNode parentNode) {
            this.board = board;
            this.step = parentNode.step + 1;
            this.parentNode = parentNode;
            this.isTwin = parentNode.isTwin;
        }

        private int priority() {
            return step + board.manhattan();
        }

        public int compareTo(SearchNode node) {
            if (priority() - node.priority() < 0) return -1;
            else if (priority() - node.priority() > 0) return 1;
            else return 0;
        }

    }

    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException();

        SearchNode startNode = new SearchNode(initial, false);
        SearchNode startNodeTwin = new SearchNode(initial.twin(), true);
        // initialization: create a set that contains start node only
        MinPQ<SearchNode> openSet = new MinPQ<>();
        openSet.insert(startNode);
        openSet.insert(startNodeTwin);

        while (true) {
            SearchNode current = openSet.delMin();
            // when endNode is poped, we can say we've found the end node!
            if (current.board.isGoal()) {
                if (!current.isTwin) {
                    endNode = current;
                    step = current.step;
                    isSolvable = true;
                } else {
                    endNode = null;
                    step = -1;
                    isSolvable = false;
                }
                break;

            }

            Board currentParent = current.parentNode == null ? null : current.parentNode.board;
            for (Board neighbor: current.board.neighbors()) {
                if (neighbor.equals(currentParent)) continue;

                SearchNode node = new SearchNode(neighbor, current);
                openSet.insert(node);
            }
        }

    }

    public boolean isSolvable() {
        return isSolvable;
    }

    public int moves() {
        return step;
    }

    public Iterable<Board> solution() {
        SearchNode current = endNode;
        if (current == null) return null;
        Stack<Board> path = new Stack<>();
        while (current.parentNode != null) {
            path.push(current.board);
            current = current.parentNode;
        }
        path.push(current.board);
        return path;
    }

    public static void main(String[] args) {

    }
}
