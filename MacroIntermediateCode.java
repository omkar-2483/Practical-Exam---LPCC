import java.util.*;

public class MacroIntermediateCode {

    public static void main(String[] args) {
        String[] code = {
                "LOAD A",
                "MACRO ABC",
                "LOAD p",
                "SUB q",
                "MEND",
                "STORE B",
                "MULT D",
                "MACRO ADD1 ARG",
                "LOAD X",
                "STORE ARG",
                "MEND",
                "LOAD B",
                "MACRO ADD5 A1, A2, A3",
                "STORE A2",
                "ADD1 5",
                "ADD1 10",
                "LOAD A1",
                "LOAD A3",
                "MEND",
                "ADD1 t",
                "ABC",
                "ADD5 D1, D2, D3",
                "END"
        };

        List<String> intermediateCode = new ArrayList<>();
        boolean inMacro = false;

        for (String line : code) {
            line = line.trim();

            if (line.startsWith("MACRO")) {
                inMacro = true;
                continue;
            }

            if (line.equals("MEND")) {
                inMacro = false;
                continue;
            }

            if (!inMacro) {
                intermediateCode.add(line);
            }
        }

        // Output intermediate code
        System.out.println("Intermediate Code:");
        for (String line : intermediateCode) {
            System.out.println(line);
        }
    }
}
