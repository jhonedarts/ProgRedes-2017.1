import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

public class XMLValidator{

  public static void main(String[] args) throws Exception{
     DocumentBuilderFactory factory;
     factory =DocumentBuilderFactory.newInstance();

     factory.setValidating(true);

     
     DocumentBuilder builder;
     builder = factory.newDocumentBuilder();

     builder.parse(args[0]);

     System.out.println("XML de acordo com o DTD!");
  }
}