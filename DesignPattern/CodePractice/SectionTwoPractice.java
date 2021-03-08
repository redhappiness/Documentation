import java.util.ArrayList;
import java.util.List;
import java.io.*;
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
    }
}

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