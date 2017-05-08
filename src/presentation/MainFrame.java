package presentation;

import blImpl.FileOperateController;
import blSer.FileOperateBlSer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

/**
 * Created by A on 2017/5/8.
 */
public class MainFrame {
    FileOperateBlSer fileOperateBlSer;
    public MainFrame(){
        fileOperateBlSer=new FileOperateController();
    }

    JFrame mainFrame;
    JPanel mainPanel;
    TextField directoryTextField;//文件所在目录
    JButton addButton;
    JButton pasteButton;
    JButton backButton;//返回按钮

    JPanel mainMenuPanel=new JPanel();

    public static void main(String [] args){
        MainFrame mainFrame=new MainFrame();
        mainFrame.buildGUI();
    }

    public void buildGUI(){
        mainFrame=new JFrame("File Manager");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addButton =new JButton("Add");
        ImageIcon icon1 = new ImageIcon("D:/java/FileManager/src/presentation/images/新建.png");
        Image temp1=icon1.getImage().getScaledInstance(40,60,icon1.getImage().SCALE_DEFAULT);
        icon1=new ImageIcon(temp1);
        addButton.setIcon(icon1);
        addButton.setContentAreaFilled(false);
        addButton.setBorderPainted(false);
        addButton.addActionListener(new AddActionListener());

        pasteButton =new JButton("Paste");
        ImageIcon icon2 = new ImageIcon("D:/java/FileManager/src/presentation/images/复制.png");
        Image temp2=icon2.getImage().getScaledInstance(40,60,icon2.getImage().SCALE_DEFAULT);
        icon2=new ImageIcon(temp2);
        pasteButton.setIcon(icon2);
        pasteButton.setContentAreaFilled(false);
        pasteButton.setBorderPainted(false);
        pasteButton.addActionListener(new DoPasteActionListener());

        mainMenuPanel =new JPanel();
        mainMenuPanel.add(addButton);
        mainMenuPanel.add(pasteButton);
        mainMenuPanel.setVisible(true);

        mainFrame.getContentPane().add(BorderLayout.NORTH,mainMenuPanel);

        FlowLayout flowLayout=new FlowLayout();
        mainPanel=new JPanel(flowLayout);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //添加目录
        JLabel diLable=new JLabel("Directory:");
        directoryTextField =new TextField("main/",100);
        backButton=new JButton("Back");
        ImageIcon icon3 = new ImageIcon("D:/java/FileManager/src/presentation/images/返回.png");
        Image temp3=icon3.getImage().getScaledInstance(40,60,icon3.getImage().SCALE_DEFAULT);
        icon3=new ImageIcon(temp3);
        backButton.setIcon(icon3);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.addActionListener(new BackActionListener());

        mainPanel.add(backButton);
        mainPanel.add(diLable);
        mainPanel.add(directoryTextField);

        showFile("D:/main/");

        mainFrame.getContentPane().add(BorderLayout.CENTER,mainPanel);
        mainFrame.setSize(1000,600);
        mainFrame.setLocationRelativeTo(null);
//        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    //文件夹具体内容的显示
    ArrayList<JPanel> filePanels;
    ArrayList<JButton> fileButtons;
    JPanel centerPanel;
    public int showFile(String name){
//        String lastname="";//要改成从操作地方获取的文件名称
//        String name="D:/"+directoryTextField.getText()+lastname;
        if(name.charAt(name.length()-1)!='/'){
            name=name+'/';
        }
        directoryTextField.setText(name.substring(3,name.length()));

        String [] folderNames=fileOperateBlSer.getFolder(name);
        if(folderNames==null){
            mainPanel.updateUI();
            mainPanel.setVisible(true);
            return 0;
        }
        centerPanel=new JPanel();
        centerPanel.setLayout(new FlowLayout());
        filePanels=new ArrayList<>();
        fileButtons=new ArrayList<>();
        for(int i=0;i<folderNames.length;i++){
            String tempname=folderNames[i];
            JButton tempButton=new JButton();
            tempButton.setText(tempname);
            if(tempname.length()<=4){
                ImageIcon icon = new ImageIcon("D:/java/FileManager/src/presentation/images/文件夹.png");
                Image temp=icon.getImage().getScaledInstance(40,60,icon.getImage().SCALE_DEFAULT);
                icon=new ImageIcon(temp);
                tempButton.setIcon(icon);
                tempButton.setContentAreaFilled(false);
                tempButton.setBorderPainted(false);
            }else {
                char isFile=tempname.charAt(tempname.length()-4);
                if(isFile=='.'){
                    String fileSuffix=tempname.substring(tempname.length()-4);//文件后缀
                    if((fileSuffix.charAt(0)=='.')&&(fileSuffix.charAt(1)=='z')&&(fileSuffix.charAt(2)=='i')&&(fileSuffix.charAt(3)=='p')){//如果是压缩文件
                        ImageIcon icon = new ImageIcon("D:/java/FileManager/src/presentation/images/压缩文件.png");
                        Image temp=icon.getImage().getScaledInstance(40,60,icon.getImage().SCALE_DEFAULT);
                        icon=new ImageIcon(temp);
                        tempButton.setIcon(icon);
                        tempButton.setContentAreaFilled(false);
                        tempButton.setBorderPainted(false);
                    }else if((fileSuffix.charAt(0)=='.')&&(fileSuffix.charAt(1)=='d')&&(fileSuffix.charAt(2)=='e')&&(fileSuffix.charAt(3)=='s')){//如果是加密文件
                        ImageIcon icon = new ImageIcon("D:/java/FileManager/src/presentation/images/加密.png");
                        Image temp=icon.getImage().getScaledInstance(40,60,icon.getImage().SCALE_DEFAULT);
                        icon=new ImageIcon(temp);
                        tempButton.setIcon(icon);
                        tempButton.setContentAreaFilled(false);
                        tempButton.setBorderPainted(false);
                    }else {//是其他文件时
                        ImageIcon icon = new ImageIcon("D:/java/FileManager/src/presentation/images/文件.png");
                        Image temp=icon.getImage().getScaledInstance(40,60,icon.getImage().SCALE_DEFAULT);
                        icon=new ImageIcon(temp);
                        tempButton.setIcon(icon);
                        tempButton.setContentAreaFilled(false);
                        tempButton.setBorderPainted(false);
                    }
                }else {
                    ImageIcon icon = new ImageIcon("D:/java/FileManager/src/presentation/images/文件夹.png");
                    Image temp=icon.getImage().getScaledInstance(40,60,icon.getImage().SCALE_DEFAULT);
                    icon=new ImageIcon(temp);
                    tempButton.setIcon(icon);
                    tempButton.setContentAreaFilled(false);
                    tempButton.setBorderPainted(false);
                }
            }
            fileButtons.add(tempButton);
            fileButtons.get(i).addActionListener(new FileOperateActionListener());

            JLabel tempFileLable=new JLabel();
            tempFileLable.setText(tempname);

            JPanel tempFilePanel=new JPanel();
            tempFilePanel.add(fileButtons.get(i));
            tempFileLable.setVisible(true);
            filePanels.add(tempFilePanel);

            centerPanel.add(filePanels.get(i));
        }
        centerPanel.setVisible(true);
        mainPanel.add(centerPanel);
        mainPanel.updateUI();
        mainPanel.setVisible(true);
        return 0;
    }


    //提示框
    JFrame hintFrame;
    JPanel hintPanel;
    JLabel hintLabel;
    JTextField hintTextField;
    JButton hintOkButton;
    JButton hintCancelButton;

    /**
     *
     * @param type 0指add操作，1指加密操作，2指解密操作
     * @param frameName
     * @param textFieldContent
     */
    public void showHint(int type,String frameName,String textFieldContent){
        hintFrame=new JFrame();
        hintFrame.setTitle(frameName);
        hintFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        hintLabel=new JLabel(textFieldContent);
        hintTextField=new JTextField("",20);
        hintOkButton=new JButton("OK");
        ImageIcon icon1 = new ImageIcon("D:/java/FileManager/src/presentation/images/确定.png");
        Image temp1=icon1.getImage().getScaledInstance(40,60,icon1.getImage().SCALE_DEFAULT);
        icon1=new ImageIcon(temp1);
        hintOkButton.setIcon(icon1);
        hintOkButton.setContentAreaFilled(false);
        hintOkButton.setBorderPainted(false);
        if(type==0){
            hintOkButton.addActionListener(new DoAddActionListener());
        }else if(type==1){
            hintOkButton.addActionListener(new DoEncodeActionListener());
        }else if(type==2){
            hintOkButton.addActionListener(new DoDecodeActionListener());
        }

        hintCancelButton=new JButton("Cancel");
        ImageIcon icon2 = new ImageIcon("D:/java/FileManager/src/presentation/images/取消.png");
        Image temp2=icon2.getImage().getScaledInstance(40,60,icon2.getImage().SCALE_DEFAULT);
        icon2=new ImageIcon(temp2);
        hintCancelButton.setIcon(icon2);
        hintCancelButton.setContentAreaFilled(false);
        hintCancelButton.setBorderPainted(false);
        hintCancelButton.addActionListener(new CancelActionListener());
        hintPanel=new JPanel();
        hintPanel.add(hintLabel);
        hintPanel.add(hintTextField);
        hintPanel.add(hintOkButton);
        hintPanel.add(hintCancelButton);

        hintPanel.setBorder(BorderFactory.createEmptyBorder(10,10,50,10));

        hintFrame.getContentPane().add(hintPanel);
        hintFrame.setSize(400,200);
        hintFrame.setLocationRelativeTo(null);
//        mainFrame.pack();
        hintFrame.setVisible(true);
    }

    //显示操作框
    JFrame operateFrame;
    JPanel operatePanel;
    JLabel fileNameLabel;
    JButton openOpeButton;
    JButton deleteOpeButton;
    JButton copyOpeButton;
    JButton encryptOpeButton;
    JButton compressOpeButton;
    public void showOperateFrame(String lastname){
        fileNameLabel=new JLabel();
        fileNameLabel.setText(lastname);

        deleteOpeButton=new JButton("Delete");
        ImageIcon icon1 = new ImageIcon("D:/java/FileManager/src/presentation/images/删除.png");
        Image temp1=icon1.getImage().getScaledInstance(40,60,icon1.getImage().SCALE_DEFAULT);
        icon1=new ImageIcon(temp1);
        deleteOpeButton.setIcon(icon1);
        deleteOpeButton.setContentAreaFilled(false);
        deleteOpeButton.setBorderPainted(false);
        deleteOpeButton.addActionListener(new DeleteActionListener());

        copyOpeButton=new JButton("Copy");
        ImageIcon icon2 = new ImageIcon("D:/java/FileManager/src/presentation/images/复制.png");
        Image temp2=icon2.getImage().getScaledInstance(40,60,icon2.getImage().SCALE_DEFAULT);
        icon2=new ImageIcon(temp2);
        copyOpeButton.setIcon(icon2);
        copyOpeButton.setContentAreaFilled(false);
        copyOpeButton.setBorderPainted(false);
        copyOpeButton.addActionListener(new CopyActionListener());

        operatePanel=new JPanel();
        operatePanel.add(deleteOpeButton);
        operatePanel.add(copyOpeButton);

        if(lastname.length()>4){
            String suffix=lastname.substring(lastname.length()-4);//后缀
            if((suffix.charAt(0)=='.')&&(suffix.charAt(1)=='d'&&(suffix.charAt(2)=='e')&&(suffix.charAt(3)=='s'))){
                encryptOpeButton=new JButton("Decode");
                ImageIcon icon3 = new ImageIcon("D:/java/FileManager/src/presentation/images/解密.png");
                Image temp3=icon3.getImage().getScaledInstance(40,60,icon3.getImage().SCALE_DEFAULT);
                icon3=new ImageIcon(temp3);
                encryptOpeButton.setIcon(icon3);
                encryptOpeButton.setContentAreaFilled(false);
                encryptOpeButton.setBorderPainted(false);
                encryptOpeButton.addActionListener(new DecodeActionListener());

                compressOpeButton=new JButton("Compress");
                ImageIcon icon4 = new ImageIcon("D:/java/FileManager/src/presentation/images/压缩文件.png");
                Image temp4=icon4.getImage().getScaledInstance(40,60,icon4.getImage().SCALE_DEFAULT);
                icon4=new ImageIcon(temp4);
                compressOpeButton.setIcon(icon4);
                compressOpeButton.setContentAreaFilled(false);
                compressOpeButton.setBorderPainted(false);
                compressOpeButton.addActionListener(new CompressActionListener());

                operatePanel.add(encryptOpeButton);
                operatePanel.add(compressOpeButton);
            }else if((suffix.charAt(0)=='.')&&(suffix.charAt(1)=='z'&&(suffix.charAt(2)=='i')&&(suffix.charAt(3)=='p'))){
                encryptOpeButton=new JButton("Encode");
                ImageIcon icon5 = new ImageIcon("D:/java/FileManager/src/presentation/images/加密.png");
                Image temp5=icon5.getImage().getScaledInstance(40,60,icon5.getImage().SCALE_DEFAULT);
                icon5=new ImageIcon(temp5);
                encryptOpeButton.setIcon(icon5);
                encryptOpeButton.setContentAreaFilled(false);
                encryptOpeButton.setBorderPainted(false);
                encryptOpeButton.addActionListener(new EncodeActionListener());

                compressOpeButton=new JButton("Uncompress");
                ImageIcon icon6 = new ImageIcon("D:/java/FileManager/src/presentation/images/解压缩.png");
                Image temp6=icon6.getImage().getScaledInstance(40,60,icon6.getImage().SCALE_DEFAULT);
                icon6=new ImageIcon(temp6);
                compressOpeButton.setIcon(icon6);
                compressOpeButton.setContentAreaFilled(false);
                compressOpeButton.setBorderPainted(false);
                compressOpeButton.addActionListener(new UncompressActionListener());

                operatePanel.add(encryptOpeButton);
                operatePanel.add(compressOpeButton);
            }else if(suffix.charAt(0)=='.'&&(suffix.charAt(1)=='t'&&(suffix.charAt(2)=='x')&&(suffix.charAt(3)=='t'))){//是文本文档
                openOpeButton=new JButton("Open");
                ImageIcon icon7 = new ImageIcon("D:/java/FileManager/src/presentation/images/打开.png");
                Image temp7=icon7.getImage().getScaledInstance(40,60,icon7.getImage().SCALE_DEFAULT);
                icon7=new ImageIcon(temp7);
                openOpeButton.setIcon(icon7);
                openOpeButton.setContentAreaFilled(false);
                openOpeButton.setBorderPainted(false);
                openOpeButton.addActionListener(new ShowFileActionListener());

                encryptOpeButton=new JButton("Encode");
                ImageIcon icon8 = new ImageIcon("D:/java/FileManager/src/presentation/images/加密.png");
                Image temp8=icon8.getImage().getScaledInstance(40,60,icon8.getImage().SCALE_DEFAULT);
                icon8=new ImageIcon(temp8);
                encryptOpeButton.setIcon(icon8);
                encryptOpeButton.setContentAreaFilled(false);
                encryptOpeButton.setBorderPainted(false);
                encryptOpeButton.addActionListener(new EncodeActionListener());

                compressOpeButton=new JButton("Compress");
                ImageIcon icon9 = new ImageIcon("D:/java/FileManager/src/presentation/images/压缩文件.png");
                Image temp9=icon9.getImage().getScaledInstance(40,60,icon9.getImage().SCALE_DEFAULT);
                icon9=new ImageIcon(temp9);
                compressOpeButton.setIcon(icon9);
                compressOpeButton.setContentAreaFilled(false);
                compressOpeButton.setBorderPainted(false);
                compressOpeButton.addActionListener(new CompressActionListener());

                operatePanel.add(openOpeButton);
                operatePanel.add(encryptOpeButton);
                operatePanel.add(compressOpeButton);
            }else if(suffix.charAt(0)!='.'){//是文件夹时
                openOpeButton=new JButton("Open");
                ImageIcon icon10 = new ImageIcon("D:/java/FileManager/src/presentation/images/打开.png");
                Image temp10=icon10.getImage().getScaledInstance(40,60,icon10.getImage().SCALE_DEFAULT);
                icon10=new ImageIcon(temp10);
                openOpeButton.setIcon(icon10);
                openOpeButton.setContentAreaFilled(false);
                openOpeButton.setBorderPainted(false);
                openOpeButton.addActionListener(new ShowFileActionListener());

                compressOpeButton=new JButton("Compress");
                ImageIcon icon11 = new ImageIcon("D:/java/FileManager/src/presentation/images/压缩文件.png");
                Image temp11=icon11.getImage().getScaledInstance(40,60,icon11.getImage().SCALE_DEFAULT);
                icon11=new ImageIcon(temp11);
                compressOpeButton.setIcon(icon11);
                compressOpeButton.setContentAreaFilled(false);
                compressOpeButton.setBorderPainted(false);
                compressOpeButton.addActionListener(new CompressActionListener());

                operatePanel.add(openOpeButton);
                operatePanel.add(compressOpeButton);
            }
        }else {//是文件夹时
            openOpeButton=new JButton("Open");
            ImageIcon icon12 = new ImageIcon("D:/java/FileManager/src/presentation/images/打开.png");
            Image temp12=icon12.getImage().getScaledInstance(40,60,icon12.getImage().SCALE_DEFAULT);
            icon12=new ImageIcon(temp12);
            openOpeButton.setIcon(icon12);
            openOpeButton.setContentAreaFilled(false);
            openOpeButton.setBorderPainted(false);
            openOpeButton.addActionListener(new ShowFileActionListener());

            compressOpeButton=new JButton("Compress");
            ImageIcon icon13 = new ImageIcon("D:/java/FileManager/src/presentation/images/压缩文件.png");
            Image temp13=icon13.getImage().getScaledInstance(40,60,icon13.getImage().SCALE_DEFAULT);
            icon13=new ImageIcon(temp13);
            compressOpeButton.setIcon(icon13);
            compressOpeButton.setContentAreaFilled(false);
            compressOpeButton.setBorderPainted(false);
            compressOpeButton.addActionListener(new CompressActionListener());

            operatePanel.add(openOpeButton);
            operatePanel.add(compressOpeButton);
        }

        operatePanel.setVisible(true);

        operateFrame=new JFrame("Select");
        operateFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        operateFrame.getContentPane().add(operatePanel);
        operateFrame.setSize(400,200);
        operateFrame.setLocationRelativeTo(null);
//        mainFrame.pack();
        operateFrame.setVisible(true);

    }

    //txt文本查看器
    JFrame txtFrame;
    JPanel txtPanel;
    JLabel txtTitleLable;
    JTextArea txtTextArea;
    JButton txtSaveButton;
    JButton txtCloseButton;
    public void showTxt(String title,String content){
        txtFrame=new JFrame();
        txtFrame.setTitle(title);
        txtFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        txtTitleLable=new JLabel();
        txtTitleLable.setText(title);
        txtTextArea=new JTextArea();
        txtTextArea.setColumns(30);
        txtTextArea.setText(content);

        txtCloseButton=new JButton("Close");
        ImageIcon icon1 = new ImageIcon("D:/java/FileManager/src/presentation/images/取消.png");
        Image temp1=icon1.getImage().getScaledInstance(40,60,icon1.getImage().SCALE_DEFAULT);
        icon1=new ImageIcon(temp1);
        txtCloseButton.setIcon(icon1);
        txtCloseButton.setContentAreaFilled(false);
        txtCloseButton.setBorderPainted(false);
        txtCloseButton.addActionListener(new TxtCloseActionListener());

        txtSaveButton=new JButton("Save");
        ImageIcon icon2 = new ImageIcon("D:/java/FileManager/src/presentation/images/保存.png");
        Image temp2=icon2.getImage().getScaledInstance(40,60,icon2.getImage().SCALE_DEFAULT);
        icon2=new ImageIcon(temp2);
        txtSaveButton.setIcon(icon2);
        txtSaveButton.setContentAreaFilled(false);
        txtSaveButton.setBorderPainted(false);
        txtSaveButton.addActionListener(new TxtSaveActionListener());

        txtPanel=new JPanel();
        txtPanel.add(txtTitleLable);
        txtPanel.add(txtTextArea);
        txtPanel.add(txtSaveButton);
        txtPanel.add(txtCloseButton);
        txtPanel.setVisible(true);

        txtFrame.getContentPane().add(txtPanel);
        txtFrame.setSize(400,200);
        txtFrame.setLocationRelativeTo(null);
//        mainFrame.pack();
        txtFrame.setVisible(true);
    }

    //内部类

    //增加一个文件/文件夹
    public class AddActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            showHint(0,"Add","Please input file name:");
        }
    }

    //实际增加一个文件/文件夹
    public class DoAddActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String lastname=hintTextField.getText();
            String firstname=directoryTextField.getText();
            String name="D:/"+firstname+lastname;
            hintFrame.setVisible(false);
            fileOperateBlSer.createFile(name);

            mainPanel.remove(centerPanel);
            showFile("D:/"+firstname);
        }
    }

    //取消增加一个文件夹
    public class CancelActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            hintFrame.setVisible(false);
        }
    }

    //复制一个文件/文件夹
    JPanel pasteHintPanel;
    JLabel pasteHintLabel;
    JTextField pasteHintTextField;//存储的是相对路径，即不包括"D:/"的路径
    public class CopyActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String lastname=fileNameLabel.getText();
            String firstname=directoryTextField.getText();
            String path=firstname+lastname;
            pasteHintLabel=new JLabel("Wait for paste");
            pasteHintTextField=new JTextField(path,60);
            pasteHintPanel=new JPanel();
            pasteHintPanel.add(pasteHintLabel);
            pasteHintPanel.add(pasteHintTextField);
            pasteHintPanel.setVisible(true);
            mainFrame.add(BorderLayout.SOUTH,pasteHintPanel);
            mainFrame.setVisible(true);
        }
    }

    //实际粘贴一个文件/文件夹
    public class DoPasteActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
