import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader{
    private byte[] bytes;

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        String fileName = "E:\\project\\JAVA-000\\Week_01\\Hello.xlass";
        try(FileInputStream file = new FileInputStream(fileName)){
            int num = file.available();
            bytes= new byte[num];
            file.read(bytes);
            file.close();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte)(255 - bytes[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(name,bytes, 0, bytes.length);
    }

    public static void main(String[] args) {
        try{
            Class<?> hello = (Class<?>)new HelloClassLoader().findClass("Hello");
            System.out.println(hello.getClassLoader());
            Object h1 = hello.newInstance();
            Method method = hello.getMethod("hello");
            method.invoke(h1);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
