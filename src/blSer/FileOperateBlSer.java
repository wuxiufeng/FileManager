package blSer;

import java.io.IOException;

/**
 * Created by A on 2017/5/7.
 1.实现文件夹的创建、删除、进入
 2.实现当前文件夹下的内容罗列。
 3.实现文件拷贝和文件夹拷贝（文件夹拷贝指深度拷贝，包括所有子目录和文件）。
 4.实现指定文件的加密和解密。
 5.实现指定文件和文件夹的压缩。
 6.实现压缩文件的解压。
 */
public interface FileOperateBlSer {
    /**
     * 文件/文件夹的创建
     * @param name 文件/文件夹名称
     * @return
     */
    public boolean createFile(String name);

    /**
     * 文件/文件夹的删除
     * @param name 文件/文件夹名称
     * @return
     */
    public boolean deleteFile(String name);

    /**
     * 获取一个文件夹里的内容
     * @param folder
     * @return
     */
    public String[] getFolder(String folder);

    /**
     * 获取一个文件的内容
     * @param filename
     * @return
     */
    public String getFile(String filename) throws Exception;

    /**
     * 写一个文件
     * @param name
     * @param content
     * @return
     * @throws IOException
     */
    public boolean writeFile(String name,String content) throws IOException;

    /**
     * 复制文件/文件夹
     * @param oriName 原本的文件/文件夹
     * @param nextName 复制后的文件/文件夹
     * @return
     */
    public boolean copyFile(String oriName,String nextName) throws Exception;

    /**
     * 加密文件/文件夹
     * @param name
     * @return
     */
    public boolean encrypt(String name,String password);

    /**
     * 解密文件/文件夹
     * @param name
     * @return
     */
    public boolean decode(String name,String password);

    /**
     * 压缩文件/文件夹
     * @param name
     * @return
     */
    public boolean compress(String name);

    /**
     * 解压缩文件/文件夹
     * @param name
     * @return
     */
    public boolean decompress(String name);

}