//            showHint("Add","Please input file name:");
            String oriPath="D:/"+pasteHintTextField.getText();
            String desPath="D:/"+directoryTextField.getText();
            if (oriPath.length()<=4){
                if(oriPath.charAt(oriPath.length()-1)!='/'){
                    oriPath=oriPath+"/";
                }
            }else {
                if(oriPath.charAt(oriPath.length()-4)!='.'){
                    if(oriPath.charAt(oriPath.length()-1)!='/'){
                        oriPath=oriPath+"/";
                    }
                }
            }
            if(desPath.charAt(desPath.length()-1)!='/'){
                desPath=desPath+"/";
            }

            try {
                fileOperateBlSer.copyFile(oriPath,desPath);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

            //先删除之前的
            mainPanel.remove(centerPanel);
            //再显示之后的
            showFile(desPath);
        }
    }

    //删除
    public class DeleteActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            operateFrame.setVisible(false);
            String firstname="D:/"+directoryTextField.getText();
            String lastname=fileNameLabel.getText();
            String name=firstname+lastname;
            if (name.length()<=4){
                if(name.charAt(name.length()-1)!='/'){
                    name=name+"/";
                }
            }else {
                if(name.charAt(name.length()-4)!='.'){
                    if(name.charAt(name.length()-1)!='/'){
                        name=name+"/";
                    }
                }
            }

            fileOperateBlSer.deleteFile(name);
            //先删除之前的
            mainPanel.remove(centerPanel);
            //再显示之后的
            showFile(firstname);
        }
    }

    //加密
    public class EncodeActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            operateFrame.setVisible(false);
            //操作
            showHint(1,fileNameLabel.getText(),"Please input key:");
        }
    }

    //解密
    public class DecodeActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            operateFrame.setVisible(false);
            //操作
            showHint(2,fileNameLabel.getText(),"Please input key:");
        }
    }

    //实行加密操作
    public class DoEncodeActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String key=hintTextField.getText();
            hintFrame.setVisible(false);
            //操作
            String lastname=hintFrame.getTitle();
            String firstname="D:/"+directoryTextField.getText();
            String path=firstname+lastname;
            if (path.length()<=4){
                if(path.charAt(path.length()-1)!='/'){
                    path=path+"/";
                }
            }else {
                if(path.charAt(path.length()-4)!='.'){
                    if(path.charAt(path.length()-1)!='/'){
                        path=path+"/";
                    }
                }
            }
            fileOperateBlSer.encrypt(path,key);
            fileOperateBlSer.deleteFile(path);
            //先删除之前的
            mainPanel.remove(centerPanel);
            //再显示之后的
            showFile(firstname);
        }
    }

    //实行解密操作
    public class DoDecodeActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String key=hintTextField.getText();
            hintFrame.setVisible(false);
            //操作
            String lastname=hintFrame.getTitle();
            String firstname="D:/"+directoryTextField.getText();
            String path=firstname+lastname;
            if (path.length()<=4){
                if(path.charAt(path.length()-1)!='/'){
                    path=path+"/";
                }
            }else {
                if(path.charAt(path.length()-4)!='.'){
                    if(path.charAt(path.length()-1)!='/'){
                        path=path+"/";
                    }
                }
            }
            fileOperateBlSer.decode(path,key);
            fileOperateBlSer.deleteFile(path);
            //先删除之前的
            mainPanel.remove(centerPanel);
            //再显示之后的
            showFile(firstname);
        }
    }

    //压缩
    public class CompressActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            operateFrame.setVisible(false);
            //操作
            String lastname=fileNameLabel.getText();
            String firstname="D:/"+directoryTextField.getText();
            String name=firstname+lastname;
            if (name.length()<=4){
                if(name.charAt(name.length()-1)!='/'){
                    name=name+"/";
                }
            }else {
                if(name.charAt(name.length()-4)!='.'){
                    if(name.charAt(name.length()-1)!='/'){
                        name=name+"/";
                    }
                }
            }
            fileOperateBlSer.compress(name);
            //先删除之前的
            mainPanel.remove(centerPanel);
            //再显示之后的
            showFile(firstname);
        }
    }

    //解压缩
    public class UncompressActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            operateFrame.setVisible(false);
            //操作
            String lastname=fileNameLabel.getText();
            String firstname="D:/"+directoryTextField.getText();
            String name=firstname+lastname;
            if (name.length()<=4){
                if(name.charAt(name.length()-1)!='/'){
                    name=name+"/";
                }
            }else {
                if(name.charAt(name.length()-4)!='.'){
                    if(name.charAt(name.length()-1)!='/'){
                        name=name+"/";
                    }
                }
            }
            fileOperateBlSer.decompress(name);
            //先删除之前的
            mainPanel.remove(centerPanel);
            //再显示之后的
            showFile(firstname);
        }
    }

    //要对一个文件/文件夹进行操作
    public class FileOperateActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            //显示操作框
            JButton fileButton=(JButton) e.getSource();
            String lastname=fileButton.getText();
            showOperateFrame(lastname);
        }
    }

    //显示指定文件夹的具体内容
    public class ShowFileActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            operateFrame.setVisible(false);
            String lastname=fileNameLabel.getText();
            String firstname=directoryTextField.getText();
            String path="D:/"+firstname+lastname;
            if (path.length()<=4){
                if(path.charAt(path.length()-1)!='/'){
                    path=path+"/";
                }
            }else {
                if(path.charAt(path.length()-4)!='.'){
                    if(path.charAt(path.length()-1)!='/'){
                        path=path+"/";
                    }
                }
            }

            if(lastname.length()>4){
                String suffix=lastname.substring(lastname.length()-4);
                if(suffix.charAt(0)=='.'&&(suffix.charAt(1)=='t'&&(suffix.charAt(2)=='x')&&(suffix.charAt(3)=='t'))){//如果是txt，则打开文本文档
                    String content="";
                    fileOperateBlSer=new FileOperateController();
                    try {
                        content=fileOperateBlSer.getFile(path);
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    showTxt(lastname,content);
                }else {//是文件夹
                    //先删除之前的
                    mainPanel.remove(centerPanel);
                    //再显示之后的
                    showFile(path);
                }
            }else {//是文件夹
                //先删除之前的
                mainPanel.remove(centerPanel);
                //再显示之后的
                showFile(path);
            }

        }
    }

    public class TxtCloseActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            txtFrame.setVisible(false);
        }
    }

    public class TxtSaveActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String lastname=txtTitleLable.getText();
            String content=txtTextArea.getText();
            String firstname="D:/"+directoryTextField.getText();
            if(firstname.charAt(firstname.length()-1)!='/'){
                firstname=firstname+"/";
            }
            String path=firstname+lastname;
            try {
                fileOperateBlSer.writeFile(path,content);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            txtFrame.setVisible(false);
        }
    }

    public class BackActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            String sonDir=directoryTextField.getText();
            String dirs[]=sonDir.split("/");
            String parentDir="";
            if(dirs.length>1){
                for(int i =0;i<dirs.length-1;i++){
                    parentDir=parentDir+dirs[i]+"/";
                }
                directoryTextField.setText(parentDir);

                //先删除之前的
                mainPanel.remove(centerPanel);
                //再显示之后的
                showFile("D:/"+parentDir);
            }

        }
    }

}

