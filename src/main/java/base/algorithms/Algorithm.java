package base.algorithms;

import base.Node;
import base.ResultData;
import base.Vector;

import java.util.List;

public interface Algorithm {
    ResultData run(Node[][] grid, Vector startNodePosition, List<Vector> targetNodePositions, boolean visualized);
}
