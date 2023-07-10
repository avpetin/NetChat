import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Settings {
    int port = 0;
    public int setPortFromFile(String settings){
        try (BufferedReader fileReader = new BufferedReader(new FileReader(settings))){
            if (fileReader.ready()) {
                port = Integer.parseInt(fileReader.readLine());
            }
            else{
                System.out.println("Порт сервера не установлен");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return port;
    }

}
