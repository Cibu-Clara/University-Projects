import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Product{
    // we will lock the selling based on the Product
    private int id ;
    private int price;
    private int quantity;
    private Lock lock;

    public Product (int id, int price, int quantity) {
        this.id = id;
        this.price = price;
        this.quantity= quantity;
        this.lock = new ReentrantLock();
    }

    public int getId() {
        return id;
    }

    public int getAvailableQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }

    public void sell(int sell_quantity) throws InterruptedException {
        if (lock.tryLock(10, TimeUnit.SECONDS)) {// to avoid thread starvation and deadlock
            if (sell_quantity <= quantity) {
                quantity -= sell_quantity;
            } else {
                throw new IllegalArgumentException("Not enough available quantity for product with ID " + id + " have " + Integer.toString(this.getAvailableQuantity()) + " want " + Integer.toString(sell_quantity));
            }

            lock.unlock();
        }
    }
}

class BillItem{
    // a product along with its quantity , and the cost of the product sale
    private Product product;
    private int quantity;

    public BillItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public double getItemTotalPrice() {
        return product.getPrice() * quantity;
    }
}

class Bill{
    private List<BillItem> items;
    private int totalPrice;

    public Bill() {
        this.items =new ArrayList<>();
        this.totalPrice= 0;
    }

    public void addItem(Product product, int quantity) {
        BillItem billItem = new BillItem(product, quantity);
        items.add(billItem);
        totalPrice += billItem.getItemTotalPrice();

    }

    public List<BillItem> getItems() {
        return items;
    }

    public int getTotalPrice() {
        return totalPrice;
    }
}

class Inventory{
    private int money;
    private List <Bill> bills;
    private List<Product> products ;
    private Lock lock;
    public Inventory(List<Product> products) {
        this.money=0;
        this.products = products;
        this.bills=new ArrayList<>();
        this.lock= new ReentrantLock();
    }

    public void addBill( Bill bill ,int  new_money ) throws InterruptedException {
        if (lock.tryLock(10, TimeUnit.SECONDS)) {
            this.bills.add(bill);
            this.money += new_money;
            lock.unlock();
        }
    }

    public int getMoney() {
        return money;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public List<Product> getProducts() {
        return products;
    }
}

class Sale implements Runnable {

    private int id; // to know which thread is
    private Inventory inventory;
    private Random random;

    public Sale(int id, Inventory inventory) {
        this.id = id;
        this.inventory = inventory;
        this.random = new Random();
    }

    public int stillQuantity() {
        List<Product> products = inventory.getProducts();
        int maximum = 0;
        for (Product product : products) {
            if (product.getAvailableQuantity() > maximum) {
                maximum = product.getAvailableQuantity();
            }
        }
        return maximum;
    }

    @Override
    public void run() {
        // while so that it continues to try and make sells
        int max_quantity = stillQuantity();
        while (max_quantity != 0) {
            System.out.println("Hello from thread " + Thread.currentThread().getId());
            // create a new bill
            Bill new_bill = new Bill();
            //randomly choose how many items to purchase
            int number_of_items_purchased = random.nextInt(1, 3);
            int number = 0;
            while (number != number_of_items_purchased) {
                max_quantity = stillQuantity();
                // randomly choose a product
                int productId = random.nextInt(1, 6);
                // randomly choose a quantity
                int quantity = random.nextInt(1, max_quantity);

                List<Product> products = inventory.getProducts();
                for (Product product : products) {
                    if (product.getId() == productId) {
                        try {
                            product.sell(quantity);
                            new_bill.addItem(product, quantity);
                        } catch (InterruptedException | IllegalArgumentException e) {
                            e.printStackTrace();
                        }
                    }
                }
                number += 1;
            }
            try {
                inventory.addBill(new_bill, new_bill.getTotalPrice());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("New Bill Added  " + Thread.currentThread().getId());
        }
    }
}

class InventoryCheck implements Runnable {
    private Inventory inventory;

    public InventoryCheck(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public void run() {
        System.out.println("Performing inventory check...");
        List<Bill> bills = inventory.getBills();
        int totalMoney = 0;

        // Check that all the sold products and money are justified by the recorded bills
        for (Bill bill : bills) {
            for (BillItem item : bill.getItems()) {
                totalMoney += item.getItemTotalPrice();
            }
        }

        if (totalMoney != inventory.getMoney()) {
            System.out.println("Error: Total money not justified by bills!");
        }

        System.out.println("Inventory check completed.");
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // initialise the  products
        Product product1 = new Product(1, 10, 100);
        Product product2 = new Product(2, 20, 50);
        Product product3 = new Product(3, 5, 200);
        Product product4 = new Product(4, 15, 75);
        Product product5 = new Product(5, 8, 150);


        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);

        //initialise the Inventory
        Inventory inventory = new Inventory(products);

        //create threads to take care of inventory check
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(new InventoryCheck(inventory),1,20,TimeUnit.SECONDS);

        Sale[] sales = new Sale[6];
        ExecutorService service = Executors.newFixedThreadPool(6);

        try {
            for (int i = 0; i < 6; i++) {
                sales[i] = new Sale(i + 1, inventory);
                service.execute(sales[i]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            service.shutdown();
            scheduler.awaitTermination(10, TimeUnit.MINUTES); // Wait for the scheduler to finish
            scheduler.shutdown();
        }
    }

}
// 1 lock is the Product , so no 2 threads can change the quantity at the same time
// 2 lock is the inventory so no 2 threads can add a bill at the same time

// the case I presented is the optimal one , in which we lock on the product itself , so we can perform 2 sells at the same
// time if the sells are of different products . If we were to lock on the operation of selling, then the time for the
// transactions will be increased . Considering the number of threads , if we have multiple threads allocated using the
// executor service then the time it takes to create a new thread for a new transaction will be decreased , as the executor
// keeps the threads alive instead of killing them when one task is done and reuses them when it needs to ; also, by creating
// multiple threads we can perform more sales at the same time if those sells are of different products ; However if we use the
// Cached thread pool from ExecutorService which makes new threads when a new task appears and kills them after 60 seconds of
// being idle the time could be increased rather than decreased , even though each thread is dealing with a single task , the fact that it
// kills it and creates a new thread would be more time-consuming than waiting for a thread to finish and reuse it. Also, a big number of
// threads can make the app work slower because of the cpu