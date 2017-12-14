
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Servidor {

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(3333);

        while (true) {
            Socket socket = server.accept();
            System.out.println("Servidor ON");
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            PrintStream out = new PrintStream(output);

            out.println("Digite o seu login: ");
            String log = in.readLine();
            System.out.println("Login do cliente: " + log);

            ///// Verifica se o login existe 
            BufferedReader b = new BufferedReader(new FileReader("D:\\_-_GoogleDrive\\_-_IFES\\_-_2017-2\\_-_REDES\\Trabalho 1\\Versao quase final\\MyWhatsApp - gabriel - 3011\\src\\logins.txt"));
            StringBuffer textoDoArquivo = new StringBuffer();
            String linha = null;

            int numeroDeOcorrencias = 0;
            while ((linha = b.readLine()) != null) {

                if (linha.equalsIgnoreCase(log)) {
                    numeroDeOcorrencias = 1;

                }
            }
            b.close();

            if (numeroDeOcorrencias > 0) {
                System.out.println("Login encontrado.");

                out.println("Digite 'sim' para receber msgs não lidas ou tecle Enter para enviar uma nova msgs.");
                String r = in.readLine();

                if ("sim".equals(r)) {
                    out.println("MSGS");

                    /////// Verifica se o usuário possui mensagens para receber
                    BufferedReader d = new BufferedReader(new FileReader("D:\\_-_GoogleDrive\\_-_IFES\\_-_2017-2\\_-_REDES\\Trabalho 1\\Versao quase final\\MyWhatsApp - gabriel - 3011\\src\\memoria.txt"));
                    StringBuffer textoDoArquivod = new StringBuffer();
                    String linhad = null;
                    while ((linhad = d.readLine()) != null) {
                        textoDoArquivod.append(linhad);
                        Matcher md = Pattern.compile(log).matcher(linhad);
                        while (md.find()) {

                            String[] dados = linhad.split(";");

                            for (int i = 0; i < dados.length; i++) {
                                System.out.println(dados[i]);
                            }
                            if (log.equals(dados[0])) {
                                out.println("Mensagem de " + dados[1] + ": " + dados[2]);
                            }

                            //logica para apagar a msg vem aqui
                        }
                    }
                    d.close();
                    out.println("CAMBIO");

                }

                ///// Destinatário
                out.println("Para enviar uma msg digite o nome do destinatário ou tecle Enter para sair: ");
                String dest = in.readLine();
                System.out.println("Destinatário do cliente: " + dest);

                ///// Tentar mandar para si faz desconectar!
                if (log.equals(dest)) {
                    out.println("FIM");
                }

                ///// Verifica se o destinatário existe no arquivo login.txt
                BufferedReader c = new BufferedReader(new FileReader("D:\\_-_GoogleDrive\\_-_IFES\\_-_2017-2\\_-_REDES\\Trabalho 1\\Versao quase final\\MyWhatsApp - gabriel - 3011\\src\\logins.txt"));
                StringBuffer textoDoArquivoc = new StringBuffer();
                String linhac = null;

                int numeroDeOcorrenciasc = 0;
                while ((linhac = c.readLine()) != null) {

                    if (linhac.equalsIgnoreCase(dest)) {
                        numeroDeOcorrenciasc = 1;

                    }
                }
                c.close();

                if (numeroDeOcorrenciasc > 0) {
                    System.out.println("Destinatário encontrado.");

                    ///// Mensagem 
                    out.println("Digite sua mensagem para " + dest + " :");
                    String message = in.readLine();
                    System.out.println("Mensagem de " + log + " para " + dest + " :" + message);

                    FileWriter arq = new FileWriter("D:\\_-_GoogleDrive\\_-_IFES\\_-_2017-2\\_-_REDES\\Trabalho 1\\Versao quase final\\MyWhatsApp - gabriel - 3011\\src\\memoria.txt", true);
                    PrintWriter gravarArq = new PrintWriter(arq);

                    gravarArq.println(dest + ";" + log + ";" + message);
                    gravarArq.flush();
                    out.println("FIM");
                } else {
                    System.out.println("Destinatário não encontrado.");
                    out.println("FIM");
                }
            } else {
                System.out.println("Login não encontrado.");
                out.println("FIM");
            }
        }
    }
}
