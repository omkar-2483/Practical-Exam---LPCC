import java.util.*;

public class IntermediateCode {

    static class Symbol {
        String name;
        int address;

        Symbol(String name, int address) {
            this.name = name;
            this.address = address;
        }
    }

    public static void main(String[] args) {
        String[] code = {
            "START 100",
            "READ A",
            "READ B",
            "MOVER AREG, A",
            "SUB AREG, B",
            "STOP",
            "A DS 1",
            "B DS 1",
            "END"
        };

        Map<String, String> opcodeMap = new HashMap<>();
        opcodeMap.put("READ", "05");
        opcodeMap.put("MOVER", "04");
        opcodeMap.put("SUB", "03");
        opcodeMap.put("STOP", "00");
        opcodeMap.put("START", "01");
        opcodeMap.put("DS", "02");
        opcodeMap.put("END", "02");

        Map<String, Integer> regMap = Map.of(
            "AREG", 1, "BREG", 2, "CREG", 3
        );

        List<Symbol> symTab = new ArrayList<>();
        List<String> intermediateCode = new ArrayList<>();
        int lc = 0;

        for (String line : code) {
            line = line.trim();
            if (line.isEmpty()) continue;

            String[] tokens = line.split("\\s+");

            if (tokens[0].equals("START")) {
                lc = Integer.parseInt(tokens[1]);
                intermediateCode.add("(AD,01) (C," + lc + ")");
                continue;
            }

            if (tokens[0].equals("END")) {
                intermediateCode.add("(AD,02)");
                break;
            }

            if (tokens.length > 1 && tokens[1].equals("DS")) {
                symTab.add(new Symbol(tokens[0], lc));
                intermediateCode.add("(DL,02) (C," + tokens[2] + ")");
                lc++;
                continue;
            }

            // Handle instructions
            String mnemonic = tokens[0];
            String icLine = "(IS," + opcodeMap.get(mnemonic) + ")";

            if (tokens.length > 1) {
                String op1 = tokens[1].replace(",", "");
                if (regMap.containsKey(op1)) {
                    icLine += " (" + regMap.get(op1) + ")";
                    String symbol = tokens[2];
                    int index = getSymbolIndex(symTab, symbol);
                    if (index == -1) {
                        symTab.add(new Symbol(symbol, -1));
                        index = symTab.size() - 1;
                    }
                    icLine += " (S," + index + ")";
                } else {
                    // For READ A or similar
                    String symbol = op1;
                    int index = getSymbolIndex(symTab, symbol);
                    if (index == -1) {
                        symTab.add(new Symbol(symbol, -1));
                        index = symTab.size() - 1;
                    }
                    icLine += " (S," + index + ")";
                }
            }

            intermediateCode.add(icLine);
            lc++;
        }

        System.out.println("Intermediate Code:");
        for (String line : intermediateCode) {
            System.out.println(line);
        }
    }

    private static int getSymbolIndex(List<Symbol> symTab, String name) {
        for (int i = 0; i < symTab.size(); i++) {
            if (symTab.get(i).name.equals(name)) return i;
        }
        return -1;
    }
}

