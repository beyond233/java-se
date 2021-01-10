package pojo;

/**
 * <p>项目文档: </p>
 *
 * @author beyond233 <a href="https://github.com/beyond233/"></a>
 * @version 1.0
 * @since 2020-04-16 16:59
 */

public class Book implements Comparable<Book> {
    private String name;
    private double price;

    public Book(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "pojo.Book{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }


    @Override
    public int compareTo(Book o) {
        if (o!=null) {
            //先按照价格进行排序
            int result = Double.compare(this.price, o.getPrice());
            //若价格相同按照书名进行排序
            return result == 0 ? name.compareTo(o.getName()) : result;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        System.out.println("*******调用Book类的equals()方法********");
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Book book = (Book) o;

        if (Double.compare(book.price, price) != 0) {
            return false;
        }
        return name != null ? name.equals(book.name) : book.name == null;
    }

    @Override
    public int hashCode() {
        System.out.println("*******调用Book类的hashCode()方法********");
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }




}
