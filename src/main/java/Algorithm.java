public enum Algorithm {
    A_STAR(0),
    BFS(1);
    public final int algorithm;
    
    private Algorithm(int algorithm) {
        this.algorithm = algorithm;
    }
}