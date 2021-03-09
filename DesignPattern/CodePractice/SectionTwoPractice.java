import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.stream.Stream;
public class SectionTwoPractice
{   
    public static void main(String[] args)
    {
        System.out.println("Single Responsibility Principle :");
        Journal j =  new Journal();
        j.addEntity("First");
        j.addEntity("Second");
        System.out.print(j.toString());
        Persistence p = new Persistence();
        String outputFile= "C:/Users/ywei/Documents/Work/documentation/CommonKnowledge/Documentation/DesignPattern/SingleResponsibilityPrinciple.txt";
        p.saveToFile(j, outputFile, true);
        // Open txt file using notepad in Windows 
        try
        {
            Runtime.getRuntime().exec("notepad.exe " + outputFile);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        System.out.println("OCP:");
        Product apple = new Product(Color.RED, Size.SMALL, "apple");
        Product ball = new Product(Color.RED, Size.SMALL, "ball");
        Product pear = new Product(Color.GREEN, Size.SMALL, "pear");
        Product waterMelon = new Product(Color.GREEN, Size.MEDIAN, "waterMelon");
        Product ballEight = new Product(Color.BLACK, Size.SMALL, "Black8");
        List<Product> products = new ArrayList<Product>();
        products.add(apple);
        products.add(ball);
        products.add(pear);
        products.add(waterMelon);
        products.add(ballEight);
        ProductFilter pf = new ProductFilter();
        pf.filter(products, new ColorSpecification(Color.GREEN)).forEach( product -> System.out.println(""+ product.name +" is Green"));
        pf.filter(products, new SizeSpecification(Size.MEDIAN)).forEach( product -> System.out.println(""+ product.name +" is Median"));
        pf.filter(products, new AndSpecification<>(new ColorSpecification(Color.RED), new SizeSpecification(Size.SMALL)))
        .forEach(product -> System.out.println(""+ product.name +" is Red and Small"));
    }
}

/** 
 * Example of Sinlge Responsibility Principle
 * Journal class only stores properiteis and mehtods to its own
*/
class Journal
{
    public List<String> entities = new ArrayList<String>();
    public int count = 0;
    public void addEntity(String entity)
    {
        entities.add(""+ (++count) + ": "+ entity);
    }
    @Override
    public String toString()
    {
        return String.join(System.lineSeparator(), entities);
    }
}
/** 
 * Example of Sinlge Responsibility Principle
 * Persistanece class stores methods that manipulate the Journal Class.
*/
class Persistence
{
    public void saveToFile(Journal journal, String filename, boolean overwrite)
    {
        if (overwrite || new File(filename).exists())
        {
            //PrintStream to write to a file
            try (PrintStream out = new PrintStream(filename)) {
                out.println(journal.toString());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
/**
 * Example of Open-closed Principle
 */
enum Color
{
    RED,
    BLUE,
    GREEN,
    BLACK,
    WHITE
}
/**
 * Example of Open-closed Principle
 */
enum Size
{
    SMALL,
    MEDIAN,
    LARGE,
    HUGE
}
/**
 * Example of Open-closed Principle
 */
class Product
{
    public Color color;
    public Size size;
    String name;
    public Product(Color color, Size size, String name)
    {
        this.name = name;
        this.color = color;
        this.size =  size;
    }
}
/**
 * Example of Open-closed Principle
 * using Specification pattern and take in genetic variable as input
 */
interface Specification<T>
{
    public boolean isSatisfied(T item);
}
/**
 * Example of Open-closed Principle
 * using Specification pattern and take in genetic variable as input
 * using java Stream Class please see Stream tutorial for more details. 
 */
interface Filter<T>
{
    public Stream<T> filter(List<T> items, Specification<T> spec);
}
class ProductFilter implements Filter<Product>
{
    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec)
    {
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
}
/**
 * Example of Open-closed Principle
 */
class ColorSpecification implements Specification<Product>
{
    private Color color;
    public ColorSpecification(Color color){ this.color = color;}
    @Override
    public boolean isSatisfied(Product item)
    {
        return item.color == color;
    }
}
/**
 * Example of Open-closed Principle
 */
class SizeSpecification implements Specification<Product>
{
    private Size size;
    public SizeSpecification(Size size){ this.size = size;}
    @Override
    public boolean isSatisfied(Product item)
    {
        return item.size == size;
    }
}
/**
 * Example of Open-closed Principle
 */
class AndSpecification<T> implements Specification<T>
{
    Specification<T> first, second;
    public AndSpecification(Specification<T> first, Specification<T> second)
    { 
        this.first = first;
        this.second=  second;
    }
    @Override
    public boolean isSatisfied(T item)
    {
        return first.isSatisfied(item) && second.isSatisfied(item);
    }
}