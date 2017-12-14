
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 3333);

        System.out.println("Conexão com o servidor estabelecida...");

        InputStream input = socket.getInputStream();
        OutputStream output = socket.getOutputStream();

        BufferedReader in = new BufferedReader(new InputStreamReader(input));
        PrintStream out = new PrintStream(output);

        Scanner scanner = new Scanner(System.in);
        
        String mensagem2 = in.readLine();
            
        System.out.println("Servidor: " + mensagem2);
       
     
        while (!"FIM".equals(mensagem2)) {
            String mensagem = scanner.nextLine();

            out.println(mensagem);                 
            
            mensagem2 = in.readLine();

            if(!"MSGS".equals(mensagem2)){            
                System.out.println("Servidor: " + mensagem2);            
            }
            
            if("MSGS".equals(mensagem2)){
                while(!"CAMBIO".equals(mensagem2)){
                    mensagem2 = in.readLine();
                    if(!"CAMBIO".equals(mensagem2)){
                        System.out.println("Servidor: " + mensagem2);                        
                    }                    
                }
                mensagem2 = in.readLine();
                System.out.println("Servidor: " + mensagem2);
            }       
                    
        }

        System.out.println("Encerrando conexão.");

        in.close();

        out.close();

        socket.close();

    }

}
