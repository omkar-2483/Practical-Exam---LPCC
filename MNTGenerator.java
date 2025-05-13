import java.util.*;

public class MNTGenerator {
    public static void main(String[] args) {
        String[] code = {
            "LOAD F", "STORE E",
            "MACRO SRS", "LOAD s", "SUB t", "MEND",
            "MACRO SRS XYZ", "LOAD U", "STORE XYZ", "MEND",
            "MACRO ADD1 Si, Sii, Siii", "LOAD Sii", "ADD3 1", "SRS 11", "STORE Si", "STORE Siii", "MEND",
            "SRS", "ADD1 C1, C2, C3", "END"
        };

        List<String> mnt = new ArrayList<>();
        int mdtIndex = 0; // To track the start index of each macro in MDT

        for (int i = 0; i < code.length; i++) {
            String line = code[i].trim();
            if (line.startsWith("MACRO")) {
                String[] parts = line.split("\\s+", 3);
                String macroName = parts[1];
                int paramCount = (parts.length == 3) ? parts[2].split(",").length : 0;

                // Add the macro entry to MNT with format "Macro_name   No_of_Params   Start_index"
                mnt.add(String.format("%-13s  %02d              %02d", macroName, paramCount, mdtIndex));

                // Move to the macro body and calculate its start index in MDT
                i++; // Skip the "MACRO" line
                while (i < code.length && !code[i].trim().equals("MEND")) {
                    mdtIndex++;
                    i++;
                }
                mdtIndex++; // For the "MEND" line
            }
        }

        // Printing the MNT list
        System.out.println("Macro_name   No_of_Params   Start_index");
        for (String entry : mnt) {
            System.out.println(entry);
        }
    }
}
