/**
*
* @author Nefise İmamniyaz-naifeisa.abudurexiti@ogr.sakarya.edu.tr
* @since 01.12.2024
* <p>
* Bilişim sistemleri mühendisliği 4.sınıf
* </p>
*/

package pdp;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pdp {
    private int tekliOperatorler;
    private int ikiliOperatorler;
    private int ucluOperatorler;

    public pdp(File file) {
    	tekliOperatorler = 0;
    	ikiliOperatorler = 0;
    	ucluOperatorler = 0;

        // Dosyayı okuyup metin olarak işler
        String fileContent = readFile(file);
        if (fileContent != null) {
            // Yorum ve string ifadeleri temizler
            String cleanedContent = removeCommentsAndStrings(fileContent);
            // Operatörleri sayar
            countOperators(cleanedContent);
        }
    }
    // Dosyayı okuma metodu
    private String readFile(File file) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Dosya okunurken hata oluştu: " + e.getMessage());
            return null;
        }
        return content.toString();
    }

    // Yorum ve string ifadeleri temizleme metodu
    private String removeCommentsAndStrings(String content) {
        // Çok satır yorumlar
        content = content.replaceAll("/\\*.*?\\*/", "");
        // Tek satır yorumlar
        content = content.replaceAll("//.*", "");
        // Çift tırnak içerisindeki stringler
        content = content.replaceAll("\"(\\\\.|[^\"])*\"", "");
        // Tek tırnak içerisindeki karakterler
        content = content.replaceAll("'(\\\\.|[^'])'", "");
        //count ifadelerini kaldırır
        content = content.replaceAll("bcount\\s*<<.*?;", "");
        //count ifadelerini kaldırır
        content = content.replaceAll("include\\s*<.*?>", "");
        return content;
    }

    // Operatörleri sayma metodu
    private void countOperators(String content) {
        // Tekli operatörler
        String unaryRegex =  "\\+\\+|--|!|~";
        // İkili operatörler
        String binaryRegex = "\\+=|-=|\\=|/=|%=|&=|\\|=|\\^=|<<=|>>=|==|!=|<=|>=|&&|\\|\\||\\+|-|\\|/|%|&|\\||\\^|<<|>>|=";
        // Üçlü operatör (ternary)
        String ternaryRegex = "\\?";

        // Tekli operatörler için arama
        Pattern unaryPattern = Pattern.compile(unaryRegex);
        Matcher unaryMatcher = unaryPattern.matcher(content);
        while (unaryMatcher.find()) {
            tekliOperatorler++;
        }

        // İkili operatörler için arama
        Pattern binaryPattern = Pattern.compile(binaryRegex);
        Matcher binaryMatcher = binaryPattern.matcher(content);
        while (binaryMatcher.find()) {
            ikiliOperatorler++;
        }

        // Üçlü operatörler için arama
        Pattern ternaryPattern = Pattern.compile(ternaryRegex);
        Matcher ternaryMatcher = ternaryPattern.matcher(content);
        while (ternaryMatcher.find()) {
            ucluOperatorler++;
        }
    }

    // Sonuçları ekrana yazdırma metodu
    public void showResults() {
        System.out.println("Operatör Bilgisi:");
        System.out.println("Tekli Operatör Sayısı: " + tekliOperatorler);
        System.out.println("İkili Operatör Sayısı: " + ikiliOperatorler);
        System.out.println("Üçlü Operatör Sayısı: " + ucluOperatorler);
    }

    // Ana metot
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Analiz edilecek dosyanın tam yolunu giriniz:");
        String filePath = scanner.nextLine();
        File file = new File(filePath);

        if (!file.exists()) {
            System.err.println("Dosya bulunamadı! Lütfen doğru bir yol giriniz.");
            return;
        }

        pdp analyzer = new pdp(file);
        analyzer.showResults();
    }
}
