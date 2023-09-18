package Vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {
    private Scene escena;
    private VBox vBox;
    private BorderPane borderPane;
    private GridPane grdteclado;
    private TextField txtresultado;
    private Button[][]arTeclado = new Button[4][4];
    //private Button btnborrar = new Button("Borrar");
    private Button btnlimpiar = new Button("C");
    private char []arDigitos = {'7','8','9','/','4','5','6','*','1','2','3','-','0','.','=','+'};
    private String numeroActual = ""; // Para almacenar el número actual
    private String operador = "";     // Para almacenar el operador actual
    private double resultado = 0.0;  // Para almacenar el resultado
    public Calculadora(){
        CrearUI();
        escena = new Scene(vBox,200,200);//carga la escena
        escena.getStylesheets().add(getClass().getResource("/Estilos/Calculadora.css").toString());//la ccss para cargarla
        this.setTitle("Calculadora");//titulo de la ventana
        this.setScene(escena);//carga la escena
        this.show();//la muestra
    }

    private void CrearUI(){
        int pos=0;//creo variable para su posterior uso en el ciclo
        grdteclado = new GridPane();//aqui se van a cargar los botones
        grdteclado.add(btnlimpiar,5,1);//aqui agregue a la seccion de botones uno que le llamo refrescar y me ayuda con el borrado del histotial por asi decirlo
        txtresultado = new TextField("0");//creo el textfile y le pongo un valor
        txtresultado.setAlignment(Pos.BASELINE_RIGHT);//todo lo escrito se horienta a la derecha
        txtresultado.setEditable(false);//no se puede escribir en la cajita

        for (int i=0; i<4;i++){
            for (int j=0;j<4;j++){//creacion de los ciclos para los botones y sus simbolos en ciclos anidados
                final String simbolo = arDigitos[pos] + ""; //aqui se mete el orden de los simbolos con la ayuda del arreglo ya declarado arribota
                arTeclado[i][j]=new Button(arDigitos[pos]+"");//creamos los botones
                arTeclado[i][j].setPrefSize(50,50);//se le asigna el tamaño a los botones
                arTeclado[i][j].setOnAction(e -> GenerarExpresion(simbolo));//aqui al pulsar los botones se mostraria en la cajita el simbolo que tienen
                grdteclado.add(arTeclado[i][j],i,j);//los botones se crean con los ciclos en orden
                pos++;//aumento para el proceso de creacion de botones
            }
        }
        btnlimpiar.setOnAction(e -> {//cuando se pulsa el boton de refrescar produce el borrado
            RefrescarCalcu();//mi metodo de refrescar o borrado de historial
        });

        vBox = new VBox(txtresultado,grdteclado);//permite ver todo
    }

  private void GenerarExpresion(String simbolo){//sirve para cuando se pulsa los botones
      if (esNumero(simbolo)) {//verifica si lo pulsado es un numero
          numeroActual += simbolo;//se contatena a la variable actual
          txtresultado.setText(numeroActual); // Actualiza el contenido del TextField
      } else if (esOperador(simbolo)) {//esto valida si es un operador
          if (!numeroActual.isEmpty()) {//verifica si esta vacio el numero actual
              calcularResultadoParcial();//llama el metodo
          }
          operador = simbolo;//se actualiza el opeardor
      } else if (simbolo.equals("=")) {//cuando se da el igual se acaba
          calcularResultadoFinal();//hace el metodo para hacer la operacion correspondiente
      }
    }

    private boolean esNumero(String simbolo) {//el metodo es booleano y recibe el simbolo pulsado por el usuario con el boton
        return simbolo.matches("[0-9.]");//se pone el rango de los valores aceptados que le puse los valores de 0 al 9 el punto decimal
    }
    private boolean esOperador(String simbolo) {//el metodo recibe lo pulsado por el usuario
        return simbolo.matches("[+ * / -]");//agarra los simbolos como lo que solo acepta con el matches
    }

    private void calcularResultadoParcial() {
        double numero = Double.parseDouble(numeroActual);//el valor ingresado se convierte a double
        if (operador.equals("+")) {//operacion suma
            resultado += numero;
        } else if (operador.equals("-")) {//operacion resta
            resultado -= numero;
        } else if (operador.equals("*")) {//operacion multiplicacion
            resultado *= numero;
        } else if (operador.equals("/")) {//operacion division
            if (numero != 0) {//para evitar la dision entre 0
                resultado /= numero;
            } else {//procuro para marcar el error de division entre 0
                resultado = 0;
                txtresultado.setText("NO SE PUEDE DIVIDIR ENTRE CERO");
                return; // Salir del método y mostrar el mensaje de error.
            }
        }
        numeroActual = "";//reinicio el valor del numero actual despues de salir de los if
        txtresultado.setText(String.valueOf(resultado));// se actualiza con el resultado parcial
    }
    private void calcularResultadoFinal() {
        if (!numeroActual.isEmpty()) {//verifica si hay un numero en el textfile
            calcularResultadoParcial();//llama al metodo donde se hacen las operaciones basicas
        }
    }

    private void RefrescarCalcu(){
        numeroActual="";//borra cualquier rastro del dato
        operador="";//borra los operadores
        resultado=0.0;//pone el resultado en 0
        txtresultado.setText("");//borra todo del textfile
    }
    }
