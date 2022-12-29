package main;

public class IdGenerator {
    // ----- ATTRIBUTOS -----
    private int id;
    private static IdGenerator uniqueInstance;

    private IdGenerator() {
        id = 0;
    }

    // Singleton implementation
    public static IdGenerator getInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new IdGenerator();
        }
        return uniqueInstance;
    }

    public int getId(){
        int returnValue = id;
        id = id + 1;
        return returnValue;
    }
}
