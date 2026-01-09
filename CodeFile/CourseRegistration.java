public class CourseRegistration {
    public static void main(String[] args) {
        // 1. ÖĞRENCİLERİ OLUŞTUR [cite: 15, 84]
        Student s1 = new Student("Ali", "Kaya", "Computer Engineering", 101, 3);
        Student s2 = new Student("Ayse", "Demir", "Industrial Engineering", 102, 1); // 1. sınıf (prereq takılabilir) [cite: 31]
    
        // 2. HOCAYI OLUŞTUR [cite: 84]
        Faculty hoca = new Faculty(501, "Ahmet", "Yilmaz", "Computer Engineering");
    
        // 3. DERSİ OLUŞTUR [cite: 45]
        // Bölüm: Comp Eng, Kontenjan: 2, Rezerv: 1 (Yani 1 asil, 1 yedek alabilir) [cite: 35, 36]
        Course c1 = new Course("Computer Engineering", "Java 101", 5, 2, 2, 1);
    
        // 4. HOCAYI DERSE ATA [cite: 56]
        c1.assignInstructor(hoca, true);
    
        // 5. ÖĞRENCİLERİ KAYDETMEYE ÇALIŞ [cite: 46, 54]
        System.out.println("--- Registration Starts ---");
        c1.registerCourse(s1); // Bu asil listeye girmeli (3. sınıf ve bölümü aynı) 
        c1.registerCourse(s2); // Bu yedeğe (replacement) girmeli (kontenjan 1+1 olduğu için) [cite: 54]
    
        // 6. SONUÇLARI YAZDIR [cite: 74, 91]
        System.out.println("\n--- Student List for " + c1.getCourseName() + " ---");
        c1.printStudentList();
    
        System.out.println("\n--- Replacement Process ---");
        c1.registerReplacementList(); // Yedeği asile taşımaya çalış [cite: 63]
    
        System.out.println("\n--- Final Student List ---");
        c1.printStudentList();
    
        System.out.println("\n--- Student Credits ---");
        System.out.println(s1.getStudentName() + " Total Credits: " + s1.getTotalCredit()); // 5 gelmeli [cite: 9]
    }
}
