import java.util.*;

public class PoolTable {
    static class Literal {
        String value;

        Literal(String value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        String[] code = {
                "START 100",
                "READ A",
                "MOVER AREG, ='1'",
                "MOVEM AREG, B",
                "MOVER BREG, ='6'",
                "ADD AREG, BREG",
                "COMP AREG, A",
                "BC GT, LAST",
                "LTORG",
                "NEXT SUB AREG, ='1'",
                "MOVER CREG, B",
                "ADD CREG, ='8'",
                "MOVEM CREG, B",
                "PRINT B",
                "LAST STOP",
                "A DS 1",
                "B DS 1",
                "END"
        };

        List<String> literalPool = new ArrayList<>();
        List<Integer> poolTable = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        int litCount = 0;

        for (String line : code) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            String[] tokens = line.split("\\s+");

            for (String token : tokens) {
                if (token.startsWith("='") && token.endsWith("'") && !seen.contains(token)) {
                    seen.add(token);
                    literalPool.add(token);
                    litCount++;
                }
            }

            if (line.contains("LTORG") || line.contains("END")) {
                if (litCount > 0) {
                    poolTable.add(literalPool.size() - litCount + 1); // 1-based index
                    litCount = 0;
                }
            }
        }

        // Output Pool Table
        System.out.println("PoolTable\nPool_no\tStart_Index");
        for (int i = 0; i < poolTable.size(); i++) {
            System.out.println("#" + (i + 1) + "\t" + poolTable.get(i));
        }
    }
}
