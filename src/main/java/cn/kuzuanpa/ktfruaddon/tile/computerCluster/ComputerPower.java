package cn.kuzuanpa.ktfruaddon.tile.computerCluster;

public enum ComputerPower {
    Normal(0x33cce5), Biology(0x99e533), Quantum(0xb233cc), Spacetime(0x334ce5);

    public final int color;
    ComputerPower(int color){
        this.color = color;
    }
    public static ComputerPower getType(int ordinal){
        switch (ordinal){
            case 0:return Normal;
            case 1:return Biology;
            case 2:return Quantum;
            case 3:return Spacetime;
        }
        return Normal;
    }
}
