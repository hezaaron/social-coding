-- Exams
INSERT INTO EXAM (name, description) VALUES ('Java Exam: Beginner', 'This exam offers test on the basics of the Java language');
INSERT INTO EXAM (name, description) VALUES ('Java Exam: Intermediate', 'This exam offers test on the more sophisticated capabilities than the basics of the Java language');
INSERT INTO EXAM (name, description) VALUES ('Java Exam: Advanced', 'This exam offers test on the more advanced concepts and nuances of the Java language');

-- Java Beginner Questions with Answers
INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'class SuperClass {
  private int a;
}
final class SubClass extends SuperClass {
  int a = 5;
  SubClass(int a) {
    System.out.print("-a" + super.a);
  }
  public static void main(String[] args) {
    SubClass sub = new SubClass(2);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, '-b2', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, '-b0', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (1, 'runtime error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class ClassA {
  int b = 2;
  public static void methodA(int b) {
    b++;
    System.out.print("-b" + b);
    b++;
  }
  public static void main(String[] args) {
    int b = 2;
    methodA(b);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, '-b3', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, '-b2', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (2, 'runtime error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'class Outer {
  private int a = 12;
  class InnerA {
    int a = 4;
    public InnerA() {
      a -- ;
      System.out.print("-a" + a);
    }
    private void methodA(){
      a++ ;
      System.out.print("-a" + a);
    }
  }
  public static void main(String[] args) {
    InnerA innerA = new Outer().new InnerA();
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, '-a3', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, '-a4', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, '-a11', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (3, 'compile error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class MyArray {
  public static void main(String[] args) {
    int[] arrayInt = new int[7];
    arrayInt[2] = 4;
    arrayInt[4] = 3;
    arrayInt[6] = 2;
    for(int i = 0; i < arrayInt.length; i++) {
      if(i > 2) {
        System.out.print("-x" + arrayInt[i]);
      }
    }
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, 'runtime error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, 'null', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, '-x0-x3-x0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (4, '-x0-x3-x0-x2', 1);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (1, 'What is the output of the following program?',
'public class MyMethod {
  double x = 12;
  double y = 10;
  public double getResult(double x, double y) {
    return this.x + y;
  }
  public static void main(String[] args) {
    MyMethod mm = new MyMethod();
    System.out.println(mm.getResult(4,3));
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, 'runtime error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, '7.0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, '15.0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (5, 'compile error', 1);

-- Java Intermediate Questions with Answers
INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class MySuper {
  int x = 1; int y = 6;
  MySuper() {
    System.out.print("-x" + x);
    new MySuper(5);
  }
  MySuper(int y) {
    System.out.print("-y" + y); x = 4;
  }
}
public class MySub extends MySuper {
  int w = 4;
  MySub(int w) {
    System.out.print("-w" + w);
  }
  public static void main(String[] args) {
    MySub mySub = new MySub(2);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, '-x1-y6-w2', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, '-x1-y5-w2', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, 'x4-y5-w2', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (6, 'runtime error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class MyClass {
  public static void main(String[] args) {
    int y = 3;
    for(int x = 0; x < 3; x ++ ) {
      System.out.print("-x" + x);
      if(x == 1) {
        System.out.print("-x" + y);
      }
      if(x == 0) {
        int b = x + y;
        System.out.print("-x" + b);
      }
    }
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (7, '-x0-x3-x1-x3-x2', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (7, '-x0-x3-x1-x2-x3', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (7, '-x0-x2-x1-x4-x3', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (7, 'compile error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class MyClass {
  String x = "x";
  int y = 3;
  MyClass() {
    x = "y";
  }
  void myMethod(int y) {
    System.out.print(x + y);
  }
  public int myMethod(int i, int i2) {
    return i * i2;
  }
  public static void main(String[] args) {
    MyClass m = new MyClass();
    int y = m.myMethod(2,3);
    m.myMethod(y);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (8, 'x6', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (8, 'x3', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (8, 'y3', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (8, 'runtime error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'public class MyClass {
  int x = 5;
  int y = 7;
  public void myMethod(int x) {
    System.out.print("-x" + x);
    x = this.x;
    System.out.print("-x" + x);
  }
  public static void main(String args[]) {
    MyClass mc = new MyClass();
    int y = 2;
    mc.myMethod(y);
    System.out.print("-y" + y);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (9, '-x2-x2-y2', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (9, '-x5-x5-x7', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (9, '-x2-x5-y2', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (9, '-x2-x5-x7', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (2, 'What is the output of the following program?',
'class Outer {
  public int a;
  private int b;
  Outer() {
    a = 1;
    b = 4;
    InnerA innerA = new InnerA();
    innerA.methodA();
  }
  class InnerA {
    int a = 4;
    private void methodA() {
      System.out.print("-a" + a + "-b" + b);
    }
  }
  public static void main(String[] args) {
    InnerA innerA = new Outer().new InnerA();
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (10, '-a4-b4', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (10, '-a0-b0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (10, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (10, 'runtime error', 0);

-- Java Advanced Questions with Answers
INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'public class LoopLabel {
  public static void main(String[] args) {
    int b = 3;
    one:for(int x = 0; x < 2; x ++ ) {
      System.out.print("-x" + x);
      for(int y = 0; y < 2; y ++ ) {
        System.out.print("-y" + y);
        for(int z = 0; z < 7; z ++ ) {
          z = z + b;
          System.out.print("-z" + z);
          if(z > 3) {break one;
        }
      }
    }
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (11, 'compile error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (11, 'runtime error', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (11, '-x0-y0-z3-z7', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (11,'-x0-y0-z3-x1-y1-z7', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'class MySuper {
  protected int b = 4;
  MySuper() {
    this(6);
    System.out.print("-b" + (b - 2));
  }
  MySuper(int c) {
    b = c;
    System.out.print("-b" + b);
  }
}
Final class MySub extends MySuper {
  int b;
  int c = 5;
  MySub() {
    System.out.print("-b" + c);
  }
  MySub(int c) {
    int d = (super.b) + c;
    System.out.print("-b" + d);
  }
  void myMethod() {
    System.out.print("-b" + b);
  }
  public static void main(String[] args) {
    MySub mySub = new MySub(2);
    mySub.myMethod();
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (12, '-b6-b0-b8-b0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (12, '-b9-b0-b8-b0', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (12, '-b6-b4-b8-b0', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (12, '-b4-b4-b9-b0', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'public class MyClass {
  String x = "x";
  int y = 3;
  MyClass() {
    x = "y";
  }
  String myMethod(int y) {
    int z = myMethod(13,12);
    String x = "z";
    if(z < y) {
      x = "w";
      System.out.print("-" + x + y);
      return x;
    }else {
      x = "n";
      String s = "z";
      System.out.print("-" + s + y);
      return x;
    }
  }
  public int myMethod(int i, int i2) {
    int y = (i + i2) / 5;
    return y;
  }
  public static void main(String[] args) {
    MyClass m = new MyClass();
    String s = m.myMethod(7);
    int y = m.myMethod(2,3);
    m.myMethod(y);
    System.out.print("-" + s + y);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (13, '-w7-z1-w1', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (13, '-w7-z7', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (13, '-w7-z1-w2', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (13, 'runtime error', 0);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'public class MyLoop {
  int x = 0;
  int y = 0;
  boolean isEqual;
  void methodA() {
    while(x < 4) {
      ++x;
      y += 2;
      System.out.print("-x" + x);
      if(y > x) {
        System.out.print("-y" + y);
        y -= 2;
        isEqual = false;
      }else {
        x--;
        -- y;
        System.out.print("-x" + x + "-y" + y);
        isEqual = true; break;
      }
    }
  }
  void methodA(int i) {
    int x = 4 + i;
    methodA();
    if(isEqual) {
      System.out.print("-y" + x);
    }
  }
  public static void main(String[] args) {
    MyLoop myLoop = new MyLoop();
    myLoop.methodA(2);
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (14, '-x1-y2-x2-x1-y1', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (14, '-x1-y2-x2-x1-y0-y6', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (14, '-x1-y2-x2-x1-y2-y6', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (14, '-x1-y2-x2-x1-y1-y6', 1);

INSERT INTO QUESTION (exam_id, name, code, multi_answer) VALUES (3, 'What is the output of the following program?',
'class MySuper {
  protected int x = 3;
  protected int c = 7;
  protected String s1 = "-n";
  protected String s2 = "-m";
  MySuper() {
    new MySuper(s1);
  }
  MySuper(String s) {
    System.out.print(s + x);
  }
  void method(String s) {
    System.out.print(s + c);
  }
}
class MySubA extends MySuper {
  int x = 6;
  int c = 1;
  String s1 = "-k";
  MySubA(String s) {
    new MySubA(s1,s2);
  }
  MySubA(String s1, String s2) {
    System.out.print(super.s1 + x + super.s2 + c);
  }
  void method(String s) {
    s = super.s1;
    System.out.print(s + c);
  }
  public static void main(String[] args) {
    MySuper mySubA = new MySubA("-n");
    System.out.print(mySubA.c);
    mySubA.method("-w");
  }
}', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (15, '-n3-n3-n6-m17-n1', 1);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (15, '-n3-n6-m11-n1', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (15, 'n6-m17-w1', 0);
INSERT INTO ANSWER (question_id, name, is_correct) VALUES (15, '-n3-n6-m17-n1', 0);
