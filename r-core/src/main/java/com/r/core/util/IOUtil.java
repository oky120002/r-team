package com.r.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

/**
 * IO实用工具
 * 
 * @author Rain
 * @date 2012年1月30日
 */
public abstract class IOUtil {

    /** 获得当前程序工作目录 */
    public static String getCurrentWorkingDirectory() {
        return SystemUtils.USER_DIR;
    }

    /**
     * 压缩文件
     * 
     * @param zipFileName
     *            压缩文件的保存路径(包括文件名的绝对路径)
     * @param list
     *            文件列表(包括文件名的绝对路径)
     * @return boolean true:有生成文件|false:没有生成文件
     * @throws FileNotFoundException
     *             根据传入的文件名创建文件失败或者找不到传入的文件路径时抛出此异常
     * @throws IOException
     *             创建文件,压缩文件,写入文件错误时,抛出此异常
     */
    public static boolean zipFilesFromFileName(String zipFileName, List<String> list) throws FileNotFoundException, IOException {
        if (list != null && list.size() > 0) {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
            for (String path : list) {
                File file = new File(path);
                out.putNextEntry(new ZipEntry(file.getName()));
                FileInputStream in = new FileInputStream(file);
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                in.close();
            }
            out.close();
            return true;
        }
        return false;
    }

    /**
     * 压缩文件
     * 
     * @param zipFileName
     *            压缩文件的保存路径(包括文件名的绝对路径)
     * @param list
     *            文件列表
     * @return boolean true:有生成文件|false:没有生成文件
     * @throws FileNotFoundException
     *             根据传入的文件名创建文件失败或者找不到传入的文件路径时抛出此异常
     * @throws IOException
     *             创建文件,压缩文件,写入文件错误时,抛出此异常
     */
    public static boolean zipFilesFromFile(String zipFileName, List<File> list) throws FileNotFoundException, IOException {
        if (list != null && list.size() > 0) {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
            for (File file : list) {
                out.putNextEntry(new ZipEntry(file.getName()));
                FileInputStream in = new FileInputStream(file);
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                in.close();
            }
            out.close();
            return true;
        }
        return false;
    }

    /**
     * 压缩文件
     * 
     * @param zipFileName
     *            压缩文件的保存路径(包括文件名的绝对路径)
     * @param map
     *            文件列表(key:压缩后的条目名称,value:文件的绝对路径(包括后缀))
     * @return boolean true:有生成文件|false:没有生成文件
     * @throws FileNotFoundException
     *             根据传入的文件名创建文件失败或者找不到传入的文件路径时抛出此异常
     * @throws IOException
     *             创建文件,压缩文件,写入文件错误时,抛出此异常
     */
    public static boolean zipFilesFromEntry(String zipFileName, Map<String, String> map) throws FileNotFoundException, IOException {
        if (map != null && !map.isEmpty()) {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
            for (Entry<String, String> entry : map.entrySet()) {
                File file = new File(entry.getValue());
                out.putNextEntry(new ZipEntry(entry.getKey()));
                FileInputStream in = new FileInputStream(file);
                int b;
                while ((b = in.read()) != -1) {
                    out.write(b);
                }
                in.close();
            }
            out.close();
            return true;
        }
        return false;
    }

    /**
     * 压缩文件或者文件夹<br />
     * 自动判断文件和文件夹
     * 
     * @param zipFileName
     *            压缩文件的保存路径(包括文件名的绝对路径)
     * @param inputFile
     *            压缩的文件或者文件夹
     * @throws IOException
     *             创建文件,压缩文件,写入文件错误时,抛出此异常
     */
    public static void zipFile(String zipFileName, File inputFile) throws IOException {
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFileName));
        BufferedOutputStream bo = new BufferedOutputStream(out);
        zip(out, inputFile, inputFile.getName(), bo);
        IOUtils.closeQuietly(bo);
        IOUtils.closeQuietly(out);
    }

