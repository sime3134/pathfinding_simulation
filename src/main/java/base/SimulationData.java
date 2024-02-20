package base;

public class SimulationData {
    private final AlgorithmData aStarData;
    private final AlgorithmData bfsData;

    public SimulationData(AlgorithmData aStarData, AlgorithmData bfsData) {
        this.aStarData = aStarData;
        this.bfsData = bfsData;
    }

    public AlgorithmData getAStarData() {
        return aStarData;
    }

    public AlgorithmData getBfsData() {
        return bfsData;
    }
}
