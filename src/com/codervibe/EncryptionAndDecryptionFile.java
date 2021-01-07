package com.codervibe;

import com.Untils.Base64s;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2020/12/7  0007
 * @author Administrator
 * DateTime:2020/12/07 21:21
 * Description: 加密文件和解密文件的windows 图形界面 1.0.0
 * Others:只是用输入和输出框
 * 输入框输入要加密的字符，输出框输出加密后的字符。
 * 加密方式是BASE64进行左右移动
 * @author Administrator
 */
public class EncryptionAndDecryptionFile extends JFrame {
    public static void main(String[] args) {
        //运行的主方法

        EncryptionAndDecryptionFile encryptionAndDecryptionFile = new EncryptionAndDecryptionFile();

    }

    /**
     * 文件按钮
     */
    JButton button1 = new JButton("文件");
    /**
     * 加密按钮
     */
    JButton button2 = new JButton("加密");
    /**
     * 解密按钮
     */
    JButton button3 = new JButton("解密");
    /**
     * 这两个是输入框
     */
    JTextField textfield = new JTextField();
    JTextField textfield2 = new JTextField();


    /**
     * 要加密的文件路径
     */
    String path = "";
    /**
     * 要加密的文件名
     **/
    String name = "";

    public EncryptionAndDecryptionFile() {
        super();
        setVisible(true);
        //window_coordinates_width_and_height
        setBounds(450, 200, 700, 500);
        //窗体最上方的标题类似于<title></title>
        setTitle("加密与解密");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //文本域的大小和位置
        textfield.setBounds(100, 89, 400, 30);
        //将这个文本域添加到窗体上
        add(textfield);

        textfield2.setBounds(100, 150, 400, 30);
        //同上
        add(textfield2);

        //设置三个按钮的坐标和大小
        button2.setBounds(125, 250, 60, 35);
        add(button1);


        button3.setBounds(200, 250, 60, 35);
        add(button2);

        button1.setBounds(500, 86, 60, 30);
        add(button3);

        //文件按钮监听事件
        button1.addActionListener(e -> {
            //这一部分就是把那个文件选择的框调出来
            JFileChooser chooser = new JFileChooser();//创建文件选择类
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "文本文档", "txt", "md");//文件选择的类型
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(chooser);

            if (returnVal == JFileChooser.APPROVE_OPTION) {

                //clooser.getSelectedFile().getName()为点击的文件名
                textfield.setText(chooser.getSelectedFile().getPath());
                path = chooser.getSelectedFile().getPath();// 路径赋值
                name = chooser.getSelectedFile().getName();//文件名赋值
            }
        });
        //加密按钮的监听事件
        button2.addActionListener(e -> {
            String result;

            try {

                //根据数据源创建字符输入流对象
                //IO流读入要加密的文件
                BufferedReader br = new BufferedReader(new FileReader(textfield.getText()));
                String pwPath = getpath(path, name);//将路径中的文件名去掉留下纯文件名
                String pwPathFileName = pwPath + "加密后的" + name;
                //根据目的地创建字符输出流对象
                // IO流将加密后的文件写出  pwPath + "加密后的" + Name 写出的位置 和文件名
                PrintWriter pw = new PrintWriter(new FileWriter(pwPathFileName), true);


                //读写数据，复制文件
                String line;
                while ((line = br.readLine()) != null) {
                    //这个if 区间就是加密的部分了
                    byte[] str = line.getBytes();
                    String buttonName = e.getActionCommand();
                    //按钮的名字
                    String anticipationsButtonName = "加密";
                    if (buttonName.equals(anticipationsButtonName)) {

                        //加密算法改为 MD5 加密
                        try {
                            result = Base64s.encryptBASE64(str);
                            textfield2.setText(result);

                            pw.println(result);

                        } catch (Exception exception) {
                            exception.printStackTrace();

                        }

                    }
                }

                //释放资源
                pw.close();
                br.close();
                textfield2.setText(pwPath + "加密后的" + name);
                System.out.println("加密完成!!!!!!");
                //IO流读取写出时会出现异常 这里是用来捕捉的

            } catch (Exception exception) {
                exception.printStackTrace();
            }


        });
        //解密按钮监听事件
        button3.addActionListener(e -> {
            byte[] result;

            try {

                //根据数据源创建字符输入流对象
                BufferedReader br = new BufferedReader(new FileReader(textfield.getText()));
                //IO流读入要加密的文件
                String pwPath = getpath(path, name);//将路径中的文件名去掉留下纯文件名

                //根据目的地创建字符输出流对象
                // IO流将加密后的文件写出  pwPath + "加密后的" + Name 写出的位置 和文件名
                PrintWriter pw = new PrintWriter(new FileWriter(pwPath + "解密后的" + name), true);

                //读写数据，复制文件
                String line;
                while ((line = br.readLine()) != null) {
                    //这个if 区间就是加密的部分了

                    String buttonName = e.getActionCommand();
                    //按钮的名字
                    String anticipationsButtonName = "解密";
                    if (buttonName.equals(anticipationsButtonName)) {


                        try {
                            result = Base64s.decryptBASE64(line);
                            String str2 = new String(result);
                            pw.println(str2);

                        } catch (Exception exception) {
                            exception.printStackTrace();

                        }

                    }
                }

                //释放资源
                pw.close();
                br.close();
                System.out.println("解密已完成!!!!!!");
                //IO流读取写出时会出现异常 这里是用来捕捉的

            } catch (Exception exception) {
                exception.printStackTrace();
            }

        });


    }

    /**
     * hereIsHowToSeparateTheFileNameAndPath
     */
    public String getpath(String bigpath, String name) {
        String b = bigpath;
        //更换字符串B 中的 name 部分 为 空白
        b = b.replace(name, "");
        return b;
    }
}
