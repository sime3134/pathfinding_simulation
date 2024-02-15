package base.algorithms;

import base.Node;
import base.ResultData;
import base.Vector;

import java.util.List;

public interface Algorithm {
    ResultData run(Node[][] grid, Vector startNode, List<Vector> targetNodes);
}
