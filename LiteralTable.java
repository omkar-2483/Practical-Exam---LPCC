import java.util.*;

public class LiteralTable {

    static class Literal {
        String value;
        int address;

        Literal(String value, int address) {
            this.value = value;
            this.address = address;
        }

        @Override
        public String toString() {
            return String.format("%-6s\t%3d", value, address);
        }
    }

    public static void main(String[] args) {
        String[] code = {
                "START 100",
                "READ A",
                "READ B",
                "MOVER AREG, ='50'",
                "MOVER BREG, ='60'",
                "ADD AREG, BREG",
                "LOOP MOVER CREG, A",
                "ADD CREG, ='10'",
                "COMP CREG, B",
                "BC LT, LOOP",
                "NEXT SUB AREG, ='10'",
                "COMP AREG, B",
                "BC GT, NEXT",
                "STOP",
                "A DS 1",
                "B DS 1",
                "END"
        };

        List<Literal> literalTable = new ArrayList<>();
        Set<String> seenLiterals = new HashSet<>();
        int lc = 0;

        for (String line : code) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            String[] tokens = line.split("\\s+");

            // Initialize location counter
            if (tokens[0].equals("START")) {
                lc = Integer.parseInt(tokens[1]);
                continue;
            }

            if (tokens[0].equals("END")) {
                break;
            }

            for (String token : tokens) {
                if (token.startsWith("='") && token.endsWith("'")) {
                    if (!seenLiterals.contains(token)) {
                        seenLiterals.add(token);
                        literalTable.add(new Literal(token, -1)); // address to be assigned later
                    }
                }
            }
            lc++;
        }

        // Assign addresses to literals (sequentially after instructions)
        int address = lc;
        for (Literal lit : literalTable) {
            lit.address = address++;
        }

        // Output the Literal Table
        System.out.println("LIteral Table \n--------------");
        System.out.println("Literal\tAddress");
        for (Literal lit : literalTable) {
            System.out.println(lit);
        }
    }
}
