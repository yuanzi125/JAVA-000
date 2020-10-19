import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader{

    /**
     * 方法实现说明: 创建我们的class的二进制名称
     */
    private byte[] loadClassData(String fileName) {
        byte[] data = null;
        ByteArrayOutputStream baos = null;
        InputStream is = null;

        try {
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

    protected Class<?> findClass(String name,String fileName) throws ClassNotFoundException{
        byte[] data = loadClassData(fileName);
        System.out.println("MyClassLoader 加载的类: ==>" + name);
        return defineClass(name,data,0,data.length);
    }

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException,
        InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyClassLoader myClassLoader = new MyClassLoader();
        Class<?> aClass = myClassLoader.findClass("Hello","E:\\project\\JAVA-000\\Week_01\\Hello.xlass");
        System.out.println(aClass.getClassLoader());
        Object h1 = aClass.newInstance();
        Method method = aClass.getMethod("hello");
        method.invoke(h1);

    }
}
