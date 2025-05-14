import java.util.*;

public class SymbolTable {

    static class Symbol {
        String name;
        int address;

        Symbol(String name, int address) {
            this.name = name;
            this.address = address;
        }

        @Override
        public String toString() {
            return String.format("%s\t%d", name, address);
        }
    }

    public static void main(String[] args) {
        String[] code = {
                "START 100",
                "READ A",
                "READ B",
                "LOOP MOVER AREG, A",
                "  MOVER BREG, B",
                "  COMP BREG, ='2'",
                "  BC GT, LOOP",
                "BACK SUB AREG, B",
                "  COMP AREG, ='5'",
                "  BC LT, BACK",
                "  STOP",
                "A DS 1",
                "B DS 1",
                "END"
        };

        Set<String> mnemonics = new HashSet<>(Arrays.asList(
                "START", "READ", "MOVER", "COMP", "BC", "SUB", "STOP", "DS", "END"));

        Map<String, Symbol> symbolTable = new LinkedHashMap<>();
        int lc = 0;

        for (String line : code) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            String[] tokens = line.split("\\s+");

            // initialize LC
            if (tokens[0].equals("START")) {
                lc = Integer.parseInt(tokens[1]);
                continue;
            }

            if (tokens[0].equals("END")) {
                break;
            }

            // Check for label or symbol
            if (!mnemonics.contains(tokens[0])) {
                String label = tokens[0];
                if (!symbolTable.containsKey(label)) {
                    symbolTable.put(label, new Symbol(label, lc));
                }
            }
            lc++;
        }

        // Output the Symbol Table
        System.out.println("Name\tAddress");
        for (Symbol sym : symbolTable.values()) {
            System.out.println(sym);
        }
    }
}
