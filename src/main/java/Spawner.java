public interface Spawner {
    /**
     * All spawners will extend from this interface
     * @author Jonathan Ma
     * @version 1.0
     */

    // Created purely to keep in line with OOP principles
    public abstract boolean validSpawnPath(int row1, int column1, int row2, int column2);
}
