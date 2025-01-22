package lab1;

import java.util.Scanner;

public class lab1 {
    public static Scanner scanner;
    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        int n;
        //task1
        n = scanner.nextInt();
        System.out.println("task 1: " + KollatzSeq(n));

        //task2
        n = scanner.nextInt();
        System.out.println("task 2: " + SeqSum(n));

        //task3
        int x = scanner.nextInt();
        int y = scanner.nextInt();
        System.out.println("task 3: " + FindTreasure(x,y));

        //task4
        System.out.println("task 4: ");
        MaxMin();

        //task5
        n = scanner.nextInt();
        System.out.println("task 5: " + (IsEveTwice(n) ? "yes" : "no"));
        
    }

    public static int KollatzSeq(int n){
        if (n == 1){
            return 0;
        }
        else if (n % 2 == 0){
            return 1 + KollatzSeq(n/2);
        }
        else{
            return 1 + KollatzSeq(3 * n + 1);
        }
    }

    public static int SeqSum(int n){
        int answer = 0;
        for (int i = 0;i<n; i++){
            System.out.println("enter num:");
            int num = scanner.nextInt();
            if(i % 2 == 0){
                answer += num;
            }
            else{
                answer -= num;
            }
        }
        return answer;
    }

    public static int FindTreasure(int x, int y){
        int answer = -1;
        int stepCounter = 0;
        int tmpX = 0, tmpY = 0;
        String direction = scanner.next();
        while (!direction.equals("stop")){
            int num = scanner.nextInt();
            switch (direction) {
                case "north":
                    tmpY += num;
                    break;
                case "south":
                    tmpY -= num;
                    break;
                case "west":
                    tmpX -= num;
                    break;
                case "east":
                    tmpX += num;
                    break;
                default:
                    break;
            }
            stepCounter++;
            direction = scanner.next();
            if (tmpX == x && tmpY == y && answer == -1) {
                answer = stepCounter;
            }
        }
        return answer;
    } 

    public static void MaxMin(){
        int n = scanner.nextInt();
        int answer = 0;
        int road = 0;
        for (int i = 1; i <= n; ++i) {
            int m = scanner.nextInt();
            int minVal = scanner.nextInt();
            for (int j = 1; j < m; ++j) {
                minVal = Math.min(minVal, scanner.nextInt());
            }
            if(minVal >= answer){
                road = i;
            }
            answer = Math.max(answer, minVal);
        }
        System.out.println(road + "," + answer);
    }

    public static boolean IsEveTwice(int n){
        if(n < 100 || n > 999){
            System.out.println("number must be three-digit!");
            return false;
        }
        int sum = 0;
        int product = 1;
        while(n > 0){
            int tmp = n % 10;
            sum += tmp;
            product *= tmp;
            n /= 10;
        }
        return (sum % 2 == 0 && product % 2 == 0 ? true : false);
    }
}
