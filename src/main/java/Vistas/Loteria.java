package Vistas;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Loteria extends Stage {
    private Scene escena;
    private HBox hPrincipal, HBtonseleccion;
    private VBox vTablilla, vMazo; //donde va a estar la tablilla y las cartas
    private ImageView imgCarta;//permite visualizar la imagen de la carta

    private Button[][] arBtnTablilla = new Button[4][4];//creamos la tablilla de la loteria
    private Button btnAnterior, btnSiguiente, btoIniciar, btoFin;
    private GridPane grdTablilla;
    private int TablillaActual = 0;//ayuda para abrir con la tabla numero 1
    private String[] arImagenes = {"1.jpg", "2.jpg", "3.jpg", "4.jpg", "5.jpg", "6.jpg", "7.jpg", "8.jpg", "9.jpg", "10.jpg"
            , "11.jpg", "12.jpg", "13.jpg", "14.jpg", "15.jpg", "16.jpg", "17.jpg", "18.jpg", "19.jpg", "20.jpg", "21.jpg", "22.jpg"
            , "23.jpg", "24.jpg", "25.jpg", "26.jpg", "27.jpg", "28.jpg", "29.jpg", "30.jpg", "31.jpg", "32.jpg", "33.jpg", "34.jpg"
            , "35.jpg", "36.jpg", "37.jpg", "38.jpg", "39.jpg", "40.jpg", "41.jpg", "42.jpg", "43.jpg", "44.jpg", "45.jpg", "46.jpg"
            , "47.jpg", "48.jpg", "49.jpg", "50.jpg", "51.jpg", "52.jpg", "53.jpg", "54.jpg", "55.jpg", "56.jpg", "57.jpg", "58.jpg"
            , "59.jpg", "60.jpg", "61.jpg", "62.jpg", "63.jpg", "64.jpg", "65.jpg", "66.jpg", "67.jpg", "68.jpg", "69.jpg", "70.jpg"
            , "71.jpg", "72.jpg", "73.jpg", "74.jpg", "75.jpg", "76.jpg", "77.jpg", "78.jpg", "79.jpg", "80.jpg"};

    private Timer timer;
    private int mazoCon = 0;


    public Loteria() {//entra la interfaz
        CrearUI();
        escena = new Scene(hPrincipal, 800, 600);//tamaño de la pantalla
        this.setTitle("Loteria");//pone el titulo de la ventana
        this.setScene(escena);//carga la escena
        this.show();//ve la pantalla
    }

    private void CrearUI() {
        CrearTablilla(0);
        CrearTablilla(1);
        CrearTablilla(2);
        CrearTablilla(3);
        CrearTablilla(4);
        CrearMazo();

        btnAnterior = new Button("<");//crea los botones de abajo
        btnAnterior.setPrefSize(200, 100);//tamaño de los botones
        btnSiguiente = new Button(">");//crea los botones de abajo
        btnSiguiente.setPrefSize(200, 100);//tamaño de los botones

        btnAnterior.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TablillaActual = (TablillaActual - 1 + 5) % 5;//retrocede en las tablillas
                ActualizarTablillas();//pone la tablilla
            }
        });

        btnSiguiente.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TablillaActual = (TablillaActual + 1) % 5;//Avanza en tablillas
                ActualizarTablillas();//pone las tablillas
            }
        });

        HBtonseleccion = new HBox(btnSiguiente, btnAnterior);//une los botones en un espacio hasta abajo
        vTablilla = new VBox(grdTablilla, HBtonseleccion);//aqui se agrega la tablilla y la opcion de los botones
        vTablilla.setSpacing(20);//separa los botones

        hPrincipal = new HBox(vTablilla, vMazo);//agrega la tablilla y el mazo en la pantalla principal
        hPrincipal.setPadding(new Insets(20));//da bordes


    }

    private void CrearMazo() {
        Image imgDorso = new Image(new File("C:\\Users\\Hp\\Documents\\estructura de datos\\demo1\\src\\main\\java\\Imagenes\\Dorso.jpg").toURI().toString());//cragamos laruta absoluta de la mimagen
        imgCarta = new ImageView(imgDorso);//permite ver la imagen
        imgCarta.setFitWidth(250);//ancho de imagen
        imgCarta.setFitHeight(400);//altura de la imagen
        btoIniciar = new Button("Iniciar");//crear un boton iniciar
        btoIniciar.setOnAction(event -> IniciarMazo());
        btoFin = new Button("Pausar");
        btoFin.setOnAction(event -> DetenerMazo());
        vMazo = new VBox(imgCarta, btoIniciar, btoFin);//se le crea en la vertical mazo y se le agrga la imagen y el  boton
    }

    private void CrearTablilla(int Tablillas) {
        grdTablilla = new GridPane();//se crea un gridpane
        int pos = Tablillas * 16;//Crea para el indice de la tablilla
        for (int i = 0; i < 4; i++) {//crea la matriz que se vizualiza para de la loteria
            for (int j = 0; j < 4; j++) {
                if (pos < arImagenes.length) {
                    Image imgCarta = new Image("C:\\Users\\Hp\\Documents\\estructura de datos\\demo1\\src\\main\\java\\Imagenes\\" + arImagenes[pos]);//cargas las imagenes en orden del arreglo
                    ImageView imv = new ImageView(imgCarta);//muestra la carta
                    imv.setFitWidth(100);//ancho de la imagen
                    imv.setFitHeight(100);

                    arBtnTablilla[i][j] = new Button();
                    arBtnTablilla[i][j].setGraphic(imv);
                    arBtnTablilla[i][j].setPrefSize(100, 140);
                    grdTablilla.add(arBtnTablilla[i][j], i, j);
                    pos++;
                }
            }
        }
    }


    private void ActualizarTablillas() {
        vTablilla.getChildren().remove(grdTablilla);//no deja ver la tablilla actual
        CrearTablilla(TablillaActual);//crea la nueva tablilla dependiendo el orden
        vTablilla.getChildren().add(grdTablilla);
    }

    private void IniciarMazo() {
        btnAnterior.setDisable(true);
        btnSiguiente.setDisable(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (mazoCon < arImagenes.length) {
                    Image img = new Image("C:\\Users\\Hp\\Documents\\estructura de datos\\demo1\\src\\main\\java\\Imagenes\\" + arImagenes[mazoCon]);
                    imgCarta.setImage(img);
                    MarcarCartaEnTablilla(img);
                    mazoCon++;

                } else {
                    timer.cancel();
                    btnAnterior.setDisable(false);
                    btnSiguiente.setDisable(false);
                }
            }
        }, 0, 5000);
    }

    private void DetenerMazo() {
        if (timer != null) {
            timer.cancel();
        }
    }

    private void MarcarCartaEnTablilla(Image imagenMazo) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (arBtnTablilla[i][j] != null) {
                    ImageView imageView = (ImageView) arBtnTablilla[i][j].getGraphic();
                    Image imagenBoton = imageView.getImage();

                    // Compara la imagen del mazo con la imagen del botón
                    if (imagenBoton != null && imagenMazo != null && imagenBoton.getUrl().equals(imagenMazo.getUrl())) {
                        arBtnTablilla[i][j].setDisable(true); // Marca el botón como seleccionado
                    }
                }
            }
        }
        verificarEstadoJuego();
    }

    private void verificarEstadoJuego() {
        boolean todasDesabilitadas = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (arBtnTablilla[i][j] != null && !arBtnTablilla[i][j].isDisabled()) {
                    todasDesabilitadas = false;
                    break;
                }
            }
        }

       if (todasDesabilitadas){
           Platform.runLater(() -> mostrarMensajeJuego(true));
       }
    }

    private void mostrarMensajeJuego(boolean ganador) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        if (ganador) {
            alert.setTitle("¡Felicidades!");
            alert.setContentText("¡Has ganado el juego!");
        } else {
            alert.setTitle("¡Perdiste!");
            alert.setContentText("No todas las cartas de la plantilla fueron marcadas. ¡Inténtalo de nuevo!");
        }

        alert.showAndWait();
    }

}