//    /**
//     * 解压缩文件
//     * 
//     * @param file
//     *            待解压文件
//     * @param handler
//     *            处理器
//     * @since 1.7
//     * 
//     * @throws IOException
//     * 
//     */
//    public static void deZipFile(File file, DeZipHandler handler, String charset) throws IOException {
//        ZipFile zipFile = null;
//        ZipInputStream zipInputStream = null;
//        if (StringUtils.isBlank(charset)) {
//            zipFile = new ZipFile(file);
//            zipInputStream = new ZipInputStream(new FileInputStream(file));
//        } else {
//            zipFile = new ZipFile(file, Charset.forName(charset));// 实例化ZipFile，并指定
//            zipInputStream = new ZipInputStream(new FileInputStream(file), Charset.forName(charset));
//        }
//
//        ZipEntry zipEntry = null;
//        while ((zipEntry = zipInputStream.getNextEntry()) != null) {
//            // 通过ZipFile的getInputStream方法拿到具体的ZipEntry的输入流
//            InputStream is = zipFile.getInputStream(zipEntry);
//            handler.whileFile(zipEntry, is);
//            IOUtils.closeQuietly(is);
//        }
//        IOUtils.closeQuietly(zipInputStream);
//        IOUtils.closeQuietly(zipFile);
//    }

    public static interface DeZipHandler {
        void whileFile(ZipEntry zipEntry, InputStream in);
    }

    /**
     * 输入流 To 字符串,此方法会负责关闭流
     * 
     * @param in
     *            输入流
     * @return 从数据流中根据转换编码读取出来的字符串
     * @throws FileNotFoundException
     *             根据传入的文件名创建文件失败或者找不到传入的文件路径时抛出此异常
     * @throws IOException
     *             创建文件,压缩文件,写入文件错误时,抛出此异常
     */
    public static String inputStreamToString(InputStream in) throws FileNotFoundException, IOException {
        return IOUtil.inputStreamToString(in, null);
    }

    /**
     * 输入流 To 字符串,此方法会负责关闭流
     * 
     * @param in
     *            输入流
     * @param encoding
     *            转换编码
     * @return 从数据流中根据转换编码读取出来的字符串
     * @throws FileNotFoundException
     *             根据传入的文件名创建文件失败或者找不到传入的文件路径时抛出此异常
     * @throws IOException
     *             创建文件,压缩文件,写入文件错误时,抛出此异常
     */
    public static String inputStreamToString(InputStream in, String encoding) throws FileNotFoundException, IOException {
        InputStreamReader ir = null;
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        try {
            if (StringUtils.isEmpty(encoding)) {
                ir = new InputStreamReader(in);
            } else {
                ir = new InputStreamReader(in, encoding);
            }
            br = new BufferedReader(ir);

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\r\n");
            }
        } finally {
            IOUtils.closeQuietly(br);
            IOUtils.closeQuietly(ir);
            IOUtils.closeQuietly(in);
        }
        return sb.toString();
    }

    /**
     * 输入流 To 文件,此方法会负责关闭流
     * 
     * @param ins
     *            输入流
     * @param file
     *            文件
     * 
     * @throws FileNotFoundException
     *             根据传入的文件名创建文件失败或者找不到传入的文件路径时抛出此异常
     * @throws IOException
     *             创建文件,压缩文件,写入文件错误时,抛出此异常
     */
    public static void inputstreamToFile(InputStream ins, File file) throws FileNotFoundException, IOException {
        OutputStream os = new FileOutputStream(file);
        int bytesRead = 0;
        byte[] buffer = new byte[512];
        try {
            while ((bytesRead = ins.read(buffer, 0, 512)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(ins);
        }
    }

    /** 关闭套接字 */
    public static void close(Socket socket) {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception e) {
        }
    }

    private static void zip(ZipOutputStream out, File f, String base, BufferedOutputStream bo) throws IOException {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (fl.length == 0) {
                out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
            }
            for (int i = 0; i < fl.length; i++) {
                zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
            }
        } else {
            out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
            FileInputStream in = new FileInputStream(f);
            BufferedInputStream bi = new BufferedInputStream(in);
            int b;
            while ((b = bi.read()) != -1) {
                bo.write(b); // 将字节流写入当前zip目录
            }
            IOUtils.closeQuietly(bi);
            IOUtils.closeQuietly(in);
        }
    }
}
