import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        //port(80);
        //staticFileLocation("/public");
        //getHtml("index.html");
        //get("/hello", (req, res) -> {
        //    res.type("text/html");
        //    return "Hello World";
        //});

        //get("/encode", (req, res) ->{
        //    res.type("application/json");
//
        //})
        Scanner keyboard = new Scanner(System.in);
        System.out.println("encode or decode?");
        String eord = keyboard.nextLine();
        if(eord.equals("encode")){
            System.out.println("Input your message");
            String message = keyboard.nextLine()+"\u0000";
            System.out.println("Type your input file");
            String fileName = keyboard.nextLine();
            System.out.println("Type your output file");
            String outName = keyboard.nextLine();
            File inFile = new File(fileName);
            File outFile = Encoder.encode(inFile, message, outName);
            try{
                outFile.createNewFile();
            }catch(IOException ioe){
                System.out.println("error: can't write file");
            }

        }else if(eord.equals("decode")){
            System.out.println("Type your file");
            String fileName = keyboard.nextLine();
            File decodeFile = new File(fileName);
            System.out.println(Encoder.decode(decodeFile));
        }else{
            System.out.println("I'm afraid I can't do that Starfox");
        }

    }
}