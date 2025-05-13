import java.util.*;

public class MDTGenerator {

    public static void main(String[] args) {
        String[] code = {
            "LOAD A",
            "STORE B",
            "MACRO ABC",
            "LOAD p",
            "SUB q",
            "MEND",
            "MACRO ADD1 ARG",
            "LOAD x",
            "STORE ARG",
            "MEND",
            "MACRO ADD5 A1, A2, A3",
            "STORE A2",
            "ADD1 5",
            "ADD1 10",
            "LOAD A1",
            "LOAD A3",
            "MEND",
            "ABC",
            "ADD5 D1, D2, D3",
            "END"
        };

        List<String> mdt = new ArrayList<>();
        boolean inMacro = false;
        Map<String, String> paramMap = new HashMap<>();

        for (int i = 0; i < code.length; i++) {
            String line = code[i].trim();

            if (line.startsWith("MACRO")) {
                inMacro = true;
                paramMap.clear();

                // Split MACRO line to get macro name and parameters
                String[] parts = line.split("\\s+", 3); // max 3 parts: "MACRO", "name", "params"
                if (parts.length == 3) {
                    String[] params = parts[2].split(",\\s*");
                    for (int j = 0; j < params.length; j++) {
                        paramMap.put(params[j].trim(), "#" + (j + 1));
                    }
                }
                continue;
            }

            if (line.equals("MEND")) {
                mdt.add("MEND");
                inMacro = false;
                continue;
            }

            if (inMacro) {
                String[] tokens = line.split("\\s+|,\\s*");
                StringBuilder replaced = new StringBuilder(tokens[0]);

                for (int j = 1; j < tokens.length; j++) {
                    String arg = tokens[j];
                    if (paramMap.containsKey(arg)) {
                        replaced.append(" ").append(paramMap.get(arg));
                    } else {
                        replaced.append(" ").append(arg);
                    }
                }

                mdt.add(replaced.toString());
            }
        }

        // Print MDT
        System.out.println("MDT:");
        for (int i = 0; i < mdt.size(); i++) {
            System.out.printf("%02d  %s\n", i + 1, mdt.get(i));
        }
    }
}
