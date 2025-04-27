import java.util.Scanner;

/*倒计时线程类*/
class CountdownThread extends Thread {
    private int remainingSeconds;

    public CountdownThread(int seconds) {
        this.remainingSeconds = seconds;
    }

    @Override
    public void run() {
        try {
            while (remainingSeconds > 0 && !isInterrupted()) {
                System.out.println("剩余时间：" + remainingSeconds + "秒");
                Thread.sleep(1000); // 暂停1秒
                remainingSeconds--;
            }

            // 根据中断状态输出不同结果
            if (isInterrupted()) {
                System.out.println("\n倒计时被中断！剩余时间：" + remainingSeconds + "秒");
            } else {
                System.out.println("\n倒计时结束！");
            }
        } catch (InterruptedException e) {
            // sleep 期间被中断会进入这里
            System.out.println("\n倒计时被中断！剩余时间：" + remainingSeconds + "秒");
            Thread.currentThread().interrupt(); // 重新设置中断状态
        }
    }
}

/*主程序*/
public class ConsoleTimer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 输入倒计时时间
        System.out.print("请输入倒计时时间（秒）：");
        int seconds = scanner.nextInt();
        scanner.nextLine(); // 消耗换行符

        // 创建并启动线程
        CountdownThread timer = new CountdownThread(seconds);
        timer.start();

        // 键盘监听逻辑
        System.out.println("按 q + Enter 中断计时...");
        while (timer.isAlive()) {
            String input = scanner.nextLine();
            if ("q".equalsIgnoreCase(input)) {
                timer.interrupt(); // 发送中断信号
                break;
            }
        }

        // 等待线程结束
        try {
            timer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("程序退出");
        scanner.close();
    }
}