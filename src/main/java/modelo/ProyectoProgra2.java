

package modelo;

import java.util.Scanner;


public class ProyectoProgra2 {

    public static void main(String[] args) {
       // valida que contenga @
        Scanner sc = new Scanner(System.in );
        try{
        System.out.println("Digite su correo");
        String correo = sc.nextLine();
        
        if(correo.contains("@")){
            System.out.println("correo valido");
        }else{
            System.out.println("correo invaalido ");  
        }
           
        }catch(Exception e){
            System.out.println("");
        }
        
        //valida que no dijite letras 
        try{
        System.out.println("Digite su edad");
        String edad = sc.nextLine();
        Integer.parseInt(edad);
            System.out.println(edad);
        
        }catch(Exception e){
            System.out.println("No se permite letras");   
        }
        
        //valida que no digite letras
         try {
            System.out.println("Digite su nombre:");
            String nombre = sc.nextLine();

            for (int i = 0; i < nombre.length(); i++) {
                char letra = nombre.charAt(i);
                
                if (!Character.isLetter(letra)) {
                    throw new Exception("El nombre solo puede contener letras");
                }
            }

            System.out.println("Nombre valido.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
} 