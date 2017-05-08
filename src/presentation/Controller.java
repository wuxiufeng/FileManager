package presentation;

import blSer.FileType;
import javafx.fxml.FXML;

import javafx.scene.control.*;

import java.util.ArrayList;

/**
 * Created by A on 2017/5/7.
 */
public class Controller{
    Controller controller;
    ArrayList<FileType> fileTypes;
    ArrayList<String> names;

    @FXML
    private Button FirstButton;

    @FXML
    private Button SecondButton;

    @FXML
    private TextArea textField;

    public void File1Clicked(){
        textField.setText("hello1");
        System.out.print("ok1");
    }

    public void File2Clicked(){
        textField.setText("hello2");
        System.out.print("ok2");
    }

}
