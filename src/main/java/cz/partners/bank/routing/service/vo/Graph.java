package cz.partners.bank.routing.service.vo;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Zdenek Benes
 */
@Data
public class Graph {

    private Set<Node> nodes = new HashSet<>();

    public void addNode(Node nodeA) {
        nodes.add(nodeA);
    }

    public Node getNodeFromGraph(String nodeName) {
        return getNodes().stream()
            .filter(node -> node.getName().equalsIgnoreCase(nodeName))
            .findAny().orElse(null);
    }


}
