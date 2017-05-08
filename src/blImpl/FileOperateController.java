package blImpl;

import blSer.FileOperateBlSer;
import blSer.FileType;

import java.io.*;

/**
 * Created by A on 2017/5/7.
 */
public class FileOperateController implements FileOperateBlSer {
//    public static void main(String [] args) throws Exception {
//        FileOperateController f=new FileOperateController();
//        String path="D:/java/FileManager/src/data/b.txt";
////        f.createFile(path);
////        f.deleteFile(path);
////        f.getFolder(path);
////        f.writeFile("D:/java/FileManager/src/data/b.txt","wdmzswxf");
////        System.out.print(f.getFile(path));
////        f.copyFile("D:/java/FileManager/src/data/","D:/java/FileManager/src/shi/");
////        f.encrypt("D:/java/FileManager/src/data/b.txt","wxf");
////        f.decode("D:/java/FileManager/src/data/b.txt.des","aaa");
////        f.compress(path);
//        f.decompress("D:/main/a.txt.zip");
//    }

    @Override
    public boolean createFile( String name) {
        if(name==null){
            return false;
        }
        Create create=new Create();
        File file=new File(name);
        if(name.length()>4){
            char x=name.charAt(name.length()-4);
            if(x=='.'){
                create.createFile(name);//创建文件
            }else {
                create.createDir(name);//创建目录
            }
        }else {
            create.createDir(name);//创建目录
        }

        return (file.exists());
    }

    @Override
    public boolean deleteFile(String name) {
        DeleteFileUtil deleteFileUtil=new DeleteFileUtil();
        return deleteFileUtil.delete(name);//可以删除文件/文件夹
    }

    @Override
    public String[] getFolder(String folder) {
        File file=new File(folder);
        String[] filename=file.list();
        return filename;
    }

    @Override
    public boolean writeFile(String name,String content) throws IOException{
        File file=new File(name);
        BufferedWriter out = new BufferedWriter(new FileWriter(file));
        out.write(content); // \r\n即为换行
        out.flush(); // 把缓存区内容压入文件
        out.close(); // 最后记得关闭文件
        return true;
    }

    @Override
    public String getFile(String filename) throws Exception {
        String a="";
        BufferedReader reader= new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
        String line=null;
        for(;(line=reader.readLine())!=null;){
            a+=line+"\n";
        }
        reader.close();
        return a;
    }

    @Override
    public boolean copyFile(String oriName, String nextName) throws Exception{
        File src=new File(oriName);
        File dest=new File(nextName);
        if(src.isFile()){
            String filename=oriName.substring(oriName.lastIndexOf("/")+1);
            String newFile=nextName+filename;
            createFile(newFile);

            String content=getFile(oriName);
            writeFile(newFile,content);
        }else {//是目录时
            if (!dest.exists()) {
                dest.mkdir();
            }
            String behinds[]=oriName.split("/");
            String behindLast=behinds[behinds.length-1];
            String nextFolder=nextName+behindLast+"/";
            createFile(nextFolder);

            String files[] = src.list();
            for (String file : files) {
                String ori=oriName+file+"/";
                if(file.length()>4){
                    char isFile=file.charAt(file.length()-4);
                    if(isFile=='.'){
                        ori=oriName+file;
                    }
                }
//                ori=oriName+file+"/";
//                String next=nextName+file;
                // 递归复制
                copyFile(ori,nextFolder);
            }
        }
//        if (src.isDirectory()) {
//            if (!dest.exists()) {
//                dest.mkdir();
//            }
//            String files[] = src.list();
//            for (String file : files) {
//                File srcFile = new File(src, file);
//                File destFile = new File(dest, file);
//                // 递归复制
//                copyFile(srcFile.getName(), destFile.getName());
//            }
//        }
        return false;
    }

    @Override
    public boolean encrypt(String name,String password) {
        TestDES testDES=new TestDES(password);
        String desFile=name+".des";
        try {
            testDES.encrypt(name,desFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean decode(String name,String password) {
        TestDES testDES=new TestDES(password);
        String desFile=name.substring(0,name.length()-4);
        try {
            testDES.decrypt(name,desFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public boolean compress(String name) {
        String oriName=name;

        ZIPUtil zipUtil=new ZIPUtil();
        if(name.length()>4){
            if(name.charAt(name.length()-4)=='.'){
                name=name.substring(0,name.lastIndexOf("."));
            }
        }
        zipUtil.zip(oriName,name+".zip");
        return true;
    }

    @Override
    public boolean decompress(String name) {
        ZIPUtil zipUtil=new ZIPUtil();
//        String des=name.substring(0,name.length()-4);
        String des=name.substring(0,name.lastIndexOf("/"))+"/";
        zipUtil.unZip(name,des);
        return true;
    }

}
