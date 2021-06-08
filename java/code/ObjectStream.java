import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class ObjectStream {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        HashMap<String, Object> map = new HashMap<>();
        map.put("receiverSchoolID", "100038192");
        map.put("receiverSchoolName", "school name");
        map.put("receiverGradeID", "8");
        map.put("receiverClassID", "cs-001");
        map.put("receiverClassName", "class name");
        System.out.println("source : ");
        System.out.println(map);
        System.out.println();

        //序列化 输出为比特流
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
        objectOut.writeObject(map);
        byte[] stream = byteOut.toByteArray();
        System.out.println("stream : ");
        System.out.println(new String(stream));
        System.out.println();

        //反序列化
        ByteArrayInputStream byteInput = new ByteArrayInputStream(stream);
        ObjectInputStream objectInput = new ObjectInputStream(byteInput);
        Map<String, Object> newMap = (Map<String, Object>) objectInput.readObject();
        System.out.println("target : ");
        System.out.println(newMap);
    }
}