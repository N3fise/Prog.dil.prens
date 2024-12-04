import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pdp {

    public static void main(String[] args) {
        // Dosya yolunu al
        System.out.print("Lütfen C++ dosyasının tam yolunu girin: ");
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        String filePath = scanner.nextLine();
        scanner.close();

        // Dosyayı oku
        BufferedReader reader = null;
        int unaryCount = 0, binaryCount = 0, ternaryCount = 0;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;
            boolean insideComment = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Yorum satırlarını geç
                if (line.startsWith("//")) {
                    continue;  // Tek satırlık yorum
                } else if (line.contains("/*") && line.contains("*/")) {
                    continue;  // Aynı satırda çok satırlı yorum
                } else if (line.contains("/*")) {
                    insideComment = true;  // Çok satırlı yorum başlar
                    continue;
                } else if (line.contains("*/")) {
                    insideComment = false;  // Çok satırlı yorum biter
                    continue;
                } else if (insideComment) {
                    continue;  // Çok satırlı yorum devam ediyor
                }

                // Stringleri geçmek için
                Matcher stringMatcher = Pattern.compile("\"([^\"]*)\"").matcher(line);
                while (stringMatcher.find()) {
                    line = line.replace(stringMatcher.group(0), "");  // Stringleri kaldır
                }

                // Tekli operatörleri say
                unaryCount += countOperators(line, "\\+\\+|--|!|~");

                // İkili operatörleri say
                binaryCount += countOperators(line, "\\b\\+|\\b-|\\*|/|%|==|!=|>|<|>=|<=|&&|\\|\\||<<|>>|&|\\^|\\|");

                // Üçlü operatörleri say
                ternaryCount += countOperators(line, "\\?[^:]*:");
            }

            // Sonuçları yazdır
            System.out.println("Tekli operatörler: " + unaryCount);
            System.out.println("İkili operatörler: " + binaryCount);
            System.out.println("Üçlü operatörler: " + ternaryCount);

        } catch (IOException e) {
            System.out.println("Dosya bulunamadı!");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Operatör sayma fonksiyonu
    public static int countOperators(String line, String regex) {
        int count = 0;
        Matcher matcher = Pattern.compile(regex).matcher(line);
        while (matcher.find()) {
            count++;
        }
        return count;
    }
}
