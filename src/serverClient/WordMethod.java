package serverClient;

public interface WordMethod {
public String searchword(String word,String filepath) throws Exception;
public String addword(String word,String meaning,String filepath) throws Exception;
public String deleteword(String word,String filepath) throws Exception;
}
