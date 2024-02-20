package base.algorithms;

import base.Node;
import base.AlgorithmData;
import base.TargetVector;
import base.Vector;

import java.util.List;

public interface Algorithm {
    AlgorithmData run(Node[][] grid, Vector startNodePosition, List<TargetVector> targetNodePositions, boolean visualized);
}
