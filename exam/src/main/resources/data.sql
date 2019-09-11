-- Exams
INSERT INTO EXAM (name, description) VALUES ('Java Exam: Beginner', 'This exam offers test on the basics of the Java language.');
INSERT INTO EXAM (name, description) VALUES ('Java Exam: Intermediate', 'This exam offers test on the more sophisticated capabilities than the basics of the Java language.');
INSERT INTO EXAM (name, description) VALUES ('Java Exam: Advanced', 'This exam offers test on the more advanced concepts and nuances of the Java language.');

-- Java Beginner Questions with Answers
INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Team {
  private int size = 4;
  public static void teamSize(int size) {
	size++;
    System.out.printf("Team size is %d", size++);
  }
  public static void main(String[] args) {
    int size = 6;
	teamSize(size);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, 'Team size is 4', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, 'Team size is 7', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, 'Team size is 6', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Hour {
  double rate = 10;
  double overTimeRate = 15;
  public double getPay(double rate, double overTimeRate) {
    return this.rate + overTimeRate;
  }
  public static void main(String[] args) {
    Hour hour = new Hour();
    System.out.println(hour.getPay(6,4));
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, 'runtime error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, '25.0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, '15.0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, '14.0', 1);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Team {
  protected int size = 5;
}
class Development extends Team {
  int size = 2;
  Development(int size) {
    System.out.printf("team size is %d", this.size);
  }
  public static void main(String[] args) {
    Development development = new Development(3);
  } 
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, 'Team size is 5', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, 'Team size is 2', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, 'Team size is 3', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'class Employee {
  protected String name;
}
final class Programmer extends Employee{
  String name = "Smith";
  Programmer(String name) {
    System.out.printf("Programmer name is %s", super.name);
  }
  public static void main(String[] args) {
    Programmer programmer = new Programmer("Ed");
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, 'Programmer name is null', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, 'Programmer name is Smith', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, 'Programmer name is Ed', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, 'runtime error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Team {
  protected int size = 6;
}
class Development extends Team {
  int size = 4;
  Development(int size) {
    System.out.printf("team size is %d", this.size);
  }
  void devMethod(){
    System.out.printf(" and %d", size);
  }
  public static void main(String[] args) {
    Development development = new Development(3);
	developer.devMethod();
  } 
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, 'Team size is 6 and 3', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, 'Team size is 4 and 4', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, 'Team size is 4 and 6', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, 'Team size is 3 and 3', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Grade {
  public static char getGrade(int score){
    if(score >= 85) {
      return "A";
    }
    else if(score >= 65) {
      return "B";
    }
	else if(score > 50) {
      return "C";
    }
    else {
      return "F";
    }
  }
  public static void main(String[] args) {
    System.out.printf("Grade is %c.", Grade.getGrade(50));
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, 'Grade is C', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, 'Grade is B', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, 'Grade is F', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, 'Compile error', 1);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Product {
  public void getPrice(int age){
    double price;
    if(age < 18) {
      price = 150 - 30;
    }
    if(age >= 70) {
      price = 150 - 20;
    }
    else {
      price = 150;
    }
	System.out.printf("Price = %.2f", price);
  }
  public static void main(String[] args) {
    Product product = new Product();
	product.getPrice(68);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (7, 'Price = 130', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (7, 'Price = 150', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (7, 'Price = 120', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (7, 'Price = 68', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Grade {
  public static char getGrade(int score){
    if(score >= 85) {
      return A;
    }
    else if(score >= 65) {
	  return B;
    }
	else if(score > 50) {
	  return C;
    }
    else {
         return F;
      }
   }
   public static void main(String[] args) {
	   System.out.printf("Grade is %c.", Grade.getGrade(50));
   }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (8, 'Grade is C', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (8, 'Grade is B', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (8, 'Grade is F', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (8, 'Compile error', 1);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'class Employee {
  protected double height = 6.0;
  Employee() {
    System.out.printf("Emp_height = %.1f", height);
   }
}
class Programmer extends Employee {
  Programmer(double height) {
    System.out.printf(",Prog_height = %.1f.", height);
  }
  public static void main(String[] args){
    Programmer programmer = new Programmer(5.8);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (9, ',Prog_height = 5.8', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (9, 'Emp_height = 6.0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (9, 'emp_height = 6.0,Prog_height = 5.8', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (9, ',Prog_height = 5.8Emp_height = 6.0', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Department {
  private int id = 110;
  class Production {
    int id = 25;
    Production() {
      id--;
      System.out.printf("id = %d", id);
    }
    private void printId(){
      id++;
      System.out.printf("id = %d", id);
    }
  }
  public static void main(String[] args) {
    Production team = new Department().new Production();
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (10, 'id = 24', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (10, 'id = 110', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (10, 'id = 109', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (10, 'compile error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'Which method overrides a method in the super class?',
'public class Department {
  static void testing(String requirement) {
  }
  static void coding(String requirement) {
  }
  void codeReview(String requirement) {
  }
  void qualityAssurance(String requirement) {
  }
}
class Development extends Department {
  static void testing(String requirement) {
  }
  void coding(String requirement) {
  }
  void codeReview(String requirement) {
  }
  static void qualityAssurance(String requirement) {
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (11, 'testing', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (11, 'coding', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (11, 'codeReview', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (11, 'qualityAssurance', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'Which method hides a method in the super class?',
'public class Department {
  static void testing(String requirement) {
  }
  static void coding(String requirement) {
  }
  void codeReview(String requirement) {
  }
  void qualityAssurance(String requirement) {
  }
}
class Development extends Department {
  static void testing(String requirement) {
  }
  void coding(String requirement) {
  }
  void codeReview(String requirement) {
  }
  static void qualityAssurance(String requirement) {
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (12, 'testing', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (12, 'coding', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (12, 'codeReview', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (12, 'qualityAssurance', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What does method coding in the sub class do?',
'public class Department {
  static void testing(String requirement) {
  }
  static void coding(String requirement) {
  }
  void codeReview(String requirement) {
  }
  void qualityAssurance(String requirement) {
  }
}
class Development extends Department {
  static void testing(String requirement) {
  }
  void coding(String requirement) {
  }
  void codeReview(String requirement) {
  }
  void qualityAssurance(String requirement) {
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (13, 'It overrides coding in the super class', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (13, 'It causes compile error', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (13, 'It hides coding in the super class', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (13, 'It causes runtime error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Hour {
  public static void main(String[] args) {
    int[] rates = new int[6];
    rates[5] = 8;
    rates[3] = 10;
    rates[1] = 12;
    for(int index = 0; index < rates.length; index++) {
      if(index > 1) {
        System.out.print(rates[index] + ",");
      }
    }
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (14, '12,10,8,', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (14, '10,8,', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (14, '0,10,8,', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (14, '0,10,0,8,', 1);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'class Product {
  public static void main(String[] args) {
    int price = 2;
    if(price >= 1) {
      if (price == 1) {
        System.out.print("price one ");
      }
    else {
      System.out.print("price two ");
    }
    System.out.println("price three ");
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (15, 'price one,', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (15, 'price one price three', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (15, 'price two price three', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (15, 'price two', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Language {
  private static int java = 5;
  private int javascript = 2;
  public static void main (String[] args) {
    Language langA = new Language(); 
    Language langB = new Language(); 
    langA.java = 3; 
    langB.java = 4; 
    langA.javascript = 3; 
    langB.javascript = 4; 
    System.out.print(langA.java +","+ langB.java+","+langA.javascript+","+langB.javascript);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (16, '5,5,3,4,', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (16, '4,4,3,4', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (16, '3,4,3,4', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (16, '4,4,4,4', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class Grade {
  public static void main(String[] args){
    int score = 5;
    switch(score) {
      case 9:
      case 10:
        System.out.println("A");
        break;
      case 8:
        System.out.println("B");
        break;
      case 7:
        System.out.println("C");
        break;
      case 6:
        System.out.println("D");
        break;
      default:
        System.out.println("F");
    }
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (17, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (17, 'F', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (17, 'null', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (17, 'runtime error', 0);

-- Java Intermediate Questions with Answers
INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class Team {
  protected int id = 4;
  protected int size = 10;
  Team() {
    System.out.print(id);
    new Team(8);
  }
  Team(int size) {
    System.out.print("," + size);
	id = 6;
  }
}
class Development extends Team {
  private int progress = 5;
  Development(int progress) {
    System.out.print("," + progress);
  }
  public static void main(String[] args) {
    Development development = new Development(3);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (18, '4,6,5', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (18, '4,8,3', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (18, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (18, '8,5,3', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class Product {
  public static void main(String[] args) {
    int price = 5;
    for(int i = 0; i < 5; i++ ) {
      System.out.print(i);
      if(i == 1) {
        System.out.print("," +price);
      }
      if(i == 0) {
        int bill = i + price;
        System.out.print("," +bill);
      }
    }
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (19, '0,51,5234', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (19, '0,5,5,1,5234', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (19, '0,5,1,5234', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (19, '0,5,1,234', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class Student {
  int score = 8;
  String grade = "B";
  Student() {
    grade = "score";
  }
  void add(int score) {
    System.out.print(grade + score);
  }
  public int multiply(int i, int j) {
    return i * j;
  }
  public static void main(String[] args) {
    Student student = new Student();
    int score = student.multiply(4,3);
    student.add(score);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (20, 'B12', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (20, 'score12', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (20, '12', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (20, 'runtime error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class Hour {
  int rate = 10;
  int otRate = 15;
  public void pay(int rate) {
    System.out.print(rate);
    rate = this.rate;
    System.out.print("," + rate);
  }
  public static void main(String args[]) {
    Hour hour = new Hour();
    int otRate = 12;
    hour.pay(otRate);
    System.out.print("," + otRate);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (21, '12,12,12', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (21, '15,10,12', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (21, '12,10,12', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (21, '12,15,15', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'class Team {
  private int id;
  protected int size;
  Team() {
    id = 3;
    size = 6;
    Development development = new Development();
    development.devMethod();
  }
  class Development {
    int id = 4;
    private void devMethod() {
      System.out.print("id:" + id + " size:" + size);
    }
  }
  public static void main(String[] args) {
    Development development = new Team().new Development();
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (22, 'id:3 size:6', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (22, 'id:4 size:0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (22, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (22, 'id:4 size:6', 1);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class Language {
  void langMethod(){
    int java = 0;
    int sql = 0;
    while(java < 3) {
      ++java;
      sql += 3;
      System.out.print(java);
        if(sql > java) {
          System.out.print("," + sql);
          sql -= 2;
        }else {
          java-- ;
          --sql;
          System.out.print("," + java);
          System.out.print("," + sql);
          break;
        }
      }
   }
   public static void main(String[] args){
      Language language = new Language();
      language.langMethod();
   }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (23, '1,32,43,5', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (23, '0,32,43,5', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (23, '1,3,2,5', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (23, 'runtime error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class Solution {
  public static void main(String[] args) {
    int n = 75;
    double d = 2.25;
    double result = n - d;
    if(result > 0) {
      boolean b = result < 0;
      if(b) System.out.println("It cannot be true!");
      else {
	    String s = "wow";
	    if(result >= 0) {
	      s = "bravo";
	      System.out.println(s);
	    }else System.out.println(s.toUpperCase());
    }
    for(int i = 0; i < 0; i++) {
      System.out.println("WOW");
    }
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (24, 'It cannot be true!', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (24, 'wow', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (24, 'bravo', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (24, 'WOW', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class FindOut {
  public final int num = 30;
  public void doIt() {
    int num = 5;
    Runnable r = new Runnable() {
      public final int num = 5;
	  public void run(){
      int num = 12;
        System.out.println(this.num);
	  }
    };
    r.run();
  }
  public static void main(String[] args) {
    FindOut f = new FindOut();
    f.doIt();
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (25, '42', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (25, '4', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (25, '6', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (25, '5', 1);

-- Java Advanced Questions with Answers
INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'public class Employee {
  protected int id = 4;
  public Employee() {
    id -= 2;
    System.out.print(id++);
  }
  public Employee(int i) {
    this();
    id += 4;
    System.out.print("," +id);
   }
}
class Engineer extends Employee {
  int id = 5;
  public Engineer() {
    super(2);
    id -= 1;
  }
}
class Programmer extends Engineer {
  public Programmer() {
    id += 2;
    System.out.print("," + id);
  }
  public static void main(String[] args){
    Programmer programmer = new Programmer();
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (26, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (26, '3,5,6', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (26, '2,6,6', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (26,'3,6,6', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'public class BreakWithLabel {
  public static void main(String[] args) {
    int a = 5;
    label:
      for(int i = 0; i < 2; i++ ) {
        System.out.print(i);
        for(int j = 0; j < 2; j++ ) {
          System.out.print("," + j);
          for(int k = 0; k < 5; k++ ) {
            k = k + a;
            System.out.print("," + k);
            if(k > 2) {
              break label;
            }
          }
        }
      }
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (27, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (27, 'runtime error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (27, '0,0,5', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (27, '0,1,6', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'public class Employee {
  protected int id = 6;
  Employee() {
    this(8);
    System.out.print(id - 2);
  }
  Employee(int i) {
    id = i;
    System.out.print(id);
  }
}
class Programmer extends Employee {
  int id;
  int i = 4;
  Programmer() {
    System.out.print("," + i);
  }
  Programmer(int i) {
    int j = super.id + i;
    System.out.print("," + j);
  }
  void progMethod() {
    System.out.print("," + id);
  }
  public static void main(String[] args) {
    Programmer programmer = new Programmer(3);
    programmer.progMethod();
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (28, '86,11,10', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (28, '84,11,10', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (28, '86,9,0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (28, '84,9,0', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'public class Team {
  String name = "dev";
  int id = 2;
  Team() {
    name = "test";
  }
  String teamMethod(int y) {
    int size = teamMethod(12,12);
    String name = "qa";
    if(size < id) {
      name = "devop";
      System.out.print(name + id);
      return name;
    }else {
      name = "prod";
      System.out.print("," + id);
      return name;
    }
  }
  public int teamMethod(int i, int j) {
    int id = (i + j) / 5;
    return id;
  }
  public static void main(String[] args) {
    Team team = new Team();
    String name = team.teamMethod(6);
    int id = team.teamMethod(4,4);
    team.teamMethod(id);
    System.out.print("," + name + id);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (29, '2,2,devop1', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (29, ',2,2,prod1', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (29, ',2,2,prod0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (29, 'compile error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'public class LoopI {
  int a;
  int b;
  boolean isEqual;
  void methodLoop() {
    while(a < 3) {
      ++a;
      b += 2;
      System.out.print(a);
      if(b > a) {
        System.out.print("-" + b);
        b -= 2;
        isEqual = false;
      }else {
        a--;
        b--;
        System.out.print("-" + a + "-" + b);
        isEqual = true; break;
      }
    }
  }
  void methodLoop(int i) {
    int a = 4 + i;
    methodLoop();
    if(isEqual) {
      System.out.print("-" + a);
    }
  }
  public static void main(String[] args) {
    LoopI loop = new LoopI();
    loop.methodLoop(4);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (30, '1-2-2-1-1', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (30, '1-22-1-0-8', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (30, '1-22-1-2-8', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (30, '1-22-1-1-8', 1);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'public class LoopII{
  public static void main(String[] args){
    for(int i = 0; i < 3; i++){
      int a = 2;
      int b = 4;
      int n = 2;
	  int x = a;
	  for(int j = 0; j < n; j++){
	    x += (int)Math.pow(2, j) * b;
		System.out.print("-" + x);
	  }
    }
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (31, '-6-14-6-14-6-14', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (31, '4-12-4-12-4-12', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (31, '0-8-0-8-0-8', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (31, 'compile error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'class SuperClass {
  protected int a = 2;
  protected int b = 4;
  protected String s1 = "x";
  protected String s2 = "y";
  SuperClass() {
    new SuperClass(s1);
  }
  SuperClass(String s) {
    System.out.print(s + a);
  }
}
class SubClass extends SuperClass {
  int a = 6;
  int b = 3;
  String s1 = "z";
  SubClass(String s1, String s2) {
    System.out.print(super.s1 + a + super.s2 + b);
  }
  public static void main(String[] args) {
    SuperClass subClass = new SubClass("w");
    System.out.print(subClass.b);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (32, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (32, 'x6x3y43', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (32, 'x2x6y34', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (32, 'runtime error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'import java.util.Arrays;
public class Flip {
  static void flip(int[] arr, int n) {
    for(int i = 0; i < n/2; i++) {
      int temp = arr[i];
      arr[i] = arr[n - i - 1];
      arr[n - i - 1] = temp;
	}
    System.out.println(Arrays.toString(arr));
  }
  public static void main(String[] args) {
    int[] arr = new int[]{11, 4, 10, 8, 9};
    int n = 4;
    flip(arr, n);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (33, '[11, 4, 10, 8, 9]', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (33, '[8, 10, 4, 11, 9]', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (33, '[8, 4, 10, 11, 9]', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (33, '[11, 9, 10, 8, 4]', 0);
