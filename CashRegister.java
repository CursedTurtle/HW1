import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Locale;
import java.util.Scanner;

/*Item class*/
class Item {
    // Arrays to store data

    public int[] itemCode;
    public String[] itemName;
    public float[] unitPrice;


    Item(){
        String[] data = new String[0];
        /*Read file*/
        data = this.readFile(data);

        itemCode = new int[data.length];
        itemName = new String[data.length];
        unitPrice = new float[data.length];

        for(int i = 0;i < data.length;i++){
            String[] dataDivided = data[i].split(",");
            itemCode[i] = Integer.parseInt(dataDivided[0]);
            itemName[i] = dataDivided[1];
            unitPrice[i] = Float.parseFloat(dataDivided[2]);
        }
    }


  
    public String[] readFile(String[] fileData){
        try{
            File eleFile = new File("StoringData.txt");
            int arraySize = 0 ;
            String line;

            BufferedReader br = new BufferedReader(new FileReader(eleFile));
            while((line = br.readLine())!=null){
                arraySize ++;
            }
            br.close();

            fileData = new String[arraySize];

            br = new BufferedReader(new FileReader(eleFile));
            int i = 0;
            while((line = br.readLine())!=null){
                fileData[i] = line;
                i++;
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return fileData;
    }

  
    public String getName(int code){
        return itemName[code-1];
    }

    /***
     *
     * @param code returns price -1
     * @return
     */
    public double getPrice(int code){
        return unitPrice[code-1];
    }

}

/*Driver Class*/
public class CashRegister {
    public static void main(String[] args){
        // creating scanner class object
        Scanner sc = new Scanner(System.in);
        // Variables to store data
        String[] data = new String[100];
        int count = 0;
        int code;
        double totalDataCode = 0;
        double sales = 0;

        // Print output
        System.out.println("Welcome to the cash register system!");
        System.out.println("Beginning a new sale (Y/N)");
        String ans = sc.next();

        /*User input */
        while(ans.toLowerCase(Locale.ROOT).equals("y")) {
            if (ans.toLowerCase(Locale.ROOT).equals("y")) {
                do {
                    Item n1 = new Item();

                    System.out.print("Enter product code: ");
                    String value1 = sc.next();

                    // Check if input is correct
                    while(value1.charAt(0) < 45 || value1.charAt(0) > 57){
                        System.out.println("!!!Invalid product code");
                        System.out.print("Enter product code: ");
                        value1 = sc.next();
                    }
                    code = Integer.parseInt(value1);


                    if (code == -1) {
                        continue;
                    }

                    System.out.println("         item name: " + n1.getName(code));
                    String itemName = n1.getName(code);
                    double price = n1.getPrice(code);

                    System.out.print("Enter quantity: ");
                    value1 = sc.next();

                    while(value1.charAt(0) < 48 || value1.charAt(0) > 57){
                        System.out.println("!!!Invalid quantity :");
                        System.out.print("Enter quantity code: ");
                        value1 = sc.next();
                    }
                    int quantity = Integer.parseInt(value1);

                    double total = quantity * price;
                    System.out.println("Item Total : $ " + total);

                    /*Add amount*/
                    totalDataCode = totalDataCode + total;
                    data[count] = quantity + " " + itemName + "  $" + String.format("%.2f", total);
                    count++;
                } while (code != -1);

                System.out.println("---------------------------");
                System.out.println("Item List :");
                for (String datum : data) {
                    if(datum!=null) {
                        System.out.println(datum);
                    }
                }

                /*Print end*/
                System.out.println("SubTotal  $" + totalDataCode);
                double totalWithTax = totalDataCode + totalDataCode * 6 / 100;
                System.out.println("Total with tax " + String.format("%.2f", totalWithTax));
                System.out.print("Tendered Amount : $ ");
                double amountPay = Double.parseDouble(sc.next());

                //If tendered < total
                while (true) {
                    if (totalWithTax - amountPay > 0) {
                        System.out.println("Enter tendered amount again.");
                        amountPay = Double.parseDouble(sc.next());
                    } else {
                        break;
                    }
                }

                /*Change amount*/
                sales  = sales + totalWithTax;
                double change = amountPay > totalWithTax ? amountPay - totalWithTax : 0;
                System.out.println("Change : $" + String.format("%.2f", change));
                System.out.println("---------------------------");

                System.out.println();
                System.out.println();

                System.out.println("--------------------------");
                System.out.println("Beginning a new sale (Y/N)");
                ans = sc.next();
            }
        }

        if(ans.toLowerCase(Locale.ROOT).equals("n")){
            System.out.println("Total sales of the day is $ "+String.format("%.2f", sales));
            System.out.println("Thanks for using POST System. Goodbye.");
        }
    }
}
