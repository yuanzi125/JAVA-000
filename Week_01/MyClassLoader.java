import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader{
    private final static String fileSuffixExt = ".xlass";
    private String classLoaderName;
    private String loadPath;

    public void setLoadPath(String loadPath) {
        this.loadPath = loadPath;
    }

    /**
     * 执行当前类加载器的父类加载器
     */
    public MyClassLoader(ClassLoader parent, String classLoaderName) {
        super(parent);
        this.classLoaderName = classLoaderName;
    }
    /**
     * 使用appClassLoader加载器,作为本类的加载器
     */
    public MyClassLoader(String classLoaderName) {
        super();
        this.classLoaderName = classLoaderName;
    }

    public MyClassLoader(ClassLoader  classLoader) {
        super(classLoader);
    }

    /**
     * 方法实现说明: 创建我们的class的二进制名称
     */
    private byte[] loadClassData(String name) {
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        InputStream is = null;

        try {
            name = name.replace(".", "\\");
            String fileName = loadPath + name + fileSuffixExt;
            File file = new File(fileName);
            is = new FileInputStream(file);

            baos = new ByteArrayOutputStream();
            int ch;
            while (-1 != (ch = is.read())) {
                baos.write(255- ch);
            }
            data = baos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    protected Class<?> findClass(String name) throws ClassNotFoundException{
        byte[] data = loadClassData(name);
        System.out.println("MyClassLoader 加载的类: ==>" + name);
        return defineClass(name,data,0,data.length);
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException,
        InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader("myClassLoader");
        myClassLoader.setLoadPath("E:\\project\\JAVA-000\\Week_01\\");
        Class<?> xClass = myClassLoader.loadClass("Hello");
        Hello h1 = (Hello)xClass.newInstance();
        h1.hello();

    }
}
