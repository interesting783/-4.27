import java.util.InputMismatchException;
import java.util.Scanner;

// 自定义异常类
class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);//调用父类构造函数，并将指定信息传递给父类
    }
}

// 抽象学生类
abstract class Student {
    private String name;
    private int score;//均设为私有

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }//消除成员变量与局部变量的歧义

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }
    //用get方法拿到name和score的信息
    // 抽象方法：计算等级
    public abstract String calculateGrade();
}

// 本科生类
class Undergraduate extends Student {
    public Undergraduate(String name, int score) {
        super(name, score);
    }

    @Override//重写属于本科生的计算等级的抽象方法
    public String calculateGrade() {
        int score = getScore();
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
        //等级处理
    }
}

// 研究生类
class Graduate extends Student {
    public Graduate(String name, int score) {
        super(name, score);
    }

    @Override
    public String calculateGrade() {
        int score = getScore();
        if (score >= 85) return "A";
        if (score >= 75) return "B";
        if (score >= 65) return "C";
        if(score >= 60) return "D";
        return "F";
        //等级处理
    }
}

public class Studentscore {
    private static Scanner scanner = new Scanner(System.in);
    //设置为私有静态方法，防止外部读取数据并且配合下面的成绩输入方法（静态）

    // 输入成绩并验证
    //编写输入成绩方法加上异常处理机制
    private static int inputScore() throws InvalidScoreException {
            System.out.print("请输入成绩（0-100）：");
            int score = scanner.nextInt();
            if (score < 0 || score > 100) {
                throw new InvalidScoreException("成绩必须在0-100之间");
            }
            return score;
    }//自定义异常处理

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n学生成绩管理系统");// \n换行
                System.out.println("1.\t添加本科生");
                System.out.println("2.\t添加研究生");
                System.out.println("3.\t退出");// \t空格
                System.out.print("请选择操作：");

                int choice = scanner.nextInt();//读入选择序号
                scanner.nextLine(); // 消耗换行符

                if (choice == 3) break;//读入“3”的时候退出循环

                System.out.print("请输入学生姓名：");
                String name = scanner.nextLine();//读入学生姓名

                int score = inputScore(); // 获取验证后的成绩

                Student student = null;//得初始化student，否则会报错
                switch (choice) {
                    case 1:
                        student = new Undergraduate(name, score);//引用类型传送数据
                        break;
                    case 2:
                        student = new Graduate(name, score);//引用类型传送数据
                        break;
                    default:
                        System.out.println("无效选择！");
                        continue;
                }

                System.out.println("\n学生信息：");
                System.out.println("姓名：" + student.getName());
                System.out.println("成绩：" + student.getScore());
                System.out.println("等级：" + student.calculateGrade());

            } catch (InvalidScoreException e) {
                System.out.println("输入错误：" + e.getMessage());
            }
        }
        System.out.println("系统已退出");
    }
}